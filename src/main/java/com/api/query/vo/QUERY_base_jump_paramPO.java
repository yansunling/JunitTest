package com.api.query.vo;

import lombok.Data;

@Data
public class QUERY_base_jump_paramPO {


    private String param_type="跳转列，还是参数列";

    private String column_id="跳转字段";

    private String column_name="跳转名称";

    private String ref_func="映射函数";


}
