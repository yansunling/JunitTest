package com.javaBuild.tmsp.api.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.tmsp.api.data.TmspQualityErrorInfoData;
import com.word.util.CrmxCommonUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/actions/tmsp_quality_error_info")
public class TmspQualityErrorInfoController {



    @CJ_jcjs_esbMethodInfo(desc = "品质差错申请", author = "T1113", alias = "quality_error_addData")
    @RequestMapping(value="/addData")
    public CIPResponseMsg addData(@RequestBody TmspQualityErrorInfoData param){
        log.info("addData param:"+ JSON.toJSONString(param));

        return CrmxCommonUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(desc = "品质差错二次申请", author = "T1113", alias = "quality_error_addData")
    @RequestMapping(value="/updateData")
    public CIPResponseMsg updateData(@RequestBody TmspQualityErrorInfoData param){
        log.info("updateData param:"+ JSON.toJSONString(param));

        return CrmxCommonUtil.success();
    }

}
