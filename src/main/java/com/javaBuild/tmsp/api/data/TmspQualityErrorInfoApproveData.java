package com.javaBuild.tmsp.api.data;

import com.annotation.MyNotNull;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "审批参数")
public class TmspQualityErrorInfoApproveData {
    @MyNotNull
    @CJ_column(name = "差错上报流程编号")
    private String serial_no;

    @CJ_column(name = "审批意见")
    private String operate_content;
    @CJ_column(name = "奖惩信息")
    private List<TmspQualityPenaltyRewardPO> penalty_reward_list;











}
