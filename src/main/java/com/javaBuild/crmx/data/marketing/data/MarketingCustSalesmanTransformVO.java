package com.javaBuild.crmx.data.marketing.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.util.List;

@Data
public class MarketingCustSalesmanTransformVO {



    @CJ_column(name = "客户编码")
    private List<String> customer_list;


    @CJ_column(name = "分配前销售")
    private String before_cust_salesman_id;



    @CJ_column(name = "分配后销售")
    private String after_cust_salesman_id;



    @CJ_column(name = "分配说明")
    private String remark;




}
