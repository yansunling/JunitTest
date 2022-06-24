package com.http;


import com.util.CommonUtil;
import com.yd.common.data.CIPResponseMsg;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Component
public class HttFileForwardController {

    @RequestMapping(value = "/forward/file_start")
    public CIPResponseMsg importData(HttpServletRequest request){
        CIPResponseMsg success = CommonUtil.success();

        return success;
    }

}
