package com.javaBuild.po;

import lombok.Data;

@Data
public class BuildConfig {
    private String statusColumn;
    private String importColumn;



    public BuildConfig(String statusColumn, String importColumn) {
        this.statusColumn = statusColumn;
        this.importColumn = importColumn;
    }
}
