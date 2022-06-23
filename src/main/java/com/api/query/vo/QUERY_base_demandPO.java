package com.api.query.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QUERY_base_demandPO {


    /**
     * 查询id
     */
    private String query_id="查询ID";
    /**
     *	 版本号
     */
    private String version="版本号";


    /**
     * 前台字段id
     */
    private String demand_id="当前版本需求ID";

    /**
     * 前台字段id
     */
    private String demand_name="当前版本需求名称";

    /**
     * 后台字段
     */
    private String task_id="当前版本任务ID";

    /**
     * 字段名称
     */
    private String task_name="当前版本任务名称";


    private String bug_id="当前版本BUG ID";

    private String bug_name="当前版本BUG名称";

    private String remark="当前版本提交备注";



    
}

