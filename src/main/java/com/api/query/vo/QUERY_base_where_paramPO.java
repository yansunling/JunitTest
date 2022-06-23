package com.api.query.vo;

import lombok.Data;

@Data
public class QUERY_base_where_paramPO {


    /**
     * 查询id
     */
    private String query_id="查询Id";
    /**
     * 字段顺序
     */
    private Integer  order_no = 1;
    /**
     *	 版本号
     */
    private String version="版本号";

    /**
     * 查询参数字段Id
     */
    private String param_id="查询参数ID";

    private String where_name="字段中文名称";

    private String hide_flag="显示/隐藏";


    private String must_flag="是否必填";


    private String ui_control="前端控件类型";


    private String link_query_id="数据域服务";

    private String doamin_id="数据域Id";

    private String default_flag="默认值";

    private String validate_rule="校验规则";


}

