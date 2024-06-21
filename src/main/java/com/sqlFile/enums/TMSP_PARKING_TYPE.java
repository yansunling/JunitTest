package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "")
public enum TMSP_PARKING_TYPE implements Cacheable<TMSP_PARKING_TYPE> {
    DEFAULT("",""),
    PARKING_TYPE_0("0","停车"),
    PARKING_TYPE_1("1","路桥"),

  ;




    @EnumValue
    private String code_type;
    private String code_name;
    TMSP_PARKING_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    public static Map<String, TMSP_PARKING_TYPE> toMap(){
        Map<String, TMSP_PARKING_TYPE> toReturn = new HashMap<>();
        TMSP_PARKING_TYPE[] values = TMSP_PARKING_TYPE.values();
        for(TMSP_PARKING_TYPE val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static TMSP_PARKING_TYPE ofValue(String value){
        return toMap().get(value);
    }
	
	public  TMSP_PARKING_TYPE nameToEnum(String name){
        TMSP_PARKING_TYPE[] values = TMSP_PARKING_TYPE.values();
        for(TMSP_PARKING_TYPE val:values){
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
