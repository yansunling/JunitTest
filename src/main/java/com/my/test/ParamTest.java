package com.my.test;

import com.alibaba.fastjson.JSONObject;
import com.yd.utils.common.StringUtils;

import java.util.List;
import java.util.Map;

public class ParamTest {


    public static void main(String[] args) {
        String js="[{field:'send_time',name:'发车时间',ctrlType:'easyui-datetimebox',hidden:false},\n" +
                "\t\t\t{field:'load_date',name:'装车时间',ctrlType:'easyui-datetimebox',hidden:false},\n" +
                "\t\t\t{field:'arrive_time',name:'到达时间',ctrlType:'easyui-datetimebox',hidden:false},]";

        js=js.replaceAll("field","\"field\"");
        js=js.replaceAll("defaultValue","\"defaultValue\"");
        js=js.replaceAll("mustFlag","\"mustFlag\"");
        js=js.replaceAll("domainId","\"domainId\"");
        js=js.replaceAll("ctrlType","\"ctrlType\"");
        js=js.replaceAll("name:","\"name\":");
        js=js.replaceAll("searchFlag","\"searchFlag\"");
        js=js.replaceAll("ddicTable","\"ddicTable\"");
        js=js.replaceAll("'","\"");
//        System.out.println(js);

        List<Map> maps = JSONObject.parseArray(js, Map.class);

        StringBuffer sb=new StringBuffer();
        String subfiex="_begin";
        for(Map map:maps){
            String field=map.get("field")+"";
            String name=map.get("name")+"";
            if(field.endsWith("time")){
                field=field+subfiex;
                subfiex="_end";
            }
            sb.append("/**\n" +
                    "     * "+name+"\n" +
                    "     */\n" +
                    "    private String "+field+" ;\n");

        }

        System.out.println(sb.toString());










    }
}
