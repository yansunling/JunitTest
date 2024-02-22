package com.http.eventSource;

import com.util.CommonUtil;
import com.yd.common.data.CIPResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class MsgController {
    @Resource(name="msgService")
    private MsgService msgService;

    @ResponseBody
    @RequestMapping(value = "/getMsg", produces="text/event-stream;charset=UTF-8")
    DeferredResult<String> getMsg(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        msgService.removeErrorResponse();
        msgService.getListRes().add(response);
        DeferredResult<String> df = new DeferredResult<String>(3000l);
        if (!response.getWriter().checkError()) {
            Random rand =new Random();
            int num=rand.nextInt(100);
            log.info("num:"+num);
            if(num>50){
                response.getWriter().checkError();
            }
            response.getWriter().write("data:msg: hello, the random num is: " + num + "\n\n");
            response.getWriter().flush();

        }

        return df;
    }

    @RequestMapping(value = "/getTest")
    public CIPResponseMsg importData(HttpServletRequest request) throws Exception {
        return CommonUtil.success();
    }








}
