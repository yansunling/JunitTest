package com.javaBuild.crmx.controller;

import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.crmx.data.CrmxStoreCustomerAppVO;
import com.javaBuild.crmx.data.CrmxStoreCustomerPO;
import com.javaBuild.crmx.data.NewCIPResponseMsg;
import com.yd.common.runtime.CIPErrorCode;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 销售APP 客户相关接口
 */
@Api("销售app-储备客户")
@Component
@RequestMapping("/store/crm_store_customer")
@RestController
public class CrmxStoreCustomerAppController {
    Logger logger = LoggerFactory.getLogger(getClass());




    @CJ_jcjs_esbMethodInfo(alias = "store_addCustomer", desc = "新增储备客户", author = "T1113")
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    @ResponseBody
    public NewCIPResponseMsg createCustomer(@RequestBody CrmxStoreCustomerAppVO vo) {
        logger.info("新增储备客户传入参数: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }


    @CJ_jcjs_esbMethodInfo(alias = "store_updateCustomer", desc = "更新储备客户", author = "T1113")
    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    @ResponseBody
    public NewCIPResponseMsg updateData(@RequestBody CrmxStoreCustomerAppVO vo) {
        logger.info("客户信息更新参数: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }

    @CJ_jcjs_esbMethodInfo(alias = "store_getCustomer", desc = "获得储备客户详情", author = "T1113")
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public NewCIPResponseMsg getData(@RequestBody CrmxStoreCustomerPO vo) {
        logger.info("获得储备客户详情: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }


}
