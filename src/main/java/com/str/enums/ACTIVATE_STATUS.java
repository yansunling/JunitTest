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
public enum ACTIVATE_STATUS implements Cacheable<ACTIVATE_STATUS> {
    DEFAULT("",""),
    ACTIVATE_STATUS_1(ACTIVATE_STATUS.C_ACTIVATE_STATUS_1,"启用"),
    ACTIVATE_STATUS_2(ACTIVATE_STATUS.C_ACTIVATE_STATUS_2,"禁用");

    public final static String C_ACTIVATE_STATUS_1 = "1";
    public final static String C_ACTIVATE_STATUS_2 = "2";


    @EnumValue
    private String code_type;
    private String code_name;
    ACTIVATE_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String, ACTIVATE_STATUS> toMap(){
        Map<String, ACTIVATE_STATUS> toReturn = new HashMap<>();
        ACTIVATE_STATUS[] values = ACTIVATE_STATUS.values();
        for(ACTIVATE_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static ACTIVATE_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        ACTIVATE_STATUS[] values = ACTIVATE_STATUS.values();
        for (ACTIVATE_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        ACTIVATE_STATUS[] values = ACTIVATE_STATUS.values();
        for (ACTIVATE_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return ACTIVATE_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
