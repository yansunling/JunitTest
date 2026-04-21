package com.str;

import com.yd.common.exttype.Money;
import com.yd.utils.common.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class StringMain {
    public static void main(String[] args) {

        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\tmain.*,\n" +
                "\tsu.name_space_id AS name_space_id,\n" +
                "\tsu.app_id AS app_id,\n" +
                "\tsu.user_id AS user_id,\n" +
                "\tui.user_name AS user_name,\n" +
                "\tui.org_id AS org_id,\n" +
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
                "\tLEFT JOIN auth_user_userinfo ui ON su.company_id = ui.company_id \n" +
                "\tAND su.name_space_id = ui.name_space_id \n" +
                "\tAND su.user_id = ui.user_id\n" +
                "\tLEFT JOIN auth_authorize_app_info app ON su.app_id = app.app_id \n" +
                "\tAND app.company_id = ui.company_id \n" +
                "\tAND app.name_space_id = ui.name_space_id\n" +
                "\tLEFT JOIN auth_authorize_subject_info au ON main.subject_id = au.subject_id ");
        sql.append(" GROUP BY su.company_id, su.name_space_id, su.app_id,  main.subject_id, su.user_id ");
        System.out.println(sql.toString());
    }

    private static String bankProcess(String str){
        if(StringUtils.isNotBlank(str)){
            str=str.trim();
            str.replaceAll("\\s", "");
        }
        return str;
    }



    public static Timestamp getMonthsLaterFirstDay(Timestamp timestamp, int month) {
        // 转换为 LocalDate（丢弃时间部分）
        LocalDate localDate = timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // 增加四个月并设置为 1 日
        LocalDate futureDate = localDate.plusMonths(month).withDayOfMonth(1);

        // 组合为 LocalDateTime（00:00:00）
        LocalDateTime futureDateTime = futureDate.atStartOfDay();

        // 转回 Timestamp
        return Timestamp.valueOf(futureDateTime);
    }




}
