package com.word.dataSource.controller;


import com.alibaba.fastjson.JSON;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.po.CompAssetInsuranceRemindPO;
import com.word.util.AssetUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.tmsp.constants.ACTIVATE_STATUS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/asset/comp_asset_insurance_remind")
public class CompAssetInsuranceRemindController {


    @CJ_jcjs_esbMethodInfo(desc = "保险到期提醒新增", author = "T1113", version = "v1.0")
    @RequestMapping(value="/addData")
    public CIPResponseMsg addData(@RequestBody CompAssetInsuranceRemindPO param){
        log.info("addData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "保险到期提醒修改", author = "T1113", version = "v1.0")
    @RequestMapping(value="/updateData")
    public CIPResponseMsg updateData(@RequestBody CompAssetInsuranceRemindPO param){
        log.info("updateData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "保险到期提醒启用", author = "T1113", version = "v1.0")
    @RequestMapping(value="/enableData")
    public CIPResponseMsg batchUpdate(@RequestBody CompAssetInsuranceRemindPO param){
        log.info("enableData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "保险到期提醒禁用", author = "T1113", version = "v1.0")
    @RequestMapping(value="/disableData")
    public CIPResponseMsg disableData(@RequestBody CompAssetInsuranceRemindPO param){
        log.info("disableData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "保险到期提醒删除", author = "T1113", version = "v1.0")
    @RequestMapping(value="/deleteData")
    public CIPResponseMsg deleteData(@RequestBody CompAssetInsuranceRemindPO param){
        log.info("deleteData param:"+ JSON.toJSONString(param));
        return AssetUtil.success();
    }

}
