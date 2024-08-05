package com.excel.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class CrmLineSalesman {

    private String customer_id;

    private String last_city;

    private String salesman_id;


    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("customer_id","客户编码");
        titleMap.put("last_city","目的城市");
        titleMap.put("salesman_id","销售人员");

    }

}
