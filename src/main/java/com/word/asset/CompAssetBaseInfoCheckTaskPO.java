package com.word.asset;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotNull;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "资产盘点任务")
@TableName(value = "comp_asset_base_info_check_task", autoResultMap = true)
public class CompAssetBaseInfoCheckTaskPO extends CIPBasePO {


    @MyNotNull(message = "任务编号为空")
    @TableId
    @CJ_column(name = "任务编号")
    private String task_num;


    @CJ_column(name = "任务名称")
    private String task_name;


    @CJ_column(name = "任务说明")
    private String remark;


    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String create_user_id;

    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;




    @Override
    public Object[] toKeys() {
        return new Object[]{task_num};
    }




}
