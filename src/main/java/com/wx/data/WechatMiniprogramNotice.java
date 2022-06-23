package com.wx.data;

import com.alibaba.fastjson.JSONArray;

public class WechatMiniprogramNotice {
    private String appid;//小程序appid，必须是与当前小程序应用关联的小程序
    private String page;//点击消息卡片后的小程序页面，仅限本小程序内的页面。该字段不填则消息点击后不跳转。
    private String title;//消息标题，长度限制4-12个汉字
    private String description;//消息描述，长度限制4-12个汉字
    private boolean emphasis_first_item = false;//是否放大第一个content_item
    private JSONArray content_item;//消息内容键值对key-value，最多允许10个item；key长度10个汉字以内，value长度30个汉字以内

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmphasis_first_item() {
        return emphasis_first_item;
    }

    public void setEmphasis_first_item(boolean emphasis_first_item) {
        this.emphasis_first_item = emphasis_first_item;
    }

    public JSONArray getContent_item() {
        return content_item;
    }

    public void setContent_item(JSONArray content_item) {
        this.content_item = content_item;
    }
}
