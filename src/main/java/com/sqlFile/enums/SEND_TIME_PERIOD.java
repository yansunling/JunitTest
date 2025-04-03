package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "送货时段")
public enum SEND_TIME_PERIOD implements Cacheable<SEND_TIME_PERIOD > {
    DEFAULT("",""),
    SEND_TIME_PERIOD_NIGHT(SEND_TIME_PERIOD.C_SEND_TIME_PERIOD_NIGHT,"晚上"),
    SEND_TIME_PERIOD_AM(SEND_TIME_PERIOD.C_SEND_TIME_PERIOD_AM,"上午"),
    SEND_TIME_PERIOD_DAY(SEND_TIME_PERIOD.C_SEND_TIME_PERIOD_DAY,"白天"),
    SEND_TIME_PERIOD_PM(SEND_TIME_PERIOD.C_SEND_TIME_PERIOD_PM,"下午");

    public final static String C_SEND_TIME_PERIOD_NIGHT = "night";
    public final static String C_SEND_TIME_PERIOD_AM = "am";
    public final static String C_SEND_TIME_PERIOD_DAY = "day";
    public final static String C_SEND_TIME_PERIOD_PM = "pm";


    @EnumValue
    private String code_type;
    private String code_name;
    SEND_TIME_PERIOD(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,SEND_TIME_PERIOD> toMap(){
        Map<String, SEND_TIME_PERIOD> toReturn = new HashMap<>();
        SEND_TIME_PERIOD[] values = SEND_TIME_PERIOD.values();
        for(SEND_TIME_PERIOD val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static SEND_TIME_PERIOD ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        SEND_TIME_PERIOD[] values = SEND_TIME_PERIOD.values();
        for (SEND_TIME_PERIOD val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        SEND_TIME_PERIOD[] values = SEND_TIME_PERIOD.values();
        for (SEND_TIME_PERIOD val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return SEND_TIME_PERIOD.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
