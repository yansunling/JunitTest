package com.javaBuild.crmx.data.marketing.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class MarketingCustomerTypeTransformVO {



    @CJ_column(name = "客户编码")
    private List<String> customer_list;


    @CJ_column(name = "转化前分类")
    private String before_customer_type;



    @CJ_column(name = "转化后分类")
    private String after_customer_type;




    @CJ_column(name = "转化说明")
    private String remark;




}
