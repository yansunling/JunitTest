package com.javaBuild.crmx.controller;

import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.crmx.data.CrmxStoreCustVisitingPO;
import com.javaBuild.crmx.data.NewCIPResponseMsg;
import com.yd.common.runtime.CIPErrorCode;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 销售APP 客户相关接口
 */
@Api("销售app-储备客户拜访")
@RestController
@RequestMapping( "/store/crm_store_cust_visiting")
public class CrmxStoreCustVisitingAppController {
    Logger logger = LoggerFactory.getLogger(getClass());



    @CJ_jcjs_esbMethodInfo(alias = "store_addVisit", desc = "新增储备客户拜访记录", author = "T1113")
    @RequestMapping(value = "/addData", method = RequestMethod.POST)
    @ResponseBody
    public NewCIPResponseMsg createCustomer(@RequestBody CrmxStoreCustVisitingPO vo) {
        logger.info("新增储备客户传入参数: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }


    @CJ_jcjs_esbMethodInfo(alias = "store_updateVisit", desc = "修改储备客户拜访记录", author = "T1113")
    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    @ResponseBody
    public NewCIPResponseMsg updateData(@RequestBody CrmxStoreCustVisitingPO vo) {
        logger.info("修改储备客户拜访记录: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }

    @CJ_jcjs_esbMethodInfo(alias = "store_getVisit", desc = "获得客户拜访记录", author = "T1113")
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    @SneakyThrows
    public NewCIPResponseMsg getData(@RequestBody CrmxStoreCustVisitingPO vo) {
        logger.info("获得储备客户详情: " + JSON.toJSONString(vo));
        NewCIPResponseMsg msg = new NewCIPResponseMsg();
        msg.status = 200;
        msg.message = CIPErrorCode.CALL_SUCCESS.name;

        return msg;
    }


}
