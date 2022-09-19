package com.word.clazzType;

import java.util.List;

public class ParamEntry {
    //类型
    Class clazz;
    //名称
    String name;
    //说明
    String desc;

    //mock数据
    String testValue;


    List<ParamEntry> children;





    public ParamEntry() {
    }

    public ParamEntry(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public List<ParamEntry> getChildren() {
        return children;
    }

    public void setChildren(List<ParamEntry> children) {
        this.children = children;
    }
}
