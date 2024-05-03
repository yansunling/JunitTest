package com.javaBuild.tmsp;

import com.javaBuild.po.ButtonType;
import com.yd.utils.common.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CreateTmspResoruceSql {
    public static void main(String[] args) {

        String fun="tmsp_contact_form_approval";
        String parentId=fun+"_list";

        String supParent="contact";
        String parentName="联络单审";
        String model="contact";
        int num=1430;

        String listSql="INSERT INTO `tmsp`.`foc_plugins_auth_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`)" +
                " VALUES ('"+parentId+"', '"+parentName+"', '', 'ui/view/"+model+"/"+parentId+".html', 'A', now(), now(), 'T1113', '');";
        System.out.println(listSql);

        listSql="INSERT INTO `tmsp`.`foc_plugins_auth_role_2_resource`(`root_node_id`, `res_node_id`, `res_node_sup`, `node_order`, `root_flag`, `res_level`, `leaf_flag`, `create_time`, `update_time`, `operator`) " +
                "VALUES ('default', '"+parentId+"', '"+supParent+"', "+num+", 0, 2, 0, now(),now(), 'T1113');\n";
        System.out.println(listSql);



        Map<String, ButtonType> map=new LinkedHashMap<>();
//          map.put("addData",new ButtonType("icon-add","申请"));
//          map.put("updateData",new ButtonType("icon-cancel","作废"));
//          map.put("getData",new ButtonType("icon-get","查看"));
//        map.put("addData",new ButtonType("icon-add","新增"));
//        map.put("updateData",new ButtonType("icon-edit","修改"));
//        map.put("deleteData",new ButtonType("icon-remove","删除"));
//        map.put("enableData",new ButtonType("icon-ok","启用","updateData"));
//        map.put("disableData",new ButtonType("icon-cancel","禁用","updateData"));
//        map.put("applyViolation",new ButtonType("icon-add","事故上报"));
//        map.put("completeData",new ButtonType("icon-ok","事故完结"));
//        map.put("applyData",new ButtonType("icon-add","借支"));
//        map.put("repayData",new ButtonType("icon-redo","还借支"));
//        map.put("payData",new ButtonType("icon-edit","申请付款"));
//        map.put("importData",new ButtonType("icon-upload","导入"));

        map.put("passData",new ButtonType("icon-ok","批准"));
        map.put("rejectData",new ButtonType("icon-cancel","退回"));

        map.put("exportData",new ButtonType("icon-download","导出"));
//        map.put("scrapData",new ButtonType("icon-cut","报废"));

        Set<String> keySet = map.keySet();
        int i=1;
        for(String button:keySet){
            String funId=fun+"_"+button;
            ButtonType buttonType = map.get(button);
            String url = buttonType.getUrl();
            if(StringUtils.isBlank(url)){
                url=button;
            }

                String sql="INSERT INTO `tmsp`.`foc_plugins_auth_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`)" +
                        " VALUES ('"+funId+"', '"+buttonType.getName()+"', '', '/actions/"+fun+"/"+url+".do', 'B', now(), now(), 'T1113', '"+buttonType.getIcon()+"');";
                System.out.println(sql);
                sql="INSERT INTO `tmsp`.`foc_plugins_auth_role_2_resource`(`root_node_id`, `res_node_id`, `res_node_sup`, `node_order`, `root_flag`, `res_level`, `leaf_flag`, `create_time`, `update_time`, `operator`) " +
                        "VALUES ('default', '"+funId+"', '"+parentId+"', "+i*10+", 0, 3, 0, now(),now(), 'T1113');\n";
                System.out.println(sql);
            i++;
        }
    }
}
