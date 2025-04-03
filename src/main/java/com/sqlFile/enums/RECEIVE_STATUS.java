package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "客户收货状态")
public enum RECEIVE_STATUS implements Cacheable<RECEIVE_STATUS > {
    DEFAULT("",""),
    RECEIVE_STATUS_INIT(RECEIVE_STATUS.C_RECEIVE_STATUS_INIT,"未预约"),
    RECEIVE_STATUS_WAIT(RECEIVE_STATUS.C_RECEIVE_STATUS_WAIT,"客户等待收货"),
    RECEIVE_STATUS_REJECT(RECEIVE_STATUS.C_RECEIVE_STATUS_REJECT,"客户暂不收货");

    public final static String C_RECEIVE_STATUS_INIT = "init";
    public final static String C_RECEIVE_STATUS_WAIT = "wait";
    public final static String C_RECEIVE_STATUS_REJECT = "reject";


    @EnumValue
    private String code_type;
    private String code_name;
    RECEIVE_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,RECEIVE_STATUS> toMap(){
        Map<String, RECEIVE_STATUS> toReturn = new HashMap<>();
        RECEIVE_STATUS[] values = RECEIVE_STATUS.values();
        for(RECEIVE_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static RECEIVE_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        RECEIVE_STATUS[] values = RECEIVE_STATUS.values();
        for (RECEIVE_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        RECEIVE_STATUS[] values = RECEIVE_STATUS.values();
        for (RECEIVE_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return RECEIVE_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
