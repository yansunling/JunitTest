package com.org;


import com.org.data.OrgData;
import com.org.util.SwitchUtil;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateSwitchOrgFixTableSql implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Qualifier("dataSource")
	@Autowired
	private YDDriverManagerDataSource ydDriverManagerDataSource;

	@Test
	public  void test() throws Exception {
		String excelFilePath="C:\\Users\\yansunling\\Desktop\\TL_绍兴机构切换调整明细_20240318.xlsx";
		List<OrgData> orgDataList = SwitchUtil.readExcel(excelFilePath);
		SwitchUtil.deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\org\\"));
		jdbcTemplate.setQueryTimeout(500);
		DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource)ydDriverManagerDataSource.getObject();
		dataSource.setMaxActive(100);
		//排除基础表
		List<String> sqlTotalList=new ArrayList<>();
		for(OrgData orgData:orgDataList){
			String orgSql="select org_id,org_name from hcm.hcm_org_info where org_id not in('25','990000011')";
			List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
			List<String> orgList=new ArrayList<>();
			List<String> orgNameList=new ArrayList<>();
			maps.forEach(item->{
				orgList.add(item.get("org_id")+"");
				orgNameList.add(item.get("org_name")+"");
			});
			String schemaSql="select table_schema from information_schema.`TABLES` " +
					"where table_schema='crm' and  table_schema not in('information_schema'," +
					"'query','dct','ouyang','portal','biq','das','acs','dctx','gms','hcmp','click','dts','fsm','costx','mdm','mms','pay','task','tms','log','vip','wac','kjob','crmx','jeewx-boot') " +
					"  group by table_schema";
			List<String> schemaList = jdbcTemplate.queryForList(schemaSql, String.class);
			List<String> errorTable=new ArrayList<>();
			String filePath = getClass().getClassLoader().getResource("").getPath();
			List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/fixTable.tab"), "utf-8");
			String tableFilesStr=StringUtils.join(",",tableFiles.toArray())+",";
			//排除基础表
			List<String> sqlList=new ArrayList<>();
			ExecutorService executorService = Executors.newFixedThreadPool(50);
			List<String> allData=new ArrayList<>();
			for(String schema:schemaList){
				String sql="select table_name from information_schema.`TABLES` where table_schema='"+schema+"'  and table_type!='VIEW' ";
				List<String> tableList = jdbcTemplate.queryForList(sql, String.class);
				CountDownLatch countDownLatch = new CountDownLatch(tableList.size());
				List<String> totalSql=new ArrayList<>();
				for(String table:tableList){
					String newTable=schema+"."+table;

					executorService.submit(new FutureTask<String>(new Callable<String>() {
						@Override
						public String call() throws Exception {
							try {
								//不是固定表跳过
								if(tableFilesStr.indexOf(newTable+",")<0){
									return "";
								}
								String columnsSql="select column_name from  information_schema.COLUMNS where table_name='"+table+"' and table_schema='"+schema+"' " +
										"and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id') and data_type not in('decimal','datetime','date','int') ";
								List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
								if(CollectionUtil.isNotEmpty(columnList)){
									List<String> sqlList=new ArrayList<>();
									columnList.forEach(column->{
										String dataSql="select `"+column+"` from "+newTable+" where  length(`"+column+"`)>=4  limit 1";
										List<String> valueList = jdbcTemplate.queryForList(dataSql, String.class);
										if(CollectionUtil.isEmpty(valueList)){
											return;
										}
										String newValue=valueList.get(0);
										if(StringUtils.isBlank(newValue)){
											return;
										}
										boolean concat=false;
										if(newValue.indexOf(",")>0){
											String[] split = newValue.split(",");
											newValue=split[0];
											concat=true;
										}
										if(SwitchUtil.containsChinese(column)){
											column="`"+column+"`";
										}
										if(orgList.contains(newValue)){
											sqlList.add(SwitchUtil.matchColumn(column,newTable,"ID",concat));

										}else if(orgNameList.contains(newValue)){
											sqlList.add(SwitchUtil.matchColumn(column,newTable,"名称",concat));
										}
									});
									totalSql.addAll(sqlList);
								}
							} catch (Exception e) {
								e.printStackTrace();
								errorTable.add(newTable);
							}finally {
								countDownLatch.countDown();
							}
							return "";
						}
					}));

				}
				countDownLatch.await();
				if(CollectionUtil.isNotEmpty(totalSql)){
					List<String> newTotalSql=new ArrayList<>();
					totalSql.forEach(item->{
						String newItem = SwitchUtil.replaceName(item, orgData);
						if(StringUtils.isNotBlank(newItem)){
							newTotalSql.add(newItem);
						}

					});
					if(CollectionUtil.isNotEmpty(newTotalSql)){
						allData.add("\n\n  -- "+schema.toUpperCase());
						allData.addAll(newTotalSql);
					}
				}
			}
			File outfile=new File("C:\\Users\\yansunling\\Desktop\\org\\errorTable.sql");
			FileUtils.writeLines(outfile,"utf-8",errorTable,true);
			String fileName=orgData.getNewOrgName()+"["+orgData.getNewOrgId()+"]切为"+orgData.getOldOrgName()+"["+orgData.getOldOrgId()+"]";
			fileName=fileName.replaceAll("'","");
			File allFile=new File("C:\\Users\\yansunling\\Desktop\\org\\"+fileName+".sql");
			if(CollectionUtil.isNotEmpty(allData)){
				sqlList.add("\n\n\n");
				sqlList.addAll(allData);
				FileUtils.writeLines(allFile,"utf-8",sqlList);
				sqlTotalList.addAll(sqlList);
			}

		}
		if(CollectionUtil.isNotEmpty(sqlTotalList)){
			File allFile=new File("C:\\Users\\yansunling\\Desktop\\org\\allSql.sql");
			FileUtils.writeLines(allFile,"utf-8",sqlTotalList);
		}
	}










}
