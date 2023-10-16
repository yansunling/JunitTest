package com.junit.po;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Query_base_columnsVO {
	/**
	 * 查询ID
	 */
	private String query_id;
	
	/**
	 * 创建开始时间
	 */
	private Timestamp create_time_f;

	private String version;

	private String monit_object_1;

	
	
	
	
	
	
	
}
