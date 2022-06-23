package com.word.asset;


import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotNull;
import lombok.Data;

@Data
@CJ_table(name = "资产处置参数对象")
public class CompAssetBaseInfoDisposalVO {

    @MyNotNull(message = "资产编号为空")
    @CJ_column(name = "资产编号")
    private String asset_num;

    @MyNotNull(message = "处置类型为空")
    @CJ_column(name = "处置类型")
    private String disposal_type;
    @MyNotNull(message = "备注为空")
    @CJ_column(name = "备注")
    private String remark;



}
