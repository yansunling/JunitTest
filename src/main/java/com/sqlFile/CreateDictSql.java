package com.sqlFile;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDictSql {
    public static void main(String[] args) {
        String name="直达车类、运费类、中转类、配送类、送货费类、卸货费类、转货类、返货类、坏账类";
        String[] list = name.split("、");
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();
        String domainId="contact_class";

        List<Map<String,String>> codes=new ArrayList<>();



        for(int i=0;i<list.length;i++){
            Integer key=i;
            String value=list[i].trim();
//            sql.append("INSERT INTO comp.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
//                    " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");

            sql.append("INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES" +
                    " ('tmsp','"+domainId+"', '"+key+"', '"+value+"','"+key+"',"+i+",'', now(), now(), 'T1113','T1113');\n");
            Map<String,String> code=new HashMap<>();
            code.put("code",key+"");
            code.put("name",value);
            codes.add(code);
        }
        System.out.println(sb.toString());
        System.out.println(sql.toString());

        System.out.println(JSON.toJSONString(codes));

        /*String name="南宁 N,玉林 YL,湛江 ZJ,柳州 LZ,桂林 GL,白色 BS,东兴 DX,凭祥 PX";
        String[] names = name.split(",");
        Integer key=29;
        String domainId="cust_name_main_lines";
        String cipDomainId="main_lines";
        StringBuffer sb=new StringBuffer();
        StringBuffer sql=new StringBuffer();
        StringBuffer html=new StringBuffer();

        for(int i=0;i<names.length;i++){
            String value=names[i].trim();
            String[] values = value.split(" ");
            sb.append("INSERT INTO crm.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                    " ('"+cipDomainId+"', '"+key+"', '"+values[0]+"', now(), now(), 'T1113');\n");

            sql.append("INSERT INTO mdm.mdm_ddic_ddic_codes(sys_id,domain_id, code_type, code_name,code_value,code_order,remark, create_time, update_time, op_user_id,creator) VALUES" +
                    " ('crm','"+domainId+"', '"+key+"', '"+values[0]+"','"+values[1]+"',"+key+",'', now(), now(), 'T1113','T1113');\n");

            html.append("<input type=\"checkbox\" id='"+values[0]+"' name=\"main_lines\" value=\""+key+"\">"+values[0]+"\n");

            key++;
        }
        System.out.println(sb.toString());
        System.out.println(sql.toString());
        System.out.println(html.toString());*/
    }
}
