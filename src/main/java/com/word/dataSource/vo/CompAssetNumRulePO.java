package com.word.dataSource.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

@Data
@CJ_table(name = "资产编号规则")
@TableName(value = "comp_asset_num_rule", autoResultMap = true)
public class CompAssetNumRulePO {


    @CJ_column(name = "流水号")
    private String serial_no;








}
