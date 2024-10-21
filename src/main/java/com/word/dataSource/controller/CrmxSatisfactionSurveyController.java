package com.word.dataSource.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.po.CrmxSatisfactionSurveyPO;
import com.word.util.CrmxCommonUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/feedback/crmx_satisfaction_survey")
public class CrmxSatisfactionSurveyController {


    @RequestMapping(value="/addData")
    @CJ_jcjs_esbMethodInfo(alias = "crmx_satisfaction_survey_addData", desc = "满意度调查" ,author = "T1113")
    public CIPResponseMsg addData(@RequestBody CrmxSatisfactionSurveyPO param){
        log.info("crmx_satisfaction_survey addData param:"+ JSON.toJSONString(param));

        return CrmxCommonUtil.success();
    }


}
