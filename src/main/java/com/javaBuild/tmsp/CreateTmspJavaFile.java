package com.javaBuild.tmsp;


import cn.hutool.core.collection.ListUtil;
import com.google.common.collect.Lists;
import com.javaBuild.po.ButtonType;
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
import java.util.*;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateTmspJavaFile implements ApplicationContextAware{
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
        List<String> tableNames = Arrays.asList("tmsp_base_arrive_print_rule");
		String htmlGroup="ownCar";
        String path="C:\\Users\\yansunling\\Desktop\\build\\";
		File dir=new File(path);
		FileUtils.deleteDirectory(dir);
		//创建目录
		dir.mkdirs();
		String filePath = getClass().getClassLoader().getResource("").getPath();
		for(String tableName:tableNames){
			String content = FileUtil.readAsString(new File(filePath+"java/tmsp/TemplatePO.java"));
			String dataContent = FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateData.java"));


			String mapperContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateMapper.java"));
			String serviceContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateService.java"));
			String implContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateServiceImpl.java"));
			String controllerContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateController.java"));
			String htmlContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateList.html"));
			String jsContent=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateJavaScript.js"));

			String formHtml=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateForm.html"));
			String formJs=FileUtil.readAsString(new File(filePath+"java/tmsp/TemplateForm.js"));



			List<ColumnData> columnDataList=new ArrayList<>();
			List<String> exceptColumns = Arrays.asList("update_user_id","update_time","create_user_id","create_time",
                    "version","op_user_id","creator","serial_no","oa_flag","oa_apply_user_id","oa_apply_time","loan_process_number","repayment_process_number",
                    "expense_process_number","oa_status","approval_amount","archived_amount","violation_process_number","file_attachment");
			StringBuffer remarkTd=new StringBuffer();



            String sql="select  CONCAT(\n" +
					"if(c.column_key='PRI','    @TableId\\n',''),\n" +
					"if(c.column_name='version','    @Version\\n',''),\n" +
					"if(c.column_name in('create_time','create_user_id','creator'),'    @TableField(fill = FieldFill.INSERT)\\n',''),\n" +
					"if(c.column_name in('operator','update_time','op_user_id','update_user_id'),'    @TableField(fill = FieldFill.INSERT_UPDATE)\\n',''),\n" +
					"if((c.data_type='date' or c.data_type='datetime' and c.column_name not in('create_time','update_time','oa_apply_time')),'    @JsonFormat(pattern = \"yyyy-MM-dd\",timezone = \"GMT+8\")\\n',''),\n" +
					"'    @CJ_column(name = \"',c.column_comment,'\")\\n',\n" +
					"'    private ',case when c.data_type in('Integer','int') then 'Integer ' when c.data_type in('bigint') then 'Money ' when c.data_type='decimal ' then 'Double ' when c.data_type='date' or c.data_type='datetime'    then 'Timestamp ' else 'String ' end,c.column_name,';\\n\\n') as filed,\n" +
					"c.data_type,column_name,c.column_comment,c.table_name,tb.table_comment from information_schema.columns c\n" +
					"left join information_schema.tables tb on c.table_name=tb.table_name  and c.table_schema=tb.table_schema\n" +
					"where 1=1\n" +
					"and c.table_schema ='tmsp' " +
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

			String dataSql="select  CONCAT(\n" +
					"if(c.column_key='PRI','    @TableId\\n',''),\n" +
					"if(c.column_name='version','    @Version\\n',''),\n" +
					"if(c.column_name in('create_time','create_user_id','creator'),'    @TableField(fill = FieldFill.INSERT)\\n',''),\n" +
					"if(c.column_name in('operator','update_time','op_user_id','update_user_id'),'    @TableField(fill = FieldFill.INSERT_UPDATE)\\n',''),\n" +
					" if(c.data_type in('bigint'),concat('    @MyPositiveMoney(message = \"',c.column_comment,'为正数的数字\")\\n'),\n" +
					" if(c.column_name in('operator','serial_no','update_time','op_user_id','update_user_id','create_user_id','create_time'),'','    @MyNotEmpty\\n')),\n" +
					"'    @CJ_column(name = \"',c.column_comment,'\")\\n',\n" +
					"'    private ',case when c.data_type in('Integer','int') then 'Integer ' when c.data_type in('bigint') then 'Money ' when c.data_type='decimal ' then 'Double ' when c.data_type='date' or c.data_type='datetime'    then 'Timestamp ' else 'String ' end,c.column_name,';\\n\\n') as filed,\n" +
					"CONCAT('titleMap.put(\"',c.column_name,'\",\"',c.column_comment,'\");') as title,\n" +
					"c.data_type,column_name,c.column_comment,c.table_name,tb.table_comment from information_schema.columns c\n" +
					"left join information_schema.tables tb on c.table_name=tb.table_name  and c.table_schema=tb.table_schema\n" +
					"where 1=1\n" +
					"and c.table_schema ='tmsp' and tb.table_name in( '"+tableName+"');";


			mapList = jdbcTemplate.queryForList(dataSql);
			StringBuffer dataSb=new StringBuffer();
			StringBuffer dataTitle=new StringBuffer();
			mapList.forEach(map->{
				dataSb.append(map.get("filed")+"\n\n\n");
				String columnId=map.get("column_name")+"";
				if(!exceptColumns.contains(columnId)){
					dataTitle.append("        "+map.get("title")+"\n");
				}
			});


			String tableComment=mapList.get(0).get("table_comment")+"";


			String[] strs = tableName.split("_");
			String prexName="Tmsp";
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



			//生成Data
			String classData=prexName+"Data";
			String newDataContent=dataContent.replaceAll("\\{content\\}",dataSb.toString())
					.replaceAll("\\{table_comment\\}",tableComment)
					.replaceAll("\\{title\\}",dataTitle.toString())
					.replaceAll("\\{class_name\\}",classData);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classData+ ".java"),newDataContent);




			//生成mapper
			String classMapper=prexName+"Mapper";
			mapperContent=mapperContent.replaceAll("\\{class_name\\}",className).replaceAll("\\{class_mapper\\}",classMapper);
			FileUtil.writeAsString(new File(path+tableName+"\\" +classMapper+ ".java"),mapperContent);
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
			String formName=tableName+"_form";
			formHtml=formHtml.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{html_name\\}",formName)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{table_content\\}",formTable.toString())
					.replaceAll("\\{table_comment\\}",tableComment);
			FileUtil.writeAsString(new File(path+tableName+"\\" +formName+ ".html"),formHtml);


			


			//生成js页面
			formJs=formJs.replaceAll("\\{html_group\\}",htmlGroup)
					.replaceAll("\\{table_name\\}",tableName)
					.replaceAll("\\{table_comment\\}",tableComment);
			FileUtil.writeAsString(new File(path+tableName+"\\" +formName+ ".js"),formJs);




		}
    }









}
