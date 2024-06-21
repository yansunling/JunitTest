package com.word.dataSource.vo;


import com.other.annotation.MyNotEmpty;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

@Data
@CJ_table(name = "异常核销")
public class CompAssetStocktakingExceptionClearVO {

    @MyNotEmpty(message = "流水号为空")
    @CJ_column(name = "流水号")
    private String serial_no;

    @MyNotEmpty(message = "核销备注为空")
    @CJ_column(name = "核销备注")
    private String clear_remark;


    @MyNotEmpty(message = "核销结果为空")
    @CJ_column(name = "核销结果")
    private String except_clear;








}
