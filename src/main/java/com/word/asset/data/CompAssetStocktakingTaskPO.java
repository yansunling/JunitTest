package com.word.asset.data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "资产盘点任务")
@TableName(value = "comp_asset_stocktaking_task", autoResultMap = true)
public class CompAssetStocktakingTaskPO extends CIPBasePO {
    @TableId
    @CJ_column(name = "任务编号")
    private String task_num;




    @CJ_column(name = "任务名称")
    private String task_name;


    @CJ_column(name = "盘点公司")
    private String asset_company;


    @CJ_column(name = "盘点部门")
    private String asset_dept;


    @CJ_column(name = "盘点部门名称")
    private String asset_dept_name;


    @CJ_column(name = "盘点数量")
    private Integer asset_count;


    @CJ_column(name = "任务说明")
    private String remark;


    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String create_user_id;


    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;


    @CJ_column(name = "完结人")
    private String complete_user_id;

    @CJ_column(name = "完结时间")
    private Timestamp complete_time;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作人")
    private String update_user_id;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作时间")
    private Timestamp update_time;






    @Override
    public Object[] toKeys() {
        return new Object[]{task_num};
    }
}
