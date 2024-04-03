package com.str;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.org.FindOrgSql;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMain {
    public static void main(String[] args) {

        FindOrgSql.OrgData orgData=new FindOrgSql.OrgData("'250109040703'","'25010904030301','25010904030302','25010904030303'","'绍兴线路管理三组'","'柯西新疆收货点','北二区新疆收货点','北八区新疆收货点'");
        orgData.setNewDistrictId("'2501090407'");
        orgData.setNewRegionId("'25010904'");
        String item1="update mpp2.mpp2_apply_approve_trunk set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');";

        List<String> list=new ArrayList<>();
        list.add(item1);
        List<String> newTotalSql=new ArrayList<>();
        list.forEach(item->{
            item=item.replaceAll("'<新机构ID>'",orgData.getNewOrgId());
            item=item.replaceAll("'<老机构ID>'",orgData.getOldOrgId());
            item=item.replaceAll("'<新机构名称>'",orgData.getNewOrgName());
            item=item.replaceAll("'<老机构名称>'",orgData.getOldOrgName());
            item=item.replaceAll("'<新机构小区ID>'",orgData.getNewDistrictId());
            item=item.replaceAll("'<新机构大区ID>'",orgData.getNewRegionId());
            newTotalSql.add(item);
        });

        System.out.println(JSON.toJSONString(newTotalSql));

        System.out.println(matchTables("dctx.s202404020800001712016000242100_wac_busi_stock_detail"));

    }

    public static boolean  matchTables(String input){
       if(input.startsWith("dtcx.s202")){
           return true;
       }
       return  false;
    }



}
