package com.org;


import com.alibaba.fastjson.JSON;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class FindOrgSql implements ApplicationContextAware{
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


		OrgData orgData=new OrgData("'250109040703'","'25010904030301','25010904030302','25010904030303'","'绍兴线路管理三组'","'柯西新疆收货点','北二区新疆收货点','北八区新疆收货点'");
		orgData.setNewDistrictId("'2501090407'");
		orgData.setNewRegionId("'25010904'");
		orgData.setOldOrgList("'25010904030301|25010904030302|25010904030303'");
		orgData.setOldSigleOrg("'25010904030301'");

		deleteFolder(new File("C:\\Users\\yansunling\\Desktop\\org\\"));

		jdbcTemplate.setQueryTimeout(500);
		DruidComboPoolDataSource dataSource = (DruidComboPoolDataSource)ydDriverManagerDataSource.getObject();
		dataSource.setMaxActive(100);
		String orgSql="select org_id,org_name from hcm.hcm_org_info where org_id!='25'";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(orgSql);
		List<String> orgList=new ArrayList<>();
		List<String> orgNameList=new ArrayList<>();
		maps.forEach(item->{
			orgList.add(item.get("org_id")+"");
			orgNameList.add(item.get("org_name")+"");
		});
		String schemaSql="select table_schema from information_schema.`TABLES` " +
				"where  table_schema not in('information_schema'," +
				"'query','dct','ouyang','portal','biq','das','acs','dctx','gms','hcmp','click','dts','fsm','costx','mdm','mms','pay','task','tms','log','vip','wac','kjob','crmx','jeewx-boot') " +
				"  group by table_schema";
		List<String> schemaList = jdbcTemplate.queryForList(schemaSql, String.class);
		List<String> errorTable=new ArrayList<>();

		String filePath = getClass().getClassLoader().getResource("").getPath();
		List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/errorTable.txt"), "utf-8");
		List<String> odlSqlList = FileUtils.readLines(new File(filePath + "java/table/tmsp_org_adjust_template.sql"), "utf-8");
		String tableFilesStr=StringUtils.join(",",tableFiles.toArray())+",";
		//排除基础表
		List<String> sqlList=new ArrayList<>();
		odlSqlList.forEach(item->{
			boolean addFlag=true;
			for(String table:tableFiles){
				if(item.indexOf(" "+table+" ")>=0){
					addFlag=false;
					break;
				}
			}
			if(addFlag){
				item=item.replaceAll("'<新机构ID>'",orgData.getNewOrgId());
				item=item.replaceAll("'<老机构ID>'",orgData.getOldOrgId());
				item=item.replaceAll("'<新机构名称>'",orgData.getNewOrgName());
				item=item.replaceAll("'<老机构名称>'",orgData.getOldOrgName());
				item=item.replaceAll("'<新机构小区ID>'",orgData.getNewDistrictId());
				item=item.replaceAll("'<新机构大区ID>'",orgData.getNewRegionId());
				item=item.replaceAll("'<老机构ID集合>'",orgData.getOldOrgList());
				item=item.replaceAll("'<老机构ID单个>'",orgData.getOldSigleOrg());

				if(item.indexOf("<替换老机构ID集合>")>0&&orgData.getOldOrgId().indexOf(",")>0){
					String oldOrg = orgData.getOldOrgId();
					String[] strs = oldOrg.split(",");
					StringBuffer sb=new StringBuffer();
					for(String str:strs){
						sb.append(item.replaceAll("'<替换老机构ID集合>'",str)).append("\n");
					}
					item=sb.toString();
				}
				if(item.indexOf("<替换老机构名称集合>")>0&&orgData.getOldOrgName().indexOf(",")>0){
					String oldOrg = orgData.getOldOrgName();
					String[] strs = oldOrg.split(",");
					StringBuffer sb=new StringBuffer();
					for(String str:strs){
						sb.append(item.replaceAll("'<替换老机构名称集合>'",str)).append("\n");
					}
					item=sb.toString();
				}
				sqlList.add(item);
			}
		});
		String existSql=StringUtils.join(",",sqlList.toArray());

		ExecutorService executorService = Executors.newFixedThreadPool(50);
		List<String> allData=new ArrayList<>();
		for(String schema:schemaList){
			String sql="select table_name from information_schema.`TABLES` where table_schema='"+schema+"' and table_type!='VIEW' ";
			List<String> tableList = jdbcTemplate.queryForList(sql, String.class);
			CountDownLatch countDownLatch = new CountDownLatch(tableList.size());
			List<String> totalSql=new ArrayList<>();
			for(String table:tableList){
				String newTable=schema+"."+table;

				executorService.submit(new FutureTask<String>(new Callable<String>() {
					@Override
					public String call() throws Exception {
						try {
							//已存在配置
							if(existSql.indexOf(" "+newTable+" ")>0){
								return "";
							}

							if(tableFilesStr.indexOf(newTable+",")>0){
								return "";
							}
							if(matchTables(newTable)){
								return "";
							}
							String columnsSql="select column_name from  information_schema.COLUMNS where table_name='"+table+"' and table_schema='"+schema+"' " +
									"and column_name not in('serial_no','create_user_id','update_user_id','remark','salesman_id') and data_type not in('decimal','datetime','date','int') ";
							List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);
							if(CollectionUtil.isNotEmpty(columnList)){
								List<String> sqlList=new ArrayList<>();
								columnList.forEach(column->{
									String dataSql="select `"+column+"` from "+newTable+" where  ifnull(`"+column+"`,'')!=''  limit 1";
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
									if(containsChinese(column)){
										column="`"+column+"`";
									}

									if(orgList.contains(newValue)){
										if(concat){
											sqlList.add("update "+newTable+" set "+column+" = CONCAT("+column+",',','<新机构ID>') where "+column+" like CONCAT('%','<老机构ID>','%');");
										}else{
											sqlList.add("update "+newTable+" set "+column+" = '<新机构ID>' where "+column+" in('<老机构ID>');");
										}
									}else if(orgNameList.contains(newValue)){
										if(concat){
											sqlList.add("update "+newTable+" set "+column+" = CONCAT("+column+",',','<新机构名称>') where "+column+" like CONCAT('%','<老机构名称>','%');");
										}else{
											sqlList.add("update "+newTable+" set "+column+" = '<新机构名称>' where "+column+" in('<老机构名称>');");
										}
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
				File outfile=new File("C:\\Users\\yansunling\\Desktop\\org\\"+schema+".sql");
				List<String> newTotalSql=new ArrayList<>();
				totalSql.forEach(item->{
					item=item.replaceAll("'<新机构ID>'",orgData.getNewOrgId());
					item=item.replaceAll("'<老机构ID>'",orgData.getOldOrgId());
					item=item.replaceAll("'<新机构名称>'",orgData.getNewOrgName());
					item=item.replaceAll("'<老机构名称>'",orgData.getOldOrgName());
					item=item.replaceAll("'<新机构小区ID>'",orgData.getNewDistrictId());
					item=item.replaceAll("'<新机构大区ID>'",orgData.getNewRegionId());
					newTotalSql.add(item);
				});
				FileUtils.writeLines(outfile,"utf-8",newTotalSql);
				allData.add("\n\n  -- "+schema.toUpperCase());
				allData.addAll(newTotalSql);
			}

		}
		File outfile=new File("C:\\Users\\yansunling\\Desktop\\org\\errorTable.sql");
		FileUtils.writeLines(outfile,"utf-8",errorTable);

		File allFile=new File("C:\\Users\\yansunling\\Desktop\\org\\allFile.sql");

		sqlList.add("\n\n\n");
		sqlList.addAll(allData);
		FileUtils.writeLines(allFile,"utf-8",sqlList);

	}

	public  boolean containsChinese(String str) {
		if (str == null) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (c >= '\u4e00' && c <= '\u9fa5') {
				return true;
			}
		}
		return false;
	}

	public static boolean deleteFolder(File folder) {
		if (folder == null) {
			return false;
		}
		if (!folder.exists()) {
			return false;
		}
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					// 递归删除子文件夹
					deleteFolder(f);
				} else {
					// 删除文件
					f.delete();
				}
			}
		}
		// 删除空文件夹
		return folder.delete();
	}
	public static boolean  matchTables(String input){
		if(input.startsWith("dtcx.s202")||input.startsWith("dctx.s202")){
			return true;
		}
		return  false;
	}


	@Data
	public static class OrgData{
		private String newOrgId;
		private String oldOrgId;
		private String newOrgName;
		private String oldOrgName;
		private String newRegionId;
		private String newDistrictId;
		private String oldOrgList;
		private String oldSigleOrg;

		public OrgData(String newOrgId, String oldOrgId, String newOrgName, String oldOrgName) {
			this.newOrgId = newOrgId;
			this.oldOrgId = oldOrgId;
			this.newOrgName = newOrgName;
			this.oldOrgName = oldOrgName;
		}
	}




}
