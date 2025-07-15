package com.org.data;

import lombok.Data;

@Data
public class TableItemData {

    private String item_table;
    private String item_column;

    public TableItemData(String item_table, String item_column) {
        this.item_table = item_table;
        this.item_column = item_column;
    }

    public TableItemData() {
    }
}
