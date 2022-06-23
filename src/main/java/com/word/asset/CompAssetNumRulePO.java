package com.word.asset;

import com.baomidou.mybatisplus.annotation.*;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotNull;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "资产编号规则")
@TableName(value = "comp_asset_num_rule", autoResultMap = true)
public class CompAssetNumRulePO {


    @CJ_column(name = "流水号")
    private String serial_no;








}
