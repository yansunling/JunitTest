package com.javaBuild.crmx;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCrmDDicSql {
    public static void main(String[] args) {
        String name="货到付款、现付、周期结算、临时欠款";
        String[] list = name.split("、");
        StringBuffer sql=new StringBuffer();
        String domainId="contract_type";
        for(int i=0;i<list.length;i++){
            Integer key=i;
            String value=list[i].trim();
            sql.append("INSERT INTO crm.cip_admin_codes(domain_id, code_type, code_name, create_time, update_time, operator) VALUES" +
                    " ('"+domainId+"', '"+key+"', '"+value+"', now(), now(), 'T1113');\n");


        }
        System.out.println(sql.toString());




    }
}
