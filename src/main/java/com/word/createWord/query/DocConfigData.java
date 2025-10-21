package com.word.createWord.query;

import lombok.Data;

import java.util.List;

@Data
public class DocConfigData {
    private List<String> queryList;
    private Class clazz;


    public DocConfigData(List<String> queryList, Class clazz) {
        this.queryList = queryList;
        this.clazz = clazz;
    }
}
