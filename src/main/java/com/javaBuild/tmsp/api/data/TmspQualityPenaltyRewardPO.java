package com.javaBuild.tmsp.api.data;

import com.annotation.MyNotNull;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.exttype.Money;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "品质差错奖惩表")
@TableName(value = "tmsp_quality_penalty_reward", autoResultMap = true)
public class TmspQualityPenaltyRewardPO extends Model<TmspQualityPenaltyRewardPO> {





    @MyNotNull
    @CJ_column(name = "奖罚类型")
    private String penalty_reward_type;



    @MyNotNull
    @CJ_column(name = "奖罚对象")
    private String quality_user_id;



    @MyNotNull
    @CJ_column(name = "奖罚人所在部门")
    private String quality_org_id;



    @MyNotNull
    @CJ_column(name = "金额(元)")
    private Money amount;






}
