package com.api.query.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QUERY_base_columnsPO {


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
     * 前台字段id
     */
    private String ui_column_id="前台字段id";

    /**
     * 后台字段
     */
    private String back_column="后台SQL字段";

    /**
     * 字段名称
     */
    private String ui_column_name="字段中文名称";

    private String hide_flag="显示/隐藏";

    private String cache_id="数据域服务";

    private String domain_id="数据域Id";

    private String format_type="数据格式化";

    private String total_flag="是否底部合计";

    private String down_flag="是否下载";

    private String export_type="下载数据类型";

    private String group_flag="分组合并";

    private String detail_flag="查看详情";



	

    
}

