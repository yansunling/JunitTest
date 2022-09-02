package com.word.asset.data;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import com.yd.common.exttype.Money;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TMSP_claims_oa_record_apply_exceptData {

    //数据来源
    @CJ_column(name = "上报环节")
    private String data_source;

    @CJ_column(name = "上报部门")
    private String except_resp_org_id;

    @CJ_column(name = "上报时间",type = MysqlColumnTypeEnum.DATETIME)
    private Timestamp except_time;


    @CJ_column(name = "异常类型")
    private String except_type_id;


    @CJ_column(name = "异常件数",type = MysqlColumnTypeEnum.INT)
    private Integer except_count;

    @CJ_column(name = "核销状态")
    private String except_clear_status;

    @CJ_column(name = "核销类型")
    private String except_clear_type;
    @CJ_column(name = "异常货物单价")
    private Money except_goods_price;

    @CJ_column(name = "异常货物价值")
    private Money except_goods_value;

}
