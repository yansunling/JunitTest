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
public enum TMSP_OA_STATUS_ALL implements Cacheable<TMSP_OA_STATUS_ALL> {
    DEFAULT("",""),
    OA_STATUS_ALL_APPROVAL("approval","审批中"),
    OA_STATUS_ALL_ARCHIVED("archived","已归档"),
    OA_STATUS_ALL_BACK("back","已退回"),
    OA_STATUS_ALL_INIT("init","待审请"),

  ;




    @EnumValue
    private String code_type;
    private String code_name;
    TMSP_OA_STATUS_ALL(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    public static Map<String, TMSP_OA_STATUS_ALL> toMap(){
        Map<String, TMSP_OA_STATUS_ALL> toReturn = new HashMap<>();
        TMSP_OA_STATUS_ALL[] values = TMSP_OA_STATUS_ALL.values();
        for(TMSP_OA_STATUS_ALL val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static TMSP_OA_STATUS_ALL ofValue(String value){
        return toMap().get(value);
    }
	
	public  TMSP_OA_STATUS_ALL nameToCode(String name){
        TMSP_OA_STATUS_ALL[] values = TMSP_OA_STATUS_ALL.values();
        for(TMSP_OA_STATUS_ALL val:values){
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
