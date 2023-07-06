package com.javaBuild.tmsp;

import lombok.Data;

@Data
public class ColumnData {
    private String columnId;
    private String columnName;
    private String data_type;

    public ColumnData(String columnId, String columnName, String data_type) {
        this.columnId = columnId;
        this.columnName = columnName;
        this.data_type = data_type;
    }
}
