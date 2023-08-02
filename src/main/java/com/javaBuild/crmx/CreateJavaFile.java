package com.javaBuild.crmx;


import cn.hutool.core.util.IdUtil;
import com.yd.utils.common.StringUtils;
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
public class CreateJavaFile implements ApplicationContextAware{
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
        List<String> tableNames = Arrays.asList("crm_base_customer_bubble_detail");
        String path="C:\\Users\\yansunling\\Desktop\\build\\";
		File dir=new File(path);
		FileUtils.deleteDirectory(dir);
		//创建目录
		dir.mkdirs();
		String filePath = getClass().getClassLoader().getResource("").getPath();
		for(String tableName:tableNames){
			String content = FileUtil.readAsString(new File(filePath+"java/TemplatePO.java"));
			String mapperContent=FileUtil.readAsString(new File(filePath+"java/TemplateMapper.java"));
			String mapperXml=FileUtil.readAsString(new File(filePath+"java/TemplateMapper.xml"));
			String serviceContent=FileUtil.readAsString(new File(filePath+"java/TemplateService.java"));
			String implContent=FileUtil.readAsString(new File(filePath+"java/TemplateServiceImpl.java"));
			String controllerContent=FileUtil.readAsString(new File(filePath+"java/TemplateController.java"));
			String htmlContent=FileUtil.readAsString(new File(filePath+"java/TemplateList.html"));
			String jsContent=FileUtil.readAsString(new File(filePath+"java/TemplateJavaScript.js"));
            String sql="select  CONCAT(\n" +
					"if(c.column_key='PRI','    @TableId\\n',''),\n" +
					"if(c.column_name='version','    @Version\\n',''),\n" +
					"if(c.column_name in('create_time','create_user_id','creator'),'    @TableField(fill = FieldFill.INSERT)\\n',''),\n" +
					"if(c.column_name in('operator','update_time','op_user_id'),'    @TableField(fill = FieldFill.INSERT_UPDATE)\\n',''),\n" +
					"'    @CJ_column(name = \"',c.column_comment,'\")\\n',\n" +
					"'    private ',case when c.data_type in('Integer','int') then 'Integer ' when c.data_type in('bigint') then 'Money ' when c.data_type='decimal ' then 'Double ' when c.data_type='date' or c.data_type='datetime'    then 'Timestamp ' else 'String ' end,c.column_name,';\\n\\n') as filed,\n" +
					"c.data_type,column_name,c.column_comment,c.table_name,tb.table_comment from information_schema.columns c\n" +
					"left join information_schema.tables tb on c.table_name=tb.table_name  and c.table_schema=tb.table_schema\n" +
					"where 1=1\n" +
					"and c.table_schema ='crm' " +
					"and tb.table_name in( '"+tableName+"');";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
			StringBuffer sb=new StringBuffer();
			mapList.forEach(map->{
				sb.append(map.get("filed")+"\n\n\n");
			});
			String tableComment=mapList.get(0).get("table_comment")+"";


			String[] strs = tableName.split("_");
			String prexName="Crmx";
			for(int i=1;i<strs.length;i++){
				prexName+=StringUtils.upperFirst(strs[i]);
			}
			//生成PO
			String className=prexName+"PO";
			String newContent=content.replaceAll("\\{content\\}",sb.toString())
					.replaceAll("\\{table_comment\\}",tableComment)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{class_name\\}",className);
			FileUtil.writeAsString(new File(path+tableName+"\\" +className+ ".java"),newContent);
			//生成mapper
			String classMapper=prexName+"Mapper";
			mapperContent=mapperContent.replaceAll("\\{class_name\\}",className).replaceAll("\\{class_mapper\\}",classMapper);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classMapper+ ".java"),mapperContent);
			//生成xml
			mapperXml=mapperXml.replaceAll("\\{class_mapper\\}",classMapper);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classMapper+ ".xml"),mapperXml);




			//生成service
			String classService=prexName+"Service";
			serviceContent=serviceContent.replaceAll("\\{class_name\\}",className).replaceAll("\\{class_service\\}",classService);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classService+ ".java"),serviceContent);

			//生成impl
			String classImpl=prexName+"ServiceImpl";
			implContent=implContent.replaceAll("\\{class_name\\}",className)
					.replaceAll("\\{class_impl\\}",classImpl)
					.replaceAll("\\{class_mapper\\}",classMapper)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{class_service\\}",classService);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classImpl+ ".java"),implContent);


			//生成controller
			String classController=prexName+"Controller";
			controllerContent=controllerContent.replaceAll("\\{class_name\\}",className)
					.replaceAll("\\{class_impl\\}",classImpl)
					.replaceAll("\\{class_controller\\}",classController)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{class_service\\}",classService);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classController+ ".java"),controllerContent);
			//生成html页面
			String htmlGroup=strs[1];
			String htmlName=tableName.replaceAll("crm_","crmx_")+"_list";
			htmlContent=htmlContent.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{html_name\\}",htmlName);
			FileUtil.writeAsString(new File(path+tableName+"\\" +htmlName+ ".html"),htmlContent);
			//生成js页面
			String jsName=tableName.replaceAll("crm_","crmx_");
			jsContent=jsContent.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{js_name\\}",jsName)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{table_comment\\}",tableComment);
			FileUtil.writeAsString(new File(path+tableName+"\\" +htmlName+ ".js"),jsContent);

		}
    }

}
