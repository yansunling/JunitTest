package com.word;


import com.sqlFile.SqlFileUtil;
import com.titleMap.CreateTitleData;
import com.yd.common.exception.CIPServiceException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.crm.util.SqlUtil;
import com.yd.utils.datasource.DruidComboPoolDataSource;
import com.yd.utils.datasource.YDDriverManagerDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.StringUtil;
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
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateEnumBySource implements ApplicationContextAware{
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
        List<String> domainList = Arrays.asList("delivery_decision","send_recv_flag","cust_classify","cust_source","main_lines");
        String path="C:\\Users\\admin\\Desktop\\enum\\";
		File dir=new File(path);
		//创建目录
		dir.mkdirs();
		String classpath =  CreateTitleData.class.getResource( "/" ).getPath().replaceFirst( "/" ,  "" );
		String filePath = classpath.replaceAll( "/classes/" ,  "")+"/JuninTest/WEB-INF/template/CRMX_COMMON_STATUS.java";
		File file=new File(filePath);

		String content = FileUtil.readAsString(file);

		for(String item:domainList){
            String sql="select code_type,code_name from crm.cip_admin_codes where domain_id in('"+item+"')";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
			StringBuffer sb=new StringBuffer();
			mapList.forEach(map->{
				sb.append("    "+item.toUpperCase()).append("_").append(map.get("code_type"))
						.append("(\""+map.get("code_type")+"\",\""+map.get("code_name")+"\"),\n");
			});
			String className="CRMX_"+item.toUpperCase();
			String newContent=content.replaceAll("\\{content\\}",sb.toString()).replaceAll("CRMX_COMMON_STATUS",className);



			FileUtil.writeAsString(new File(path +className+ ".java"),newContent);
        }

    }






}
