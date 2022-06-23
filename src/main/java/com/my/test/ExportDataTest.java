package com.my.test;

import com.alibaba.fastjson.JSONObject;
import com.yd.utils.common.StringUtils;

import java.util.List;
import java.util.Map;

public class ExportDataTest {


    public static void main(String[] args) {
        String js="[{field:'send_org_id_name',name:'发车部门',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'arrive_org_id_name',name:'到达部门',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'vehicle_law_id',name:'车皮号',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'trans_way_name',name:'运输方式',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'send_time',name:'发车时间',ctrlType:'easyui-datetimebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'load_date',name:'装车时间',ctrlType:'easyui-datetimebox',hidden:false},\n" +
                "\t\t\t\t\t{field:'arrive_time',name:'到达时间',ctrlType:'easyui-datetimebox',hidden:false}]";

        js=js.replaceAll("field","\"field\"");

        js=js.replaceAll("defaultValue","\"defaultValue\"");
        js=js.replaceAll("mustFlag","\"mustFlag\"");
        js=js.replaceAll("domainId","\"domainId\"");
        js=js.replaceAll("ctrlType","\"ctrlType\"");
        js=js.replaceAll("name:","\"name\":");
        js=js.replaceAll("searchFlag","\"searchFlag\"");
        js=js.replaceAll("ddicTable","\"ddicTable\"");
        js=js.replaceAll("hidden","\"hidden\"");
        js=js.replaceAll("editor","\"editor\"");
        js=js.replaceAll("false","\"false\"");
        js=js.replaceAll("true","\"true\"");



        js=js.replaceAll("//","");
        js=js.replaceAll("'","\"");
        System.out.println(js);

        List<Map> maps = JSONObject.parseArray(js, Map.class);

        StringBuffer sb=new StringBuffer();
        for(Map map:maps){
            String field=map.get("field")+"".trim();
            if(StringUtils.equals("ck",field)){
                continue;
            }
//            System.out.println(map);
            String name=map.get("name")+"".trim();
            String hidden=map.get("hidden")+"";
            if(StringUtils.equals("true",hidden)){
//                System.out.println(hidden+"======"+field+"==="+name+"--continue");
                continue;
            }
            sb.append("titleMap.put(\""+field+"\", \""+name+"\");\n");
        }
        System.out.println(sb.toString());



    }
}
