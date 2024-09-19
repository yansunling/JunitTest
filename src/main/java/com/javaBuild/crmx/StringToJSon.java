package com.javaBuild.crmx;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class StringToJSon {
    public static void main(String[] args) {
        String str="(customer_name=韩世忠转帕尔哈提, send_recv_flag=1, prov_code=650000, city_code=654000, area_code=654004, address=, org_id=350101030902, contactor=韩世忠转帕尔哈提, cust_mobile=18599354143, other_contact=, goods_name=衣服, transfer_city=654004, order_id=701402530, send_org_name=俄罗斯物流)";
        str = str.replace("{", "").replace("}", "").replace("(", "").replace(")", "");
        String[] split = str.split(",");
        Map<String,String> map=new HashMap<>();
        for(String value:split){
            System.out.println(value);
            String[] newStrs = value.trim().split("=");
            if(newStrs.length==2){
                map.put(newStrs[0],newStrs[1]);
            }


        }
        System.out.println(JSON.toJSONString(map));


    }
}
