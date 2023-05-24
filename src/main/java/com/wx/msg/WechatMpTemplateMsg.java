package com.wx.msg;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

/**
 * @Description:
 * @Author: wyy
 * @Date: 2021/6/8 下午3:22
 */
@Data
public class WechatMpTemplateMsg {
    private String appid;//公众号appid，必填
    private String template_id;//公众号消息模板id，必填
    private String url;//要跳转的地址，必填
    private WechatMpTemplateMiniprogram miniprogram;//公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系，必填
    private Map<String, JSONObject> data;//模板数据，必填
}
