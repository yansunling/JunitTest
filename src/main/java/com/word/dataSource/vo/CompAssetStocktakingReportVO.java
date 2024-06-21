package com.word.dataSource.vo;


import com.other.annotation.MyNotEmpty;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.util.List;

@Data
@CJ_table(name = "手机上报参数对象")
public class CompAssetStocktakingReportVO {

    @MyNotEmpty(message = "流水号为空")
    @CJ_column(name = "流水号")
    private int serial_no;

    @CJ_column(name = "remark")
    private String remark;



    @MyNotEmpty(message = "异常类型为空")
    @CJ_column(name = "异常类型")
    private List<String> except_types;

    @MyNotEmpty(message = "异常说明为空")
    @CJ_column(name = "异常说明")
    private String except_remark;


    @CJ_column(name = "驳回说明")
    private String reject_remark;


    @CJ_column(name = "经度")
    private String longitude;
    @CJ_column(name = "纬度")
    private String latitude;
    @CJ_column(name = "地址名称")
    private String asset_address;

    @CJ_column(name = "异常图片")
    private String photo_url;




}
