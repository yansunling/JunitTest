package com.javaBuild.constant;

import com.javaBuild.po.ButtonType;
import com.yd.utils.common.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CreateAuthSql {
    public static void main(String[] args) {

        String env="uat";
        String fun="tmsp_own_oa_record";
        String sysId="tmsp";
        String parentId=fun+"_list";

        String envType="local";
        String supParent="oilCard";
        String parentName="消费记录";
        String model="ownCar";


        String listSql="INSERT INTO `tmsp`.`foc_plugins_auth_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`)" +
                " VALUES ('"+parentId+"', '"+parentName+"', '', 'ui/view/"+model+"/"+parentId+".html', 'A', now(), now(), 'T1113', '');";
        System.out.println(listSql);

        listSql="INSERT INTO `tmsp`.`foc_plugins_auth_role_2_resource`(`root_node_id`, `res_node_id`, `res_node_sup`, `node_order`, `root_flag`, `res_level`, `leaf_flag`, `create_time`, `update_time`, `operator`) " +
                "VALUES ('default', '"+parentId+"', '"+supParent+"', "+30+", 0, 2, 0, now(),now(), 'T1113');\n";
        System.out.println(listSql);



        Map<String, ButtonType> map=new LinkedHashMap<>();
//        map.put("addData",new ButtonType("icon-add","新增"));
//        map.put("updateData",new ButtonType("icon-edit","修改"));
        map.put("deleteData",new ButtonType("icon-remove","删除"));
//        map.put("enableData",new ButtonType("icon-ok","启用"));
//        map.put("disableData",new ButtonType("icon-cancel","禁用"));
//        map.put("importData",new ButtonType("icon-upload","导入"));
        map.put("exportData",new ButtonType("icon-download","导出"));
//        map.put("appyData",new ButtonType("icon-add","还借支申请"));

        Set<String> keySet = map.keySet();
        int i=1;
        for(String button:keySet){
            String funId=fun+"_"+button;
            ButtonType buttonType = map.get(button);
            if(StringUtils.equals(envType,"auth")){
                String sql="INSERT INTO `auth`.`auth_resource_fun_info`(`company_id`, `name_space_id`, `app_id`, `fun_id`, `fun_name`, `fun_desc`, `fun_type`, `fun_url`, `icon_id`, `target_type`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`)" +
                        " VALUES ('tlwl', '"+env+"', '"+sysId+"', '"+funId+"', '"+buttonType.getName()+"', '"+buttonType.getName()+"', 'B', '/actions/"+fun+"/"+button+".do', '"+buttonType.getIcon()+"', '', '', '', 'now()', 'T1113', 'now()', '', '');";
                System.out.println(sql);
                sql="INSERT INTO `auth`.`auth_resource_role2fun`(`root_fun_id`, `company_id`, `name_space_id`, `app_id`, `fun_id`, `sup_fun_id`, `fun_order`, `fun_level`, `remark`, `creator`, `create_time`, `op_user_id`, `update_time`, `creator_name`, `op_user_name`) " +
                        "VALUES ('default', 'tlwl', '"+env+"', '"+sysId+"', '"+funId+"', '"+parentId+"', 6, "+i+", '', '', now(), '', now(), '', '');";
                System.out.println(sql);
            }else{
                String sql="INSERT INTO `tmsp`.`foc_plugins_auth_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`)" +
                        " VALUES ('"+funId+"', '"+buttonType.getName()+"', '', '/actions/"+fun+"/"+button+".do', 'B', now(), now(), 'T1113', '"+buttonType.getIcon()+"');";
                System.out.println(sql);

                sql="INSERT INTO `tmsp`.`foc_plugins_auth_role_2_resource`(`root_node_id`, `res_node_id`, `res_node_sup`, `node_order`, `root_flag`, `res_level`, `leaf_flag`, `create_time`, `update_time`, `operator`) " +
                        "VALUES ('default', '"+funId+"', '"+parentId+"', "+i*10+", 0, 3, 0, now(),now(), 'T1113');\n";
                System.out.println(sql);

            }



            i++;



        }






    }
}
