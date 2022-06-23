package com.my.execel;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class MarketExeclData {
    private String area_name;
    private String street_name;
    private String market_num;

    public static Map<String, String> getExcelTitle() {

        Map<String,String>    titleMap = new LinkedHashMap<String, String>();

        titleMap.put("area_name","楼层");
        titleMap.put("street_name","街道");
        titleMap.put("market_num","号");


        return titleMap;
    }

}
