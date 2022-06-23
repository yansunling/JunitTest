package com.my.data;


import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
public class CRMX_base_market_messageData {


    /** 
    * 品类
    */
    private String customer_id = "" ;
    private String market_name = "" ;

    private String column_1="";
    private String column_2="";
    private String column_3="";
    private String column_4="";
    private String num="";
    private String remark="";


    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("customer_id","客户编码");
        titleMap.put("market_name","市场名称");
        titleMap.put("column_1","期");
        titleMap.put("column_2","区");
        titleMap.put("column_3","楼");
        titleMap.put("column_4","街");
        titleMap.put("num","号");
        titleMap.put("remark","店铺名称");

    }




}

