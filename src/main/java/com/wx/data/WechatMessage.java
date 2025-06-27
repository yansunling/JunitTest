package com.wx.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WechatMessage {
    private String touser;
    private String toparty;
    private String totag;
    private String msgtype = "text";
    private int agentid;
    private WechatText text;//文本消息
//    private WechatImage image;//图片消息
//    private WechatVoice voice;//语音消息
//    private WechatVideo video;//视频消息

    private WechatFile file;

    private WechatFile image;


    private WechatTextcard textcard;//文本卡片消息
//    private WechatNews news;//图文消息
//    private WechatMpnews mpnews;//mpnews类型的图文消息
//    private WechatText markdown;//markdown消息
    private WechatMiniprogramNotice miniprogram_notice;//小程序通知消息
//    private WechatTaskcard taskcard;//任务卡片消息

    private int safe = 0;


}
