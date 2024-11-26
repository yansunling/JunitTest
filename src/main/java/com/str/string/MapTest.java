package com.str.string;



import com.alibaba.fastjson.JSON;

import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Map<String,String> map=new LinkedHashMap<>();
        map.put("运单号 ","order_id");
        map.put("产品类型 ","product_type");
        map.put("品名(客户) ","cust_goods_name");
        map.put("件数(件) ","goods_send_count");
        map.put("重量(kg) ","goods_send_weight");
        map.put("体积(m3) ","goods_send_cube");
        map.put("包装 ","goods_pack");
        map.put("目的城市 ","last_city");
        map.put("中转城市 ","transfer_city_name");
        map.put("收货人 ","recv_contact");
        map.put("交货方式 ","receive_type_name");
        map.put("到付费用合计 ","recv_pay_amount");
        map.put("对外备注","remarkPro");

        List<Map<String,String>> list=new ArrayList<>();
        map.forEach((key,value)->{
            Map<String,String> item=new LinkedHashMap<>();
            item.put("field",value.trim());
            item.put("name",key.trim());
            list.add(item);
        });
        System.out.println(JSON.toJSONString(list));

    }
}
