package com.api.query.vo;

import lombok.Data;

import java.util.List;
@Data
public class QUERY_base_jumpPO {
    private String query_id="查询ID";

    private String version="版本";


    private String target_query_id="目标查询Id";

    private String target_version="查询版本";

    private String jump_type="跳转方式";


    private String target_url="目标url地址";


    private String target_name="跳转目标名称";


    private List<QUERY_base_jump_paramPO> params;


}
