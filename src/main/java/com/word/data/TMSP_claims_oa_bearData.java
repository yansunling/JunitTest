package com.word.data;

import com.dy.components.annotations.CJ_column;
import com.yd.common.exttype.Money;
import lombok.Data;

@Data
public class TMSP_claims_oa_bearData {

    @CJ_column(name = "责任大区")
    private String business_region_id;
    @CJ_column(name = "责任部门")
    private String resp_org_id;
    @CJ_column(name = "承担比例")
    private Integer bear_rate;
    @CJ_column(name = "责任人工号")
    private String resp_user_id;
    @CJ_column(name = "责任人姓名")
    private String resp_user_name;
    @CJ_column(name = "责任人承担金额")
    private Money resp_bear_amount;

}
