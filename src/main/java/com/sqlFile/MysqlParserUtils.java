package com.sqlFile;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLNullExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.yd.common.exception.CIPServiceException;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.utils.common.JSONUtils;
import com.yd.utils.common.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fukaijian
 * @description
 * @since 2021-08-02
 **/
public class MysqlParserUtils {

    /**
     * 将insert语句解析成表名和key-value值的map。支持多个sql；支持一个sql插入多条，结果会分成多条
     * 如insert into tablename(id, name) values ('1', 'aaa'), ('2','bbb')会拆分成两个对象
     * @param sql
     * @return
     */
    public static List<InsertParserResult> parserInsertSql(String sql){
        String[] insertSqls = sql.split(";");

        List<InsertParserResult> result = new ArrayList<>();
        for (int i = 1; i <= insertSqls.length; i++) {
            String insertSql = insertSqls[i-1];
            if(StringUtils.isBlank(insertSql)){
                continue;
            }
            MySqlStatementParser parser = new MySqlStatementParser(insertSql);
            SQLStatement statement = parser.parseStatement();
            if(!(statement instanceof MySqlInsertStatement)){
                throw new CIPServiceException(new CIPErrorCode(200013, "第{0}个SQL[{1}]非标准的MYSQL Insert语句！", i, insertSql));
            }
            //解析成insert语句。如果是一个insert插入多条语句，insert.getValuesList()会是多个，否则为1个
            MySqlInsertStatement insert = (MySqlInsertStatement)statement;
            String tableName = insert.getTableName().getSimpleName().replaceAll("`","");
            List<SQLExpr> columns = insert.getColumns();
            List<SQLInsertStatement.ValuesClause> valuesList = insert.getValuesList();
            for (int j = 0; j < valuesList.size(); j++) {
                List<SQLExpr> values = valuesList.get(j).getValues();
                Map<String, String> keyValue = new HashMap<>();
                for (int k = 0; k < values.size(); k++) {
                    String columnName = columns.get(k).toString().replaceAll("`", "");
                    SQLExpr valueExpr = values.get(k);
                    String value = null;
                    if(!"NULL".equals(valueExpr.toString())){
                        value = valueExpr.toString().replaceAll("'", "");
                    }
                    keyValue.put(columnName, value);
                }
                InsertParserResult ipr = new InsertParserResult(tableName, keyValue);
                result.add(ipr);
            }
        }
        return result;
    }

    public static class InsertParserResult{
        private String tableName;

        private Map<String, String> keyValues;

        public InsertParserResult() {
        }

        public InsertParserResult(String tableName, Map<String, String> keyValues) {
            this.tableName = tableName;
            this.keyValues = keyValues;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Map<String, String> getKeyValues() {
            return keyValues;
        }

        public void setKeyValues(Map<String, String> keyValues) {
            this.keyValues = keyValues;
        }
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO `mms`.`cip_admin_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`) VALUES ('cip_admin_addBtn_form_new', '添加动作资源', NULL, 'ui/view/admin/cip_admin_addBtn_form_new.html?actionId=cip_admin_addBtn_form_new', 'P', '2016-06-28 17:05:38', NULL, NULL, NULL);\n" +
                "INSERT INTO `mms`.`cip_admin_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`) VALUES ('cip_admin_addMegroup_form_new', '添加菜单组', NULL, 'ui/view/admin/cip_admin_addMegroup_form_new.html?actionId=cip_admin_addMegroup_form_new', 'P', '2016-06-28 17:21:51', NULL, NULL, NULL);\n" +
                "INSERT INTO `mms`.`cip_admin_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`) VALUES ('cip_admin_addMenu_form_new', '添加菜单项', NULL, 'ui/view/admin/cip_admin_addMenu_form_new.html?actionId=cip_admin_addMenu_form_new', 'P', '2016-06-28 17:20:50', NULL, NULL, NULL);\n";
        String sql2 = "INSERT INTO `mms`.`cip_admin_resource`(`resource_id`, `resource_name`, `resource_desc`, `sys_uri`, `resource_type`, `create_time`, `update_time`, `operator`, `icon_id`) VALUES ('cip_admin_addBtn_form_new', '添加动作资源', NULL, 'ui/view/admin/cip_admin_addBtn_form_new.html?actionId=cip_admin_addBtn_form_new', 'P', '2016-06-28 17:05:38', NULL, NULL, NULL), ('cip_admin_addMegroup_form_new', '添加菜单组', NULL, 'ui/view/admin/cip_admin_addMegroup_form_new.html?actionId=cip_admin_addMegroup_form_new', 'P', '2016-06-28 17:21:51', NULL, NULL, NULL), ('cip_admin_addMenu_form_new', '添加菜单项', NULL, 'ui/view/admin/cip_admin_addMenu_form_new.html?actionId=cip_admin_addMenu_form_new', 'P', '2016-06-28 17:20:50', NULL, NULL, NULL);\n";
        List<InsertParserResult> results = parserInsertSql(sql);
        List<InsertParserResult> results2 = parserInsertSql(sql);
        results.forEach(k-> System.out.println(JSONUtils.convertObject2Json(k)));
        results2.forEach(k-> System.out.println(JSONUtils.convertObject2Json(k)));

    }
}

