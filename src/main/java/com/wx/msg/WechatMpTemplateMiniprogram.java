package com.wx.msg;

import lombok.Data;

/**
 * @Description:
 * @Author: wyy
 * @Date: 2021/6/8 下午3:23
 */
@Data
public class WechatMpTemplateMiniprogram {
    private String appid;//小程序appid，必填
    private String appsecret;//小程序appsecret，必填
    private String page;//未知，好像可以不填
}
