package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "状态")
public enum CRMX_DELIVERY_DECISION implements Cacheable{

    DELIVERY_DECISION_1("1","有"),
    DELIVERY_DECISION_2("2","无"),
    DELIVERY_DECISION_3("3","其他"),

  ;




    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_DELIVERY_DECISION(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }

    public static Map<String, CRMX_DELIVERY_DECISION> toMap(){
        Map<String, CRMX_DELIVERY_DECISION> toReturn = new HashMap<>();
        CRMX_DELIVERY_DECISION[] values = CRMX_DELIVERY_DECISION.values();
        for(CRMX_DELIVERY_DECISION val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static CRMX_DELIVERY_DECISION ofValue(String value){
        return toMap().get(value);
    }
	
	public static String nameToCode(String name){
        CRMX_DELIVERY_DECISION[] values = CRMX_DELIVERY_DECISION.values();
        for(CRMX_DELIVERY_DECISION val:values){
            if(StringUtils.equals(name,val.code_name)){
                return val.code_type;
            }
        }
        return "";
    }


}
