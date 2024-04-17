package com.javaBuild.po;

import lombok.Data;

@Data
public class ButtonType {
    private String icon;
    private String name;
    private String url;

    public ButtonType(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public ButtonType(String icon, String name, String url) {
        this.icon = icon;
        this.name = name;
        this.url = url;
    }
}
