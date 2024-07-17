package com.javaBuild.tmsp.api.controller;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.javaBuild.tmsp.api.data.TMSP_except_infoVO;
import com.word.util.CrmxCommonUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 到站app 接口api
 * 2020-03-16
 */
@RestController
@RequestMapping(value = "/prv/api/dzapp", method = RequestMethod.POST)
@Log4j2
public class TmspArriveAppExceptController {



    @CJ_jcjs_esbMethodInfo(desc = "差异核销", author = "T1113", alias = "except_verificate_data")
    @RequestMapping(value="/verificateData",name = "差异核销")
    public CIPResponseMsg verificateData(@RequestBody TMSP_except_infoVO param){
        return CrmxCommonUtil.success();
    }

}
