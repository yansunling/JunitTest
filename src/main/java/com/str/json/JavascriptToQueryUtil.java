package com.str.json;

import com.yd.utils.common.StringUtils;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<String> sqlList=new ArrayList<>();
		maps.forEach(temp->{
            String name = temp.get("name");
            if(StringUtils.isBlank(name)||temp.get("name")==null){
                return;
            }
            String field = temp.get("field");
            sb.append("'' as "+ temp.get("field")+",-- "+temp.get("name")+"\n");
			if(StringUtils.equals("true",temp.get("hidden"))){
			    list.add(temp.get("field")+"");
//                System.out.println("update query.query_new_columns set hide_flag='Y' where query_id='tmsp_print_ticket_title_list' and ui_column_id='"+field+"';");
            }
			if(temp.get("width")!=null){
                String width = (temp.get("width") + "").replace("px", "");
//                System.out.println("update query.query_new_columns set ui_width='"+width+"' where query_id='tmsp_print_ticket_title_list' and ui_column_id='"+field+"';");
            }
			if(StringUtils.equals("easyui-datetimebox",temp.get("ctrlType")+"")){
//                System.out.println(field);
            }

            if(temp.get("styler")!=null){
//                System.out.println(field);
            }

//            System.out.println("titleMap.put(\""+field+"\",\""+name+"\");");


		});
		System.out.println(sb.toString());

//        System.out.println(StringUtils.join("','",list.toArray()));

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
