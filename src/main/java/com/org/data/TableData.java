package com.org.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableData {
    private String main_table;
    private String main_column;
    private String main_org;
    private List<TableItemData> items = new ArrayList<>();

    public TableData() {
    }

    public TableData(String main_table, String main_column, String main_org) {
        this.main_table = main_table;
        this.main_column = main_column;
        this.main_org = main_org;
    }
}
