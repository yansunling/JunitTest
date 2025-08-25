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
        String sql = "SELECT IF ( LENGTH(orderp.transfer_city)<2, signin.signin_time, IF ( signin.is_change <> '0', signin.change_time, signin.last_close_time )) AS signinTime ," +
                "signin_count, \n" +
                "user1.user_name AS signerName,\n" +
                " signin.abnormal_type AS abnormalType,\n" +
                " signin.abnormal_number abnormalNumber,\n" +
                " codes.code_name AS abnormalName \n" +
                "FROM tmsp.`tmsp_close_sign` signin \n" +
                " left join tmsp.tmsp_order_profile orderp on orderp.order_id=signin.order_id "+
                " LEFT JOIN tmsp.cip_admin_user user1 ON user1.user_id=signin.signin_user_id " +
                "LEFT JOIN mdm.mdm_ddic_ddic_codes codes ON codes.code_type = signin.abnormal_type AND codes.domain_id = 'abnormal_type' and codes.sys_id='tmsp' \n" +
                "WHERE signin.order_id= ? " +
                "AND signin_status != '0' \n" +
                "AND signin_time IS NOT NULL\n" +
                "and IF ( LENGTH(orderp.transfer_city)<2, signin.signin_time, IF ( signin.is_change <> '0', signin.change_time, signin.last_close_time ))!='1970-01-01 00:00:00'"+
                "ORDER BY signin_time ";
        System.out.println(sql);


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
