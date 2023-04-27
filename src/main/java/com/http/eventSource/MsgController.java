package com.http.eventSource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RestController
public class MsgController {
    @Resource(name="msgService")
    private MsgService msgService;

    @RequestMapping(value = "/getMsg", produces="text/event-stream;charset=UTF-8")
    public DeferredResult<String> getMsg(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        msgService.removeErrorResponse();
        msgService.getListRes().add(response);
        if(!response.getWriter().checkError()){
            response.getWriter().write("data:hello\n\n");
            response.getWriter().flush();
        }
        DeferredResult<String> df = new DeferredResult<String>(60000l);
        return df;
    }

}
