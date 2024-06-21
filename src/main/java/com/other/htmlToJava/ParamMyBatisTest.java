package com.other.htmlToJava;

import com.alibaba.fastjson.JSONObject;
import com.yd.utils.common.StringUtils;

import java.util.List;
import java.util.Map;

public class ParamMyBatisTest {


    public static void main(String[] args) {
        String js="[{field:'load_date_begin',name:'装车日期(开始)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'>=',mustFlag:'N',defaultValue:''},\n" +
                "\t{field:'load_date_end',name:'装车日期(结束)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'<=',mustFlag:'N',defaultValue:''},\n" +
                "\t{field:'send_time_begin',name:'发车时间(开始)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'>=',mustFlag:'N',defaultValue:''},\n" +
                "\t{field:'send_time_end',name:'发车时间(结束)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'<=',mustFlag:'N',defaultValue:''},\n" +
                "\t{field:'arrive_time_begin',name:'到达时间(开始)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'>=',mustFlag:'N',defaultValue:''},\n" +
                "\t{field:'arrive_time_end',name:'到达时间(结束)',ctrlType:'easyui-datetimebox',ddicTable:'',domainId:'',searchFlag:'<=',mustFlag:'N',defaultValue:''}]";

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
        String subfiex="begin";
        StringBuffer sb=new StringBuffer("<where>\n");
        String connect="";
        for(Map map:maps){
            String field=map.get("field")+"";
            String name=map.get("name")+"";
            if((StringUtils.containsIgnoreCase(field,"_time")||StringUtils.containsIgnoreCase(field,"_date"))&&StringUtils.containsIgnoreCase(field,"_begin")){
                field=field.replaceAll("_begin","");
                sb.append(" <!-- "+name+"-->\n" +
                        "            <if test=\"cond."+field+"_begin!=null\">\n" +
                        "                <![CDATA[\n" +
                        "                    and main."+field+">=#{cond."+field+"_begin}\n" +
                        "                ]]>\n" +
                        "            </if>");

                continue;

            }

            if((StringUtils.containsIgnoreCase(field,"_time")||StringUtils.containsIgnoreCase(field,"_date"))&&StringUtils.containsIgnoreCase(field,"_end")){
                field=field.replaceAll("_end","");
                sb.append(" <!-- "+name+"-->\n" +
                        "            <if test=\"cond."+field+"_end!=null\" >\n" +
                        "                <![CDATA[\n" +
                        "                and main."+field+"<=#{cond."+field+"_end}\n" +
                        "              ]]>\n" +
                        "            </if>");

                continue;
            }


            //大区
            if(StringUtils.equals(field,"big_area")){
                sb.append(" <!-- 大区 -->\n" +
                        "            <if test=\"cond.big_area!=null and cond.big_area!=''\">\n" +
                        "               and org.business_region_id=#{cond.big_area}\n" +
                        "            </if>");

                continue;

            }
            //小区
            if(StringUtils.equals(field,"area")){
                sb.append(" <!-- 小区 -->\n" +
                        "            <if test=\"cond.area!=null and cond.area!=''\">\n" +
                        "               and org.business_district_id=#{cond.area}\n" +
                        "            </if>");

                continue;
            }

            sb.append("<!-- "+name+" -->\n" +
                    "            <if test=\"cond."+field+"!=null and cond."+field+"!=''\">\n" +
                    "               "+connect+" main."+field+"=#{cond."+field+"}\n" +
                    "            </if>\n");

            connect="and";
        }
        sb.append("</where>\n");

        System.out.println(sb.toString());

    }
}
