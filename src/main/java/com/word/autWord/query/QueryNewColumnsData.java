package com.word.autWord.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryNewColumnsData {

    /**
     * 查询Id
     */
    private String query_id;

    /**
     * 前台查询条件
     */
    private String ui_column_id="";

    /**
     * 查询参数名称
     */
    private String ui_column_name="";

    public static String SEARCH_QUERY_ID="query_new_columns_list";
    public static String VERSION="V1.0.0";



    public QueryNewColumnsData() {
    }

    public QueryNewColumnsData(String query_id) {
        this.query_id = query_id;
    }


}
