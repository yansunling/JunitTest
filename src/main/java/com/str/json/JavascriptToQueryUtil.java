package com.str.json;

import com.alibaba.fastjson.JSON;
import com.yd.utils.common.StringUtils;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavascriptToQueryUtil {
	public static void main(String[] args) throws Exception{
        String filePath = JavascriptToQueryUtil.class.getClassLoader().getResource("").getPath();
        filePath+="/javascript/columns.js";
		String json = removeJsComments(FileUtil.readAsString(new File(filePath)));
        List<Map<String, String>> maps = distinctByField(JavaScriptToJsonUtil.jsToList(json));
        StringBuffer sb=new StringBuffer();
		List<String> list=new ArrayList<>();
        String queryId="tmsp_hand_arrive_list_title";
        Map<String,List<String>> sqlMap=new LinkedHashMap<>();
        Map<String,List<String>> fieldMap=new LinkedHashMap<>();
        Map<String,String> stylerMap=new LinkedHashMap<>();
        AtomicInteger count = new AtomicInteger(1); // 初始化下标为 0

        maps.forEach(temp->{

            int index = count.getAndIncrement();

            String name = temp.get("name");
            if(StringUtils.isBlank(name)||temp.get("name")==null){
                return;
            }
            String field = temp.get("field");
            if(index==maps.size()){
                sb.append("'' as "+ temp.get("field")+" -- "+temp.get("name")+"\n");
            }else{
                sb.append("'' as "+ temp.get("field")+",-- "+temp.get("name")+"\n");
            }
			if(StringUtils.equals("true",temp.get("hidden"))){
			    list.add(temp.get("field")+"");
                setSqlMap(sqlMap,"hide","update query.query_new_columns set hide_flag='Y' where query_id='"+queryId+"' and ui_column_id='"+field+"';");

            }
			if(temp.get("width")!=null){
                String width = (temp.get("width") + "").replace("px", "");
                setSqlMap(sqlMap,"width","update query.query_new_columns set ui_width='"+width+"' where query_id='"+queryId+"' and ui_column_id='"+field+"';");
            }
			if(StringUtils.equals("easyui-datetimebox",temp.get("ctrlType")+"")){
                setSqlMap(fieldMap,"datetime",field);
            }

            if(StringUtils.isNotBlank(temp.get("styler"))){
                stylerMap.put(field,temp.get("styler"));
            }

		});
        sb.append("from tmsp.tmsp_order_profile main \n\n\n");
		System.out.println(sb.toString());


        sqlMap.forEach((key,items)->{
            items.forEach(item->{
                System.out.println(item);
            });
            System.out.println("\n\n");
        });
        String datetimeFormat="";
        Set<String> keySet = fieldMap.keySet();
        for(String key:keySet){
            List<String> items = fieldMap.get(key);
            if(StringUtils.equalsIgnoreCase("datetime",key)){
                datetimeFormat="let datetimeFormat="+ JSON.toJSONString(items)+";";
            }
        }


        String tempSql="let titleByQuery =query.initDefineColumns(queryId,listTemplate);\n" +
                "titleByQuery.splice(0, 0, {field:'ck',checkbox:true});\n" ;
        if(StringUtils.isNotBlank(datetimeFormat)){
            tempSql+=datetimeFormat+"\n";
        }
        tempSql+= "titleByQuery.forEach(item=>{\n" ;
        if(!stylerMap.isEmpty()){
            tempSql+= buildStylerAssignmentCode(stylerMap);
        }
        if(StringUtils.isNotBlank(datetimeFormat)){
            tempSql+=  "\t\tif(datetimeFormat.includes(item.field)){\n" +
                    "\t\t\titem.ctrlType=\"easyui-datetimebox\";\n" +
                    "\t\t}\n" ;
        }
        tempSql+="});";

        System.out.println(tempSql);


	}
	private static void setSqlMap(Map<String,List<String>> sqlMap,String key,String sql){
        List<String> sqlList = sqlMap.getOrDefault(key, new ArrayList<>());
        sqlList.add(sql);
        sqlMap.put(key,sqlList);
    }

    private static List<Map<String, String>> distinctByField(List<Map<String, String>> maps){
        List<Map<String, String>> result = new ArrayList<>();
        Set<String> fieldSet = new HashSet<>();
        for (Map<String, String> map : maps) {
            String field = map.get("field");
            if (StringUtils.isBlank(field) || fieldSet.add(field)) {
                result.add(map);
            }
        }
        return result;
    }

    static String buildStylerAssignmentCode(Map<String, String> stylerMap){
        StringBuilder builder = new StringBuilder();
        Map<String, List<String>> stylerFieldMap = new LinkedHashMap<>();
        stylerMap.forEach((field, stylerName) -> {
            List<String> fields = stylerFieldMap.getOrDefault(stylerName, new ArrayList<>());
            fields.add(field);
            stylerFieldMap.put(stylerName, fields);
        });
        stylerFieldMap.forEach((stylerName, fields) -> {
            builder.append("\t\tif([");
            for (int i = 0; i < fields.size(); i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append("'").append(fields.get(i)).append("'");
            }
            builder.append("].includes(item.field)){\n");
            builder.append("\t\t\titem.styler=").append(stylerName).append(";\n");
            builder.append("\t\t}\n");
        });
        return builder.toString();
    }

    private static String removeJsComments(String content){
        String withoutBlockComments = content.replaceAll("(?s)/\\*.*?\\*/", "");
        return withoutBlockComments.replaceAll("(?m)//.*$", "");
    }




	public static List<Map<String,String>> getMap(String json){
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(json);
        List<Map<String,String>> list=new ArrayList<>();
        while  (matcher.find()) {
            String objStr = matcher.group(1);
            String[] strs = objStr.split(",");
            Map<String,String> map=new HashMap<>();
            for(String str:strs){
                String[] temps = str.split(":");
                map.put(temps[0].replaceAll("'",""),temps[1].replaceAll("'",""));
            }
            list.add(map);
        }
        return list;
    }


}
