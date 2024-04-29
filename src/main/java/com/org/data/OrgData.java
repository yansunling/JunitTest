package com.org.data;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class OrgData {

    private String newOrgId;
    private String oldOrgId;
    private String newOrgName;
    private String oldOrgName;
    private String newRegionId;
    private String newDistrictId;
    private String oldOrgList;
    private String oldOrgNameList;

    private String oldSingleOrg;
    private String oldDistrictName;
    private String oldRegionName;
    private String newDistrictName;
    private String newRegionName;

    private String oldRegionId;
    private String oldDistrictId;

    public OrgData() {
    }

    public OrgData(String newOrgId, String oldOrgId, String newOrgName, String oldOrgName) {
        this.newOrgId = newOrgId;
        this.oldOrgId = oldOrgId;
        this.newOrgName = newOrgName;
        this.oldOrgName = oldOrgName;
    }


    public static Map<String,String> titleMap=new LinkedHashMap<String,String>();

    static {
        titleMap.put("","");
        titleMap.put("oldOrgId","旧机构编码");
        titleMap.put("oldOrgName","切换前机构名称");
        titleMap.put("oldDistrictName","切换前所属小区");
        titleMap.put("oldRegionName","切换前所属大区");
        titleMap.put("newOrgId","新机构编码");
        titleMap.put("newOrgName","切换后机构名称");
        titleMap.put("newDistrictName","切换后所属小区");
        titleMap.put("newRegionName","切换后所属大区");
    }



}
