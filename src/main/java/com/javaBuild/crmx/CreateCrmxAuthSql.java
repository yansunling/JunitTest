package com.javaBuild.crmx;

import com.javaBuild.po.ButtonType;
import com.yd.utils.common.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CreateCrmxAuthSql {
    public static void main(String[] args) {

        String env="uat";
        String fun="crm_org_dept_config_list";
        String sysId="crm";
        String mainType="菜单";
        String functionName="特殊配置";
        if(fun.endsWith("_list")){
            fun=fun.replaceAll("_list","");
        }
        if(StringUtils.equalsIgnoreCase(mainType,"页面")){
            mainType="P";
        }else{
            mainType="A";
        }
        String funId=fun+"_list";
        String parentFun="crm_org_dept_ext_list";
        String listUrl="../crmx/ui/view/base/"+funId+".html?actionId="+funId;
        int num=10;


        String listSql="INSERT INTO `auth`.`auth_resource_fun_info`(`company_id`, `name_space_id`, `app_id`, `fun_id`, `fun_name`, `fun_desc`, `fun_type`, `fun_url`, `icon_id`, `target_type`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`)" +
                " VALUES ('tlwl', '"+env+"', '"+sysId+"', '"+funId+"', '"+functionName+"', '"+functionName+"', '"+mainType+"', '"+listUrl+"', '', 'iframe-tab', '', 'T1113', now(), 'T1113', now(), '', '');";
        System.out.println(listSql);
        listSql="INSERT INTO `auth`.`auth_resource_role2fun`(`root_fun_id`, `company_id`, `name_space_id`, `app_id`, `fun_id`, `sup_fun_id`, `fun_order`, `fun_level`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`) " +
                "VALUES ('default', 'tlwl', '"+env+"', '"+sysId+"', '"+funId+"', '"+parentFun+"', "+num+", 1, '', '', now(), '', now(), '', '');";
        System.out.println(listSql);
        System.out.println();


        Map<String, ButtonType> map=new LinkedHashMap<>();
        map.put("addData",new ButtonType("icon-add","新增"));
//          map.put("addData",new ButtonType("icon-upload","上传证件"));
//          map.put("getData",new ButtonType("icon-get","查看"));
          map.put("updateData",new ButtonType("icon-edit","修改"));
          map.put("deleteData",new ButtonType("icon-remove","删除"));

        map.put("enableData",new ButtonType("icon-ok","启用"));
        map.put("disableData",new ButtonType("icon-cancel","禁用"));
//        map.put("importData",new ButtonType("icon-upload","导入"));
//        map.put("exportData",new ButtonType("icon-download","导出"));
//        map.put("appyData",new ButtonType("icon-add","还借支申请"));

        Set<String> keySet = map.keySet();
        int i=1;
        for(String button:keySet){
            String childFunId=fun+"_"+button;
            ButtonType buttonType = map.get(button);
            String action="/actions/"+fun+"/"+button+".do";
            if(StringUtils.equals("enableData",button)||StringUtils.equals("enableData",button)){
                action="/actions/"+fun+"/updateStatus.do";
            }
            String sql="INSERT INTO `auth`.`auth_resource_fun_info`(`company_id`, `name_space_id`, `app_id`, `fun_id`, `fun_name`, `fun_desc`, `fun_type`, `fun_url`, `icon_id`, `target_type`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`)" +
                    " VALUES ('tlwl', '"+env+"', '"+sysId+"', '"+childFunId+"', '"+buttonType.getName()+"', '"+buttonType.getName()+"', 'B', '"+action+"', '"+buttonType.getIcon()+"', '', '', 'T1113', now(), 'T1113', now(), '', '');";
            System.out.println(sql);
            sql="INSERT INTO `auth`.`auth_resource_role2fun`(`root_fun_id`, `company_id`, `name_space_id`, `app_id`, `fun_id`, `sup_fun_id`, `fun_order`, `fun_level`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`) " +
                    "VALUES ('default', 'tlwl', '"+env+"', '"+sysId+"', '"+childFunId+"', '"+funId+"', "+i+", 2,'', '', now(), '', now(), '', '');";
            System.out.println(sql);
            i++;



        }






    }
}
