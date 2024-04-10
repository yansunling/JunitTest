package com.str.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "启用状态")
public enum USAGE_STATUS implements Cacheable<USAGE_STATUS> {
    DEFAULT("",""),
    USAGE_STATUS_1("1","启用"),
    USAGE_STATUS_2("2","禁用"),
    USAGE_STATUS_3("3","删除");




    @EnumValue
    private String code_type;
    private String code_name;
    USAGE_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String, USAGE_STATUS> toMap(){
        Map<String, USAGE_STATUS> toReturn = new HashMap<>();
        USAGE_STATUS[] values = USAGE_STATUS.values();
        for(USAGE_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static USAGE_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        USAGE_STATUS[] values = USAGE_STATUS.values();
        for (USAGE_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        USAGE_STATUS[] values = USAGE_STATUS.values();
        for (USAGE_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return USAGE_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
