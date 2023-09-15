package com.javaBuild.crmx;


import com.javaBuild.titleMap.CreateTitleData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
        List<String> domainList = Arrays.asList("sale_progress");
        String path="C:\\Users\\yansunling\\Desktop\\enum\\";
		File dir=new File(path);

		FileUtils.deleteDirectory(dir);
		//创建目录
		dir.mkdirs();
		String filePath = getClass().getClassLoader().getResource("").getPath()+"java/TemplateEnum.java";
		File file=new File(filePath);

		String content = FileUtil.readAsString(file);

		for(String item:domainList){
            String sql="select code_type,code_name from crm.cip_admin_codes where domain_id in('"+item+"')";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
			StringBuffer sb=new StringBuffer();
			mapList.forEach(map->{
				sb.append("    "+item.toUpperCase()).append("_").append(String.valueOf(map.get("code_type")).toUpperCase())
						.append("(\""+map.get("code_type")+"\",\""+map.get("code_name")+"\"),\n");
			});
			String className="CRMX_"+item.toUpperCase();
			String newContent=content.replaceAll("\\{content\\}",sb.toString()).replaceAll("CRMX_COMMON_STATUS",className);



			FileUtil.writeAsString(new File(path +className+ ".java"),newContent);
        }

    }






}
