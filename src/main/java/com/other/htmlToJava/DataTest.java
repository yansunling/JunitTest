package com.other.htmlToJava;

import com.alibaba.fastjson.JSONObject;
import com.yd.utils.common.StringUtils;

import java.util.List;
import java.util.Map;

public class DataTest {


    public static void main(String[] args) {
        String js="[\n" +
                "\t\t\t{field:'bal_serial_no',name:'系统账单号',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t{field:'should_doc_id',name:'单号',ctrlType:'easyui-validatebox',hidden:true},\n" +
                "\t\t\t{field:'doc_id',name:'应付单号',ctrlType:'easyui-validatebox',hidden:false},\n" +
                "\t\t\t{field: 'should_amt', name: '总金额', ctrlType: 'easyui-numberbox', hidden: false},\n" +
                "\t\t\t{field: 'real_amt', name: '已核销金额', ctrlType: 'easyui-numberbox', hidden: false},\n" +
                "\t\t\t{field: 'bal_amount', name: '未核销金额', ctrlType: 'easyui-numberbox', hidden: false},\n" +
                "\t\t\t{field:'payment_all_amount',name:'申请金额',ctrlType:'easyui-numberbox',hidden:false},\n" +
                "\t\t\t{field:'amount_money',name:'金额',ctrlType:'easyui-numberbox',hidden:true},\n" +
                "\t\t\t{field:'batch_number',name:'批次号',ctrlType:'easyui-validatebox',hidden:true}\n" +
                "\t\t]";

        js=js.replaceAll("field","\"field\"");
        js=js.replaceAll("'true'","true");
        js=js.replaceAll("defaultValue","\"defaultValue\"");
        js=js.replaceAll("mustFlag","\"mustFlag\"");
        js=js.replaceAll("domainId","\"domainId\"");
        js=js.replaceAll("ctrlType","\"ctrlType\"");
        js=js.replaceAll("name:","\"name\":");
        js=js.replaceAll("searchFlag","\"searchFlag\"");
        js=js.replaceAll("ddicTable","\"ddicTable\"");
        js=js.replaceAll("hidden","\"hidden\"");
        js=js.replaceAll("editor","\"editor\"");
        js=js.replaceAll("true","\"\"");
        js=js.replaceAll("false","\"\"");
        js=js.replaceAll("'","\"");
        System.out.println(js);

        List<Map> maps = JSONObject.parseArray(js, Map.class);

        StringBuffer sb=new StringBuffer();
        for(Map map:maps){
            String field=map.get("field")+"";
            if(StringUtils.equals("ck",field)){
                continue;
            }
            //如果是大区小区
            if(StringUtils.equals("big_area",field)||StringUtils.equals("area",field)){
                String name=map.get("name")+"";
                sb.append("/**\n" +
                        "     * "+name+"\n" +
                        "     */\n" +
                        "    @JsonSerialize(using=HcmOrgSerializer.class)\n" +
                        "    private String "+field+" ;\n");

                continue;
            }
            String type="String";
            if(field.endsWith("amt")||field.endsWith("amount")){
                type="Money";
            }
            if(field.endsWith("time")){
                type="Timestamp";
            }

            String name=map.get("name")+"";
            sb.append("/**\n" +
                    "     * "+name+"\n" +
                    "     */\n" +
                    "    private "+type+" "+field+" ;\n");

        }

        System.out.println(sb.toString());










    }
}
