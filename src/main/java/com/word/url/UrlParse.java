package com.word.url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.runtime.CIPErrorCode;

public class UrlParse {
    public static void main(String[] args) {
        String url="http://online-tmsp-api:8080/tmsp-api/prv/tmsp_take_order/checkOrgInfo.do";
        int start = url.indexOf("/", 10);

        System.out.println(url.substring(start));
        CIPResponseMsg msg = new CIPResponseMsg();
        msg.errorCode =0;
        msg.msg = "操作成功";
        String response = JSON.toJSONString(msg, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);

        System.out.println(response);


    }
}
