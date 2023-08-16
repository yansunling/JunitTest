package com.javaBuild.constant;

import java.lang.reflect.Method;

public class CreateSqlByStr {
    public static void main(String[] args) throws Exception{

        String data="潜在,搁置,意向,逼单,试发,合作,挖潜,流失";
        String domainId="sale_progress";
       String[] clazzList=data.split(",");
        StringBuffer sql=new StringBuffer();
       for(int i=0;i<clazzList.length;i++){
           String value = clazzList[i];
           String key=String.valueOf(i+1);
           sql.append("INSERT INTO cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                   " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");
       }
        System.out.println(sql.toString());
    }
}
