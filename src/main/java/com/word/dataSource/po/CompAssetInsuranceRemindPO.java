package com.word.dataSource.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.other.annotation.MyNotEmpty;
import com.yd.common.data.CIPBasePO;
import com.yd.tmsp.constants.ACTIVATE_STATUS;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "保险到期提醒")
@TableName(value = "comp_asset_insurance_remind", autoResultMap = true)
public class CompAssetInsuranceRemindPO extends CIPBasePO {

    @MyNotEmpty(message = "流水号为空")
    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;



    @MyNotEmpty(message = "到期前天数为空")
    @CJ_column(name = "到期前天数")
    private String before_expiration_day;



    @MyNotEmpty(message = "提醒人为空")
    @CJ_column(name = "提醒人",code = "hcm_user_emp_list")
    private String remind_user_id;



    @CJ_column(name = "备注")
    private String remark;




    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }




}
