package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "CCC是否标识")
public enum IS_NOT implements Cacheable<IS_NOT > {
    DEFAULT("",""),
    IS_NOT_0(IS_NOT.C_IS_NOT_0,"否"),
    IS_NOT_1(IS_NOT.C_IS_NOT_1,"是");

    public final static String C_IS_NOT_0 = "0";
    public final static String C_IS_NOT_1 = "1";


    @EnumValue
    private String code_type;
    private String code_name;
    IS_NOT(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,IS_NOT> toMap(){
        Map<String, IS_NOT> toReturn = new HashMap<>();
        IS_NOT[] values = IS_NOT.values();
        for(IS_NOT val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static IS_NOT ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        IS_NOT[] values = IS_NOT.values();
        for (IS_NOT val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        IS_NOT[] values = IS_NOT.values();
        for (IS_NOT val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public  IS_NOT nameToEnum(String name){
        IS_NOT[] values = IS_NOT.values();
        for(IS_NOT val:values){
            if(StringUtils.equals(name,val.code_name)){
                return val;
            }
        }
        return DEFAULT;
    }



    public String getDomainId() {
        return IS_NOT.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
