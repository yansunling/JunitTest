package com.api.query.vo;

import lombok.Data;

@Data
public class QUERY_base_wherePO {


    /**
     * 查询id
     */
    private String query_id="查询ID";
    /**
     * 字段顺序
     */
    private Integer  order_no = 1;
    /**
     *	 版本号
     */
    private String version="版本号";


    /**
     * 字段表别名
     */
    private String where_name="字段中文名称";


    private String sql_condition="sql语句条件";


    private String where_type="类型：simple简单查询条件，complex复杂条件设置，auth数据权限设置";

    private String column_id="数据库字段Id";

    private String table_alias="表别名";

    private Integer query_operator=0;


    private String param_id="查询参数字段Id";

    private String default_value="固定值";





	

    
}

