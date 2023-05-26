package com.word.dataSource.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.po.CrmxSaleApplicationPO;
import com.word.util.CrmxCommonUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/actions/crmx_sale_application")
public class CrmxSaleApplicationController {



    @RequestMapping(value="/addData")
    @CJ_jcjs_esbMethodInfo(alias = "crmx_sale_application_addData", desc = "跨区开发申请" ,author = "T1113")
    public CIPResponseMsg addData(@RequestBody CrmxSaleApplicationPO param){
        log.info("addData param:"+ JSON.toJSONString(param));

        return CrmxCommonUtil.success();
    }
    @RequestMapping(value="/passData")
    @CJ_jcjs_esbMethodInfo(alias = "crmx_sale_application_passData", desc = "审批通过" ,author = "T1113")
    public CIPResponseMsg passData(@RequestBody CrmxSaleApplicationPO param){
        log.info("passData param:"+ JSON.toJSONString(param));
        return CrmxCommonUtil.success();
    }
    @RequestMapping(value="/rejectData")
    @CJ_jcjs_esbMethodInfo(alias = "crmx_sale_application_rejectData", desc = "审批拒绝" ,author = "T1113")
    public CIPResponseMsg rejectData(@RequestBody CrmxSaleApplicationPO param){
        log.info("rejectData param:"+ JSON.toJSONString(param));
        return CrmxCommonUtil.success();
    }

}
