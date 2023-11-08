package com.javaBuild.crmx.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.crmx.data.marketing.data.MarketingBaseCustomerVO;
import com.javaBuild.crmx.data.marketing.data.MarketingCustSalesmanTransformVO;
import com.javaBuild.crmx.data.marketing.data.MarketingCustomerCancleVO;
import com.javaBuild.crmx.data.marketing.data.MarketingCustomerTypeTransformVO;
import com.javaBuild.crmx.data.marketing.po.MarketingBaseCustomerGetPO;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth/marketing_base_customer")
public class MarketingBaseCustomerController {



    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_addData", desc = "新增客户" ,author = "T1113")
    @RequestMapping(value="/addData")
    public CIPResponseMsg addData(@RequestBody MarketingBaseCustomerVO param){
        log.info("marketing_base_customer addData param:"+ JSON.toJSONString(param));
       return null;
    }
    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_getData", desc = "查看客户" ,author = "T1113")
    @RequestMapping(value="/getData")
    public CIPResponseMsg getData(@RequestBody MarketingBaseCustomerGetPO param){
        log.info("marketing_base_customer getData param:"+ JSON.toJSONString(param));
        return null;
    }




    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_transCustomerType", desc = "客户转化" ,author = "T1113")
    @RequestMapping(value="/transCustomerType")
    public CIPResponseMsg transCustomerType(@RequestBody MarketingCustomerTypeTransformVO param){
        log.info("marketing_base_customer transCustomerType param:"+ JSON.toJSONString(param));
        return null;
    }
    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_transCustSalesman", desc = "销售分配" ,author = "T1113")
    @RequestMapping(value="/transCustSalesman")
    public CIPResponseMsg transCustSalesman(@RequestBody MarketingCustSalesmanTransformVO param){
        log.info("marketing_base_customer transCustSalesman param:"+ JSON.toJSONString(param));
        return null;
    }
   @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_updateData", desc = "修改客户" ,author = "T1113")
   @RequestMapping(value="/updateData")
   public CIPResponseMsg updateData(@RequestBody MarketingBaseCustomerVO param){
       log.info("marketing_base_customer updateData param:"+ JSON.toJSONString(param));
       return null;
   }


    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_needCancel", desc = "需作废（一线）" ,author = "T1113")
    @RequestMapping(value="/needCancel")
    public CIPResponseMsg needCancel(@RequestBody MarketingCustomerCancleVO param){
        log.info("marketing_base_customer needCancel param:"+ JSON.toJSONString(param));
        return null;
    }
    @CJ_jcjs_esbMethodInfo(alias = "marketing_base_customer_cancel", desc = "作废（总部）" ,author = "T1113")
    @RequestMapping(value="/cancel")
    public CIPResponseMsg cancel(@RequestBody MarketingCustomerCancleVO param){
        log.info("marketing_base_customer cancel param:"+ JSON.toJSONString(param));
        return null;
    }


}
