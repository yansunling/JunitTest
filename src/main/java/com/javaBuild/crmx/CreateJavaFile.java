package com.javaBuild.crmx;


import com.google.common.collect.Lists;
import com.javaBuild.tmsp.ColumnData;
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
import java.util.ArrayList;
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
        List<String> tableNames = Arrays.asList("hcm_org_position_rel_rule");
		String prexName="Hcm";
		String dataBase="hcm";
        String path="C:\\Users\\yansunling\\Desktop\\build\\";
		File dir=new File(path);
		FileUtils.deleteDirectory(dir);
		//创建目录
		dir.mkdirs();
		String filePath = getClass().getClassLoader().getResource("").getPath();
		for(String tableName:tableNames){
			String content = FileUtil.readAsString(new File(filePath+ "java/crm/TemplatePO.java"));
			String mapperContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateMapper.java"));
			String mapperXml=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateMapper.xml"));
			String serviceContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateService.java"));
			String implContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateServiceImpl.java"));
			String controllerContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateController.java"));
			String htmlContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateList.html"));
			String jsContent=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateJavaScript.js"));

            String formHtml=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateForm.html"));
            String formJs=FileUtil.readAsString(new File(filePath+ "java/crm/TemplateForm.js"));
            List<ColumnData> columnDataList=new ArrayList<>();
            List<String> exceptColumns = Arrays.asList("update_user_id","update_time","create_user_id","create_time",
                    "version","op_user_id","creator","serial_no","oa_flag","oa_apply_user_id","oa_apply_time","loan_process_number","repayment_process_number",
                    "expense_process_number","oa_status","approval_amount","archived_amount","violation_process_number","file_attachment");
            StringBuffer remarkTd=new StringBuffer();


            String sql="select  CONCAT(\n" +
					"if(c.column_key='PRI','    @TableId\\n',''),\n" +
					"if(c.column_name='version','    @Version\\n',''),\n" +
					"if(c.data_type='date','    @JsonFormat(pattern = \"yyyy-MM-dd\", timezone = \"GMT+8\")\\n','')," +
					"if(c.data_type='datetime','    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\\n',''),\n"+
					"if(c.column_name in('create_time','create_user_id','creator'),'    @TableField(fill = FieldFill.INSERT)\\n',''),\n" +
					"if(c.column_name in('operator','update_time','op_user_id'),'    @TableField(fill = FieldFill.INSERT_UPDATE)\\n',''),\n" +
					"'    @CJ_column(name = \"',c.column_comment,'\")\\n',\n" +
					"'    private ',case when c.data_type in('Integer','int') then 'Integer ' when c.data_type in('bigint') then 'Money ' when c.data_type='decimal ' then 'Double ' when c.data_type='date' or c.data_type='datetime'    then 'Timestamp ' else 'String ' end,c.column_name,';\\n\\n') as filed,\n" +
					"c.data_type,column_name,c.column_comment,c.table_name,tb.table_comment from information_schema.columns c\n" +
					"left join information_schema.tables tb on c.table_name=tb.table_name  and c.table_schema=tb.table_schema\n" +
					"where 1=1\n" +
					"and c.table_schema ='"+dataBase+"' " +
					"and tb.table_name in( '"+tableName+"');";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
			StringBuffer sb=new StringBuffer();
			mapList.forEach(map->{
				sb.append(map.get("filed")+"\n\n\n");

                String columnId=map.get("column_name")+"";
                if(StringUtils.equalsIgnoreCase("remark",columnId)){
                    remarkTd.append("\t\t\t<tr>\n" +
                            "\t\t\t\t<td align='right' width=13%>\n" +
                            "\t\t\t\t\t<label>备注:</label>\n" +
                            "\t\t\t\t</td>\n" +
                            "\t\t\t\t<td width=20% colspan=\"5\">\n" +
                            "\t\t\t\t\t<textarea id='remark' name='remark' type='text' data-options='required:false' class='easyui-validatebox' style='height:50px;width:800px'></textarea>\n" +
                            "\t\t\t\t</td>\n" +
                            "\t\t\t</tr>");

                    return;
                }
                if(!exceptColumns.contains(columnId)){
                    columnDataList.add(new ColumnData(columnId,map.get("column_comment")+"",map.get("data_type")+""));
                }

			});
			String tableComment=mapList.get(0).get("table_comment")+"";


			String[] strs = tableName.split("_");

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
			String[] splits = tableName.split("_");
			String packageName=splits[1];


			mapperXml=mapperXml.replaceAll("\\{class_mapper\\}",classMapper);
			mapperXml=mapperXml.replaceAll("\\{packageName\\}",packageName);
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
					.replaceAll("\\{table_name\\}",tableName.replaceAll("crm_","crmx_"))
					.replaceAll("\\{class_service\\}",classService);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classController+ ".java"),controllerContent);
			//生成html页面
			String htmlGroup=strs[1];
			String htmlName=tableName.replaceAll("crm_","crmx_")+"_list";
			htmlContent=htmlContent.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{html_name\\}",htmlName);
			FileUtil.writeAsString(new File(path+tableName+"\\" +htmlName+ ".html"),htmlContent);
			//生成js页面
			String jsName=tableName.replaceAll("crm_","crmx_");
			jsContent=jsContent.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{js_name\\}",jsName)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{table_comment\\}",tableComment);
			FileUtil.writeAsString(new File(path+tableName+"\\" +htmlName+ ".js"),jsContent);





            List<List<ColumnData>> partition = Lists.partition(columnDataList, 3);
            StringBuffer formTable=new StringBuffer();

            partition.forEach(tds->{
                StringBuffer tr=new StringBuffer();
                tr.append("\t\t\t<tr>\n");
                tds.forEach(td->{
                    if(StringUtils.equalsIgnoreCase("bigint",td.getData_type())){
                        tr.append("\t\t\t\t<td align='right' width=13%>\n" +
                                "\t\t\t\t\t<label>"+td.getColumnName()+":</label>\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t\t<td width=20%>\n" +
                                "\t\t\t\t\t<input type='text' id='"+td.getColumnId()+"' name='"+td.getColumnId()+"'   class=\"easyui-numberbox\"  data-options='required:true,min:1,precision:2'  style='height:30px;width:175px' />\n" +
                                "\t\t\t\t</td>\n");
                    }else if(StringUtils.equalsIgnoreCase("date",td.getData_type())||StringUtils.equalsIgnoreCase("datetime",td.getData_type())){
                        tr.append("\t\t\t\t<td align='right' width=13%>\n" +
                                "\t\t\t\t\t<label>"+td.getColumnName()+":</label>\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t\t<td width=20%>\n" +
                                "\t\t\t\t\t<input type='text' id='"+td.getColumnId()+"' name='"+td.getColumnId()+"'   class=\"easyui-datebox\"  data-options='required:true'  style='height:30px;width:175px' />\n" +
                                "\t\t\t\t</td>\n");
                    } else{
                        tr.append("\t\t\t\t<td align='right' width=13%>\n" +
                                "\t\t\t\t\t<label>"+td.getColumnName()+":</label>\n" +
                                "\t\t\t\t</td>\n" +
                                "\t\t\t\t<td width=20%>\n" +
                                "\t\t\t\t\t<input type='text' id='"+td.getColumnId()+"' name='"+td.getColumnId()+"' class=\"easyui-validatebox\"  data-options='required:true'  style='height:26px;width:168px' />\n" +
                                "\t\t\t\t</td>\n");
                    }
                });
                tr.append("\t\t\t</tr>\n");
                formTable.append(tr);
            });
            formTable.append(remarkTd);

            //生成html页面
            String tableName1=tableName.replace("crm_","crmx_");
            String formName=(tableName+"_form").replace("crm_","crmx_");
            formHtml=formHtml.replaceAll("\\{html_group\\}",htmlGroup)
                    .replaceAll("\\{html_name\\}",formName)
                    .replaceAll("\\{table_name\\}",tableName1)
                    .replaceAll("\\{table_content\\}",formTable.toString())
                    .replaceAll("\\{table_comment\\}",tableComment);
            FileUtil.writeAsString(new File(path+tableName+"\\" +formName+ ".html"),formHtml);


            //生成js页面
            formJs=formJs.replaceAll("\\{html_group\\}",htmlGroup)
                    .replaceAll("\\{table_name\\}",tableName1)
                    .replaceAll("\\{table_comment\\}",tableComment);
            FileUtil.writeAsString(new File(path+tableName+"\\" +formName+ ".js"),formJs);




        }
    }

}
