package com.squirrel;

public class AbstractStateContext {
    private Class daoClass;

    private String[] keys;

    private String statusFieldName;

    private String serialNo;


    public Class getDaoClass() {
        return daoClass;
    }

    public void setDaoClass(Class daoClass) {
        this.daoClass = daoClass;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public String getStatusFieldName() {
        return statusFieldName;
    }

    public void setStatusFieldName(String statusFieldName) {
        this.statusFieldName = statusFieldName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
