package com.mybatis.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import com.mybatis.myconstant.constant.DRIVER_TYPE;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(value = "司机短信配置")
@TableName(value = "tmsp_driver_message_config",schema = "tmsp")
public class TMSP_driver_message_configPO extends Model<TMSP_driver_message_configPO> {

	@TableId
	@CJ_column(name = "流水号")
	private String serial_no;

	@CJ_column(name = "机构id")
	private String org_id;


	@CJ_column(name = "大区")
	private String business_region_id;


	@CJ_column(name = "司机类型(自有司机/外请司机)")
	private DRIVER_TYPE driver_type;


	@CJ_column(name = "交接类型")
	private String hand_type;


	@CJ_column(name = "发送状态")
	private String activate_status;


	@CJ_column(name = "版本号")
	private Integer version;


	@TableField(fill = FieldFill.INSERT_UPDATE)
	@CJ_column(name = "修改人")
	private String update_user_id;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@CJ_column(name = "修改时间", type = MysqlColumnTypeEnum.DATETIME)
	private Timestamp update_time;

	@TableField(fill = FieldFill.INSERT)
	@CJ_column(name = "创建人")
	private String create_user_id;

	@TableField(fill = FieldFill.INSERT)
	@CJ_column(name = "创建时间", type = MysqlColumnTypeEnum.DATETIME)
	private Timestamp create_time;


	public Object[] toKeys(){
		return new Object[]{serial_no};
	}
}
