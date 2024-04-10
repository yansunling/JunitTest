package com.str.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "性别")
public enum GENDER implements Cacheable<GENDER > {
    DEFAULT("",""),
    GENDER_FEMALE(GENDER.C_GENDER_FEMALE,"女"),
    GENDER_MALE(GENDER.C_GENDER_MALE,"男");

    public final static String C_GENDER_FEMALE = "female";
    public final static String C_GENDER_MALE = "male";


    @EnumValue
    private String code_type;
    private String code_name;
    GENDER(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,GENDER> toMap(){
        Map<String, GENDER> toReturn = new HashMap<>();
        GENDER[] values = GENDER.values();
        for(GENDER val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static GENDER ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        GENDER[] values = GENDER.values();
        for (GENDER val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        GENDER[] values = GENDER.values();
        for (GENDER val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return GENDER.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
