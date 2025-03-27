package com.str.string;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yd.common.runtime.CIPRuntimeOperator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BadSqlTest {


    public static void main(String[] args) {
        StringBuffer querySql = new StringBuffer().append("SELECT\n" +
                "\tmain.role_plus_id, main.role_plus_name, main.company_id, main.name_space_id \n" +
                "FROM\n" +
                "\tauth_resource_role_plus main\n" +
                "\tLEFT JOIN auth_resource_role_2_plus r2p ON main.company_id = r2p.company_id \n" +
                "\tAND main.name_space_id = r2p.name_space_id \n" +
                "\tAND main.role_plus_id = r2p.role_plus_id\n" +
                "\tLEFT JOIN auth_resource_role r ON r2p.company_id = r.company_id \n" +
                "\tAND r2p.name_space_id = r.name_space_id \n" +
                "\tAND r2p.app_id = r.app_id \n" +
                "\tAND r2p.role_id = r.role_id\n" +
                "\tLEFT JOIN auth_resource_role2fun r2f ON r.company_id = r2f.company_id \n" +
                "\tAND r.name_space_id = r2f.name_space_id \n" +
                "\tAND r.app_id = r2f.app_id \n" +
                "\tAND r.root_node_id = r2f.root_fun_id AND r2f.app_id = ? AND r2f.fun_id = ? ");
        System.out.println(querySql);


    }






    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < badStrs.length; i++) {
            sb.append(badStrs[i]).append(" |");
//            if (str.indexOf(badStrs[i]) >= 0) {
//                return true;
//            }
        }
        System.out.println(sb.toString());
        return false;
    }
}
