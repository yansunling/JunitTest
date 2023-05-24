package com.dy.autoTest;

import java.sql.Timestamp;

public class QUERY_base_registerPO {

	/**
	 * 查询id
	 */
	private String query_id;
	/**
	 * 查询名称
	 */
	private String query_name;
	 /**
     *	 版本号
     */
    private String version="V1.0.0";

	/**
	 * 排序
	 */
	private String order_str;
	/**
	 * 分组
	 */
	private String group_str;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 操作人
	 */
	private String op_user_id;
	/**
	 * 操作时间
	 */
	private Timestamp update_time;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Timestamp create_time;
	/**
	 * 数据源ID
	 */
	private String source_id;

	/**
	 * sys_id --系统ID
	 */
	private String sys_id;
	/**
	 * 总数统计
	 */
	private String count_flag;
	/**
	 * 合计功能
	 */
	private String total_action;
	/**
	 * es高亮
	 */
	private String es_highlight;
	
	/**
	 *加载执行函数
	 */
	private String load_javascript;
	/**
	 *查询成功函数
	 */
	private String success_javascript;
	/**
	 *查询配置
	 */
	private String query_config;
	/**
	 *权限限制数据源
	 */
	private String setting_source_id;
	/**
	 *下载文件名称
	 */
	private String down_file_name;
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEs_highlight() {
		return es_highlight;
	}

	public void setEs_highlight(String es_highlight) {
		this.es_highlight = es_highlight;
	}

	public String getQuery_id() {
		return this.query_id;
	}

	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}

	public String getQuery_name() {
		return this.query_name;
	}

	public void setQuery_name(String query_name) {
		this.query_name = query_name;
	}

	public String getOrder_str() {
		return this.order_str;
	}

	public void setOrder_str(String order_str) {
		this.order_str = order_str;
	}

	public String getGroup_str() {
		return this.group_str;
	}

	public void setGroup_str(String group_str) {
		this.group_str = group_str;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOp_user_id() {
		return this.op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	public Timestamp getUpdate_time() {
		return this.update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getCount_flag() {
		return count_flag;
	}

	public void setCount_flag(String count_flag) {
		this.count_flag = count_flag;
	}

	public String getTotal_action() {
		return total_action;
	}

	public void setTotal_action(String total_action) {
		this.total_action = total_action;
	}



	

	public String getLoad_javascript() {
		return load_javascript;
	}

	public String getSuccess_javascript() {
		return success_javascript;
	}

	public String getQuery_config() {
		return query_config;
	}

	public void setLoad_javascript(String load_javascript) {
		this.load_javascript = load_javascript;
	}

	public void setSuccess_javascript(String success_javascript) {
		this.success_javascript = success_javascript;
	}

	public void setQuery_config(String query_config) {
		this.query_config = query_config;
	}
	

	public String getSetting_source_id() {
		return setting_source_id;
	}

	public void setSetting_source_id(String setting_source_id) {
		this.setting_source_id = setting_source_id;
	}

	
	public String getDown_file_name() {
		return down_file_name;
	}

	public void setDown_file_name(String down_file_name) {
		this.down_file_name = down_file_name;
	}





	public Object[] toKeys() {
		return new Object[] { query_id,version };
	}

	@Override
	public String toString() {
		return "QUERY_base_registerPO [query_id=" + query_id + ", query_name=" + query_name + ", version=" + version
				+ ", order_str=" + order_str + ", group_str=" + group_str + ", remark=" + remark + ", op_user_id="
				+ op_user_id + ", update_time=" + update_time + ", creator=" + creator + ", create_time=" + create_time
				+ ", source_id=" + source_id + ", sys_id=" + sys_id + ", count_flag=" + count_flag + ", total_action="
				+ total_action + ", es_highlight=" + es_highlight + "]";
	}
	
}
