package com.str.data;

import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yd.common.exttype.Money;
import com.yd.tmsp.common.jackson.FormatTimeStampSerializer;
import com.yd.tmsp.common.jackson.HcmOrgSerializer;
import com.yd.tmsp.constants.DATA_SOURCE;
import com.yd.tmsp.constants.EXCEPT_CLEAR_STATUS;
import com.yd.tmsp.constants.EXCEPT_CLEAR_TYPE;
import com.yd.tmsp.constants.EXCEPT_TYPE_ID;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TMSP_claims_oa_record_apply_exceptData {

    //数据来源
    @CJ_column(name = "上报环节")
    private DATA_SOURCE data_source;

    @CJ_column(name = "上报部门")
    @JsonSerialize(using = HcmOrgSerializer.class)
    private String except_resp_org_id;

    @CJ_column(name = "上报时间",type = MysqlColumnTypeEnum.DATETIME)
    @JsonSerialize(using = FormatTimeStampSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp except_time;


    @CJ_column(name = "异常类型")
    private EXCEPT_TYPE_ID except_type_id;


    @CJ_column(name = "异常件数",type = MysqlColumnTypeEnum.INT)
    private Integer except_count;

    @CJ_column(name = "核销状态")
    private EXCEPT_CLEAR_STATUS except_clear_status;

    @CJ_column(name = "核销类型")
    private EXCEPT_CLEAR_TYPE except_clear_type;
    @CJ_column(name = "异常货物单价")
    private Money except_goods_price;

    @CJ_column(name = "异常货物价值")
    private Money except_goods_value;

}
