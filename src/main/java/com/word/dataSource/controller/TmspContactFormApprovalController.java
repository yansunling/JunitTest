package com.word.dataSource.controller;


import com.alibaba.fastjson.JSON;
import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;
import com.word.dataSource.po.TmspContactFormApprovalPO;
import com.word.util.OilCardUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/actions/tmsp_contact_form_approval")
public class TmspContactFormApprovalController {


    @CJ_jcjs_esbMethodInfo(alias = "tmsp_contact_form_approval_passData", desc = "审批通过" ,author = "T1113")
    @RequestMapping(value="/passData")
    public CIPResponseMsg addData(@RequestBody TmspContactFormApprovalPO param){
        log.info("addData param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }
    @CJ_jcjs_esbMethodInfo(alias = "tmsp_contact_form_approval_rejectData", desc = "退回" ,author = "T1113")
    @RequestMapping(value="/rejectData")
    public CIPResponseMsg updateData(@RequestBody TmspContactFormApprovalPO param){
        log.info("updateData param:"+ JSON.toJSONString(param));
        return OilCardUtil.success();
    }



}
