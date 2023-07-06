package com.javaBuild.po;

import lombok.Data;

@Data
public class ButtonType {
    private String icon;
    private String name;

    public ButtonType(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }
}
