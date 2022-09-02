package com.word.autWord.query;

import lombok.Data;

@Data
public class QueryNewRegisterData {
    private String query_id;

    private String query_name;

    public static String SEARCH_QUERY_ID="query_new_register_list";
    public static String VERSION="V1.0.0";

    public QueryNewRegisterData() {
    }

    public QueryNewRegisterData(String query_id) {
        this.query_id = query_id;
    }
}
