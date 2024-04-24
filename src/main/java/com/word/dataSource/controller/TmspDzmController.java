package com.word.dataSource.controller;

import com.dy.components.annotations.CJ_jcjs_esbMethodInfo;

import com.word.dataSource.vo.ResponseMsg;
import com.word.dataSource.vo.TmspDzmCommonVo;
import com.yd.common.runtime.CIPErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 到站app 接口api
 * 2020-03-16
 */
@RestController
@RequestMapping(value = "/dzm/hand_doc/", method = RequestMethod.POST)
@Log4j2
public class TmspDzmController {






    @CJ_jcjs_esbMethodInfo(desc = "到车确认", author = "T1113", alias = "dzm_confirmArrive")
    @RequestMapping(value = "/dzm_confirmArrive", method = RequestMethod.POST, name = "到车确认")
    public ResponseMsg<Void> confirmArrive(@RequestBody TmspDzmCommonVo vo) {
        ResponseMsg<Void> msg= new ResponseMsg<>();
        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }


    @CJ_jcjs_esbMethodInfo(desc = "签收", author = "T1113", alias = "dzm_sign")
    @RequestMapping(value = "/dzm_sign", method = RequestMethod.POST, name = "签收")
    public ResponseMsg<Void> sign(@RequestBody TmspDzmCommonVo vo) {
        ResponseMsg<Void> msg= new ResponseMsg<>();
        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }


    @CJ_jcjs_esbMethodInfo(desc = "异常备注", author = "T1113", alias = "dzm_sign_error")
    @RequestMapping(value = "/dzm_sign_error", method = RequestMethod.POST, name = "异常备注")
    public ResponseMsg<Void> signError(@RequestBody TmspDzmCommonVo vo) {
        ResponseMsg<Void> msg= new ResponseMsg<>();
        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }



}
