package com.dy.test.appMsg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SendWetch {
    public static void main(String[] args) {
        MMS_MSG_BASEQUEUE message = new MMS_MSG_BASEQUEUE();
        message.setSysId("TMSP");
        message.setDocChannelType("WEAPP");
        String serial_no = UUID.randomUUID().toString();
        message.setBatchNo(serial_no);
        message.setReceiveAddr("33");
        WechatMpTemplateMsg msg = new WechatMpTemplateMsg();
        msg.setAppid("wx777289937b09ff0d");
        msg.setTemplate_id("FJOYxe_kurxfqQ8saLDrc70sLliRuuWHko9KvtOMltc");
        Map<String, JSONObject> data = new HashMap<>();
        //data可以传对象也可以传map
        Map<String, Object> modelData = new HashMap<>();
        modelData.put("keyword1","1");
        for(String key:modelData.keySet()){
            JSONObject field =new JSONObject();
            field.put("value",modelData.get(key));
            data.put(key,field);
        }
        msg.setData(data);
        WechatMpTemplateMiniprogram miniprogram = new WechatMpTemplateMiniprogram();
        miniprogram.setAppid("wxca7189b89ce4cf8c");//小程序appId
        miniprogram.setAppsecret("2be71030bbefb793962f9913b6de9176");//小程序密钥
        miniprogram.setPage("pages/home/home");
        msg.setMiniprogram(miniprogram);
        String smsContent = JSONArray.toJSON(msg).toString();
        message.setDocDesc(smsContent);
        System.out.println(JSONObject.toJSONString(message));
//        sendMsg.sendMsg(message);
    }
}
