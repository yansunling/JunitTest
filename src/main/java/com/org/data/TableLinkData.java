package com.org.data;

import lombok.Data;

@Data
public class TableLinkData {
    private String main_table;
    private String main_column;
    private String main_org;
    private String item_table;
    private String item_column;

    public TableLinkData(String main_table, String main_column, String main_org, String item_table, String item_column) {
        this.main_table = main_table;
        this.main_column = main_column;
        this.main_org = main_org;
        this.item_table = item_table;
        this.item_column = item_column;
    }
}
