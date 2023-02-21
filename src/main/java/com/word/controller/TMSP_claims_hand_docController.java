package com.word.controller;


import com.word.asset.vo.TMSP_claims_hand_docVO;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.runtime.CIPErrorCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/actions/tmsp_claims_hand_doc", name = "返货交接定责")
public class TMSP_claims_hand_docController {






    @RequestMapping(value = "/confirm", name = "定责")
    public CIPResponseMsg confirm(@RequestBody TMSP_claims_hand_docVO param) {
        CIPResponseMsg msg = new CIPResponseMsg();

        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }
    @RequestMapping(value = "/cancel", name = "取消定责")
    public CIPResponseMsg cancel(@RequestBody TMSP_claims_hand_docVO param) {
        CIPResponseMsg msg = new CIPResponseMsg();
        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }







}
