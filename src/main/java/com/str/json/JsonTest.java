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

public class JsonTest {
	public static void main(String[] args) throws Exception{
		String filePath = "C:\\Users\\yansunling\\Desktop/1.json";
		String json = FileUtil.readAsString(new File(filePath));
        List<Map<String, String>> maps = getMap(json);
        StringBuffer sb=new StringBuffer();
		List<String> list=new ArrayList<>();
        List<String> sqlList=new ArrayList<>();
		maps.forEach(temp->{
            String field = temp.get("field") + "";
            String name = temp.get("name") + "";
            sb.append("'' as "+ temp.get("field")+",-- "+temp.get("name")+"\n");
			if(StringUtils.equals("true",temp.get("hidden")+"")){
			    list.add(temp.get("field")+"");
            }
			if(temp.get("width")!=null){
                String width = (temp.get("width") + "").replace("px", "");
//                System.out.println("update query.query_new_columns set width='"+width+"' where query_id='tmsp_stock_info_title' and ui_column_id='"+field+"';");
            }
//			if(StringUtils.equals("easyui-datetimebox",temp.get("ctrlType")+"")){
//                System.out.println(field);
//            }

            System.out.println("titleMap.put(\""+field+"\",\""+name+"\");");


		});
//		System.out.println(sb.toString());

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
