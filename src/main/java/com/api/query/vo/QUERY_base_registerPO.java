package com.api.query.vo;

import lombok.Data;

import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.Timestamp;
@Data
public class QUERY_base_registerPO {


	/**
	 * 查询id
	 */
	private String query_id="查询Id";
	/**
	 * 查询名称
	 */
	private String query_name="查询名称";
	 /**
     *	 版本号
     */
    private String version="查询版本";

    private String use_type="使用用途";

	private String call_group="调用方分组";

	private String call_sys="调用方系统";

	/**
	 * 排序
	 */
	private String order_str="排序";
	/**
	 * 分组
	 */
	private String group_str="分组";

	/**
	 * 创建人
	 */
	private String resp_user_id="责任人";

	/**
	 * 数据源ID
	 */
	private String source_id="数据查询数据源";

	/**
	 * sys_id --系统ID
	 */
	private String sys_id="所属系统";

	private String query_group="所属分组";

	private String syn_auth="是否同步auth";


	private String syn_gms="是否同步网关";


	private String multiple_flag="是否是复选框";


	private String detail_flag="双击查看详情";


	private String define_flag="允许自定义列";


	private String down_type="下载方式";

	private String down_file_name="下载文件名称";

	private String limit_flag="是否分页";
	/**
	 * 总数统计
	 */
	private String count_flag="是否自动总数统计";
	/**
	 * 合计功能
	 */
	private String count_action_flag="是否手动总数统计";

	private String count_main_flag="*是否按主表统计总数";
	/**
	 *加载执行函数
	 */
	private String load_javascript="页面加载时";
	/**
	 *查询成功函数
	 */
	private String success_javascript="查询成功时";
	/**
	 *权限限制数据源
	 */
	private String setting_source_id="数据权限数据源";


	public static void main(String[] args) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	
}
