package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "油品")
public enum TMSP_OIL_NAME implements Cacheable<TMSP_OIL_NAME> {
    DEFAULT("",""),
    OIL_NAME_0("0","柴油"),
    OIL_NAME_1("1","汽油"),
    OIL_NAME_2("2","尾气处理液"),
    OIL_NAME_3("3","燃气"),

  ;




    @EnumValue
    private String code_type;
    private String code_name;
    TMSP_OIL_NAME(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    public static Map<String, TMSP_OIL_NAME> toMap(){
        Map<String, TMSP_OIL_NAME> toReturn = new HashMap<>();
        TMSP_OIL_NAME[] values = TMSP_OIL_NAME.values();
        for(TMSP_OIL_NAME val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static TMSP_OIL_NAME ofValue(String value){
        return toMap().get(value);
    }
	
	public  TMSP_OIL_NAME nameToEnum(String name){
        TMSP_OIL_NAME[] values = TMSP_OIL_NAME.values();
        for(TMSP_OIL_NAME val:values){
            if(StringUtils.equals(name,val.code_name)){
                return val;
            }
        }
        return DEFAULT;
    }
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    @Override
    public String codeName() {
        return this.code_name;
    }

}
