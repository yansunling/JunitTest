package com.word.asset;



import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.interfaces.MyNotEmpty;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "进度跟踪参数对象")
public class CompAssetStocktakingReportVO {

    @MyNotEmpty(message = "流水号为空")
    @CJ_column(name = "流水号")
    private String serial_no;


    @MyNotEmpty(message = "异常类型为空")
    @CJ_column(name = "异常类型")
    private List<String> except_types;

    @MyNotEmpty(message = "异常说明为空")
    @CJ_column(name = "异常说明")
    private String except_remark;


    @CJ_column(name = "驳回说明")
    private String reject_remark;




}
