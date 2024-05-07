package com.org.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class BossData {

    private String boss_id;

    private String org_name;

    private String boss_name;

    private String org_id;



    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("org_name","机构名称");
        titleMap.put("boss_name","上级名称");
        titleMap.put("boss_id","上级Id");

    }





}
