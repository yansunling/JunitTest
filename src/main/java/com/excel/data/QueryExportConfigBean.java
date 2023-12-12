package com.excel.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryExportConfigBean {
    private String exportType;
    private String cacheId;
    private String dateProcess;
    private String cacheFormat;
}
