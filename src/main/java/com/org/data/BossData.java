package com.org.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class BossData {

    private String emp_id;

    private String old_boss_name;

    private String new_boss_name;




    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("emp_id","机构名称");
        titleMap.put("old_boss_name","上级名称");
        titleMap.put("new_boss_name","上级Id");

    }





}
