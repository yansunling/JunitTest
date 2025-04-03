package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "出库状态")
public enum OUT_STATUS implements Cacheable<OUT_STATUS > {
    DEFAULT("",""),
    OUT_STATUS_INIT(OUT_STATUS.C_OUT_STATUS_INIT,"未出库"),
    OUT_STATUS_PART(OUT_STATUS.C_OUT_STATUS_PART,"部分出库"),
    OUT_STATUS_OUT(OUT_STATUS.C_OUT_STATUS_OUT,"已出库");

    public final static String C_OUT_STATUS_INIT = "init";
    public final static String C_OUT_STATUS_PART = "part";
    public final static String C_OUT_STATUS_OUT = "out";


    @EnumValue
    private String code_type;
    private String code_name;
    OUT_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,OUT_STATUS> toMap(){
        Map<String, OUT_STATUS> toReturn = new HashMap<>();
        OUT_STATUS[] values = OUT_STATUS.values();
        for(OUT_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static OUT_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        OUT_STATUS[] values = OUT_STATUS.values();
        for (OUT_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        OUT_STATUS[] values = OUT_STATUS.values();
        for (OUT_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return OUT_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
