package com.sqlFile;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class SqlTest {
    public static void main(String[] args) throws Exception{
        String sql= "INSERT INTO `tmsp`.`foc_plugins_auth_role_2_resource`(`root_node_id`, `res_node_id`, `res_node_sup`, `node_order`, `root_flag`, `res_level`, `leaf_flag`, `create_time`, `update_time`, `operator`) VALUES ('default', 'tmsp_attend_load_staff_list', 'attend', 500, 0, 2, 0, '2025-12-18 13:49:49', '2025-12-18 13:49:49', 'T1113');";


        List<MysqlParserUtils.InsertParserResult> parserResults = MysqlParserUtils.parserInsertSql(sql);
        for (MysqlParserUtils.InsertParserResult parserResult : parserResults) {
            String tableName = parserResult.getTableName();
            System.out.println("tableName:"+tableName);
        }



    }
}
