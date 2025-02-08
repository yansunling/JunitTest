package com.str;

public class StringMain {
    public static void main(String[] args) {

        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\tmain.*,\n" +
                "\tsu.name_space_id AS name_space_id,\n" +
                "\tsu.app_id AS app_id,\n" +
                "\tsu.user_id AS user_id,\n" +
                "\tui.user_name AS user_name,\n" +
                "\tui.org_id AS org_id,\n" +
                "\torg.org_name AS org_name,\n" +
                "\tui.is_master_user AS is_master_user,\n" +
                "\tapp.authorize_group_id AS authorize_group_id,\n" +
                //认证信息这里做一下行列转换，不然多一种认证信息数据量就会增加一倍
                "\tGROUP_CONCAT( IF ( au.authorize_type = 'PASSWORD', au.authorize_info, NULL ) ) AS PASSWORD,\n" +
                "\tGROUP_CONCAT( IF ( au.authorize_type = 'JWT', au.authorize_info, NULL ) ) AS jwt,\n" +
                "\tGROUP_CONCAT( IF ( au.authorize_type = 'SMS', au.authorize_info, NULL ) ) AS sms,\n" +
                "\tGROUP_CONCAT( IF ( au.authorize_type = 'WECHART', au.authorize_info, NULL ) ) AS wechat \n" +
                "FROM\n" +
                "\t`auth_account_subject_info` main\n" +
                "\tLEFT JOIN auth_account_subject_user_info su ON main.company_id = su.company_id \n" +
                "\tAND main.subject_id = su.subject_id\n" +
                "\tINNER JOIN auth_user_userinfo ui ON su.company_id = ui.company_id \n" +
                "\tAND su.name_space_id = ui.name_space_id \n" +
                "\tAND su.user_id = ui.user_id AND ui.user_status = '0'\n" +
                "\tLEFT JOIN auth_user_org org ON ui.company_id = org.company_id AND ui.name_space_id = org.name_space_id AND ui.org_id = org.org_id\n" +
                "\tLEFT JOIN auth_authorize_app_info app ON su.app_id = app.app_id \n" +
                "\tAND app.company_id = ui.company_id \n" +
                "\tAND app.name_space_id = ui.name_space_id\n" +
                "\tLEFT JOIN auth_authorize_subject_info au ON main.subject_id = au.subject_id ");
        System.out.println(sql.toString());


    }




}
