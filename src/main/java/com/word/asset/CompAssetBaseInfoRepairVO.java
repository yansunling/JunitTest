package com.word.asset;


import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotNull;
import lombok.Data;

@Data
@CJ_table(name = "资产报修参数对象")
public class CompAssetBaseInfoRepairVO {

    @MyNotNull(message = "资产编号为空")
    @CJ_column(name = "资产编号")
    private String original_asset_num;
    @CJ_column(name = "报修金额")
    private Double repair_amt;
    @MyNotNull(message = "备注为空")
    @CJ_column(name = "备注")
    private String remark;

}
