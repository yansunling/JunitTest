package com.sqlFile;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class SqlTest {
    public static void main(String[] args) throws Exception{
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
}
