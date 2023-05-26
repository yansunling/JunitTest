package com.word.createWord.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryNewWhereParamData {

    /**
     * 查询Id
     */
    private String query_id;

    /**
     * 前台查询条件
     */
    private String param_id="";

    /**
     * 查询参数名称
     */
    private String where_name="";

    public static String SEARCH_QUERY_ID="query_new_where_params_list";
    public static String VERSION="V1.0.0";



    public QueryNewWhereParamData() {
    }

    public QueryNewWhereParamData(String query_id) {
        this.query_id = query_id;
    }


}
