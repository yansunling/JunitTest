package com.api.query.vo;

import lombok.Data;

@Data
public class QUERY_base_tablePO {

	/**
	 * 查询id
	 */
	private String query_id="查询Id";
	/**
	 * 表关联顺序
	 */
	private Integer order_no = 1;
	/**
     *	 版本号
     */
    private String version="版本号";


	/**
	 * 表名称
	 */
	private String table_id="表ID";
	/**
	 * 表别名
	 */
	private String table_alias="表别名";

	/**
	 * 关联模式
	 */
	private String join_mode="关联默认";
	/**
	 * 关联条件
	 */
	private String join_condition="关联条件";


	
	
}
