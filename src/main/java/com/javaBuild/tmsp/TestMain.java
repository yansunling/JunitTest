package com.javaBuild.tmsp;

public class TestMain {
    public static void main(String[] args) {
        String tableName="tmsp_own_driver_salary";
        String sql="select  CONCAT(\n" +
                "if(c.column_key='PRI','    @TableId\\n',''),\n" +
                "if(c.column_name='version','    @Version\\n',''),\n" +
                "if(c.column_name in('create_time','create_user_id','creator'),'    @TableField(fill = FieldFill.INSERT)\\n',''),\n" +
                "if(c.column_name in('operator','update_time','op_user_id','update_user_id'),'    @TableField(fill = FieldFill.INSERT_UPDATE)\\n',''),\n" +
                "'    @CJ_column(name = \"',c.column_comment,'\")\\n',\n" +
                "'    private ',case when c.data_type in('Integer','int') then 'Integer ' when c.data_type in('bigint') then 'Money ' when c.data_type='decimal ' then 'Double ' when c.data_type='date' or c.data_type='datetime'    then 'Timestamp ' else 'String ' end,c.column_name,';\\n\\n') as filed,\n" +
                "c.data_type,column_name,c.column_comment,c.table_name,tb.table_comment from information_schema.columns c\n" +
                "left join information_schema.tables tb on c.table_name=tb.table_name  and c.table_schema=tb.table_schema\n" +
                "where 1=1\n" +
                "and c.table_schema ='tmsp' " +
                "and tb.table_name in( '"+tableName+"');";
        System.out.println(sql);


    }
}
