package com.yd.crmx.base.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "")
public enum CRMX_COMMON_STATUS implements Cacheable<CRMX_COMMON_STATUS> {
    DEFAULT("",""),
{content}
  ;




    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_COMMON_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    public static Map<String, CRMX_COMMON_STATUS> toMap(){
        Map<String, CRMX_COMMON_STATUS> toReturn = new HashMap<>();
        CRMX_COMMON_STATUS[] values = CRMX_COMMON_STATUS.values();
        for(CRMX_COMMON_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static CRMX_COMMON_STATUS ofValue(String value){
        return toMap().get(value);
    }
	
	public  CRMX_COMMON_STATUS nameToCode(String name){
        CRMX_COMMON_STATUS[] values = CRMX_COMMON_STATUS.values();
        for(CRMX_COMMON_STATUS val:values){
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
