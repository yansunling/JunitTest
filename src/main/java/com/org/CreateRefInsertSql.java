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
import java.util.*;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateRefInsertSql implements ApplicationContextAware{
	ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac=applicationContext;
		
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Test
	public  void test() throws Exception {
		String filePath = getClass().getClassLoader().getResource("").getPath();
		List<String> tableFiles = FileUtils.readLines(new File(filePath + "java/table/fixTable.tab"), "utf-8");

		List<String> orgColumns= Arrays.asList("org_id","business_region_id","business_district_id",
				"committee_id","sup_org_id","ticket_org_id","inventory_org_id","rel_org_id","site_org_id",
				"depart_org_id","income_org_id","next_org_id","hr_org_id");

		List<String> fileSql=new ArrayList<>();
		for(String tableFile:tableFiles){

			String[] split = tableFile.split("\\.");
			String table=split[1];
			String schema=split[0];

			String columnsSql="select column_name from  information_schema.COLUMNS where table_name='"+table+"' and table_schema='"+schema+"' " +
					" order by ordinal_position";
			List<String> columnList = jdbcTemplate.queryForList(columnsSql, String.class);

			String sql="insert ignore into "+tableFile+"("+StringUtils.join(",",columnList.toArray())+")\n select ";

			List<String> newColumns=new ArrayList<>();
			int num=0;
			List<String> joinSql=new ArrayList<>();

			for(int i=0;i<columnList.size();i++){
				String column=columnList.get(i);
				if(orgColumns.contains(column)){
					String alias="ref"+num;
					newColumns.add(alias+".new_org_id");
					joinSql.add("inner join tmsp.tmsp_org_new_old_ref "+alias+" on "+alias+".old_org_id=main."+column);
					num++;
				}else if(StringUtils.equals("serial_no",column)){
					newColumns.add("uuid_short()");
				}else{
					newColumns.add("main."+column);
				}
			}
			sql+=StringUtils.join(",",newColumns.toArray())+"\n from "+tableFile+" main \n "+StringUtils.join("\n",joinSql.toArray());
			fileSql.add(sql+";\n\n\n");
		}

		File outfile=new File("C:/Users/yansunling/Desktop/insertOrg.sql");
		FileUtils.writeLines(outfile,"utf-8",fileSql);
		



	}










}
