package com.javaBuild.crmx.data.marketing.data;

import com.dy.components.annotations.CJ_column;

import com.javaBuild.crmx.data.marketing.po.MarketingBaseAddressPO;
import com.javaBuild.crmx.data.marketing.po.MarketingBaseCustVisitPO;
import com.javaBuild.crmx.data.marketing.po.MarketingBaseCustomerPO;
import com.javaBuild.crmx.data.marketing.po.MarketingBaseKeyManPO;
import lombok.Data;

import java.util.List;

@Data
public class MarketingBaseCustomerVO {

    @CJ_column(name = "客户信息")
    private MarketingBaseCustomerPO customer;

    @CJ_column(name = "联系人信息")
    private List<MarketingBaseKeyManPO> keyman_list;


    @CJ_column(name = "地址信息")
    private List<MarketingBaseAddressPO> address_list;

    @CJ_column(name = "拜访记录")
    private List<MarketingBaseCustVisitPO> visit_list;

}
