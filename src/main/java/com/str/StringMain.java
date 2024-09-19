package com.str;

import java.sql.Timestamp;

public class StringMain {
    public static void main(String[] args) {
        StringBuffer querySql = new StringBuffer(" SELECT\n" +
                "\tmain.* \n" +
                "FROM\n" +
                "\tauth_user_userinfo main\n" +
                "LEFT JOIN\n" +
                "\tauth_account_subject_user_info userInfo ON main.user_id = userInfo.user_id \n" +
                "WHERE\n" +
                "\tuserInfo.subject_id = ? \n" +
                "\tAND main.company_id = ? \n" +
                "\tAND main.app_id = '' \n" +
                "\tAND main.name_space_id = ? ");
        System.out.println(querySql);


    }




}
