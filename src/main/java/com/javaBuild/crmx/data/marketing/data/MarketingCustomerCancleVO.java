package com.javaBuild.crmx.data.marketing.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class MarketingCustomerCancleVO {



    @CJ_column(name = "客户编码")
    private List<String> customer_list;

    @CJ_column(name = "作废原因")
    private String remark;




}
