package com.javaBuild.crmx;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class StringToJSon {
    public static void main(String[] args) {
        String str="(customer_name=刘萍1, send_recv_flag=1, prov_code=530000, city_code=532500, area_code=532501, address=个旧, org_id=250108041001, contactor=刘萍, cust_mobile=13529475962, other_contact=, goods_name=四件套, transfer_city=532501, order_id=600899355, send_org_name=苏都) ";
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
