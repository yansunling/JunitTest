package com.wx.data;

import lombok.Data;

@Data
public class WechatFile {
    public WechatFile(String media_id) {
        this.media_id = media_id;
    }

    private String media_id;


}
