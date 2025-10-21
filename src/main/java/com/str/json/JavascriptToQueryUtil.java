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
		String json = FileUtil.readAsString(new File(filePath));
        List<Map<String, String>> maps = JavaScriptToJsonUtil.jsToList(json);
        StringBuffer sb=new StringBuffer();
		List<String> list=new ArrayList<>();
        String queryId="tmsp_stock_all_arrive_tile";
        Map<String,List<String>> sqlMap=new LinkedHashMap<>();
        Map<String,List<String>> fieldMap=new LinkedHashMap<>();
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

            if(temp.get("styler")!=null){
                setSqlMap(fieldMap,"styler",field);
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
        String styler="";
        Set<String> keySet = fieldMap.keySet();
        for(String key:keySet){
            List<String> items = fieldMap.get(key);
            if(StringUtils.equalsIgnoreCase("datetime",key)){
                datetimeFormat="let datetimeFormat="+ JSON.toJSONString(items)+";";
            }
            if(StringUtils.equalsIgnoreCase("styler",key)){
                styler="let stylerList="+ JSON.toJSONString(items)+";";
            }
        }


        String tempSql="let titleByQuery =query.initDefineColumns(queryId,listTemplate);\n" +
                "titleByQuery.splice(0, 0, {field:'ck',checkbox:true});\n" ;
        if(StringUtils.isNotBlank(datetimeFormat)){
            tempSql+=datetimeFormat+"\n";
        }
        if(StringUtils.isNotBlank(styler)){
            tempSql+=styler+"\n";
        }
        tempSql+= "titleByQuery.forEach(item=>{\n" ;
        if(StringUtils.isNotBlank(styler)){
            tempSql+= "\t\tif(stylerList.includes(item.field)){\n" +
                    "\t\t\titem.styler=cellStyler;\n" +
                    "\t\t}\n" ;
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
