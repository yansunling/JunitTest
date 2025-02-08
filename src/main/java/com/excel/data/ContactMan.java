package com.excel.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class ContactMan {

    private String org_name;

    private String org_id;


    private String contact_class;

    private String contact_class_name;

    private String approver;

    private String approver_name;

    private String notice_name;

    private String notice_id;



    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("org_name","客户编码");
        titleMap.put("contact_class_name","目的城市");
        titleMap.put("approver_name","销售人员");
        titleMap.put("notice_name","销售人员");

    }

}
