package com.javaBuild.tmsp.api.data;

import com.other.annotation.MyNotEmpty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;

import lombok.Data;

@Data
@CJ_table(name = "司机预约")
@TableName(value = "tmsp_unload_driver_plan", autoResultMap = true)
public class TmspUnloadDriverPlanPO extends Model<TmspUnloadDriverPlanPO> {



    @MyNotEmpty
    @CJ_column(name = "车牌号")
    private String vehicle_law_id;













}
