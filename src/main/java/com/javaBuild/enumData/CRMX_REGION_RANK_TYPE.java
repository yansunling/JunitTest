package com.javaBuild.enumData;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "价差客户类型")
public enum CRMX_REGION_RANK_TYPE {

    RANK_TYPE_1("1","整体"),
    RANK_TYPE_2("2","前100名"),
    RANK_TYPE_3("3","前500名"),
    RANK_TYPE_4("4","前30名"),
    RANK_TYPE_5("5","30-100名"),
    RANK_TYPE_6("6","30-500名"),

  ;




    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_REGION_RANK_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }

    public static Map<String, CRMX_REGION_RANK_TYPE> toMap(){
        Map<String, CRMX_REGION_RANK_TYPE> toReturn = new HashMap<>();
        CRMX_REGION_RANK_TYPE[] values = CRMX_REGION_RANK_TYPE.values();
        for(CRMX_REGION_RANK_TYPE val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static CRMX_REGION_RANK_TYPE ofValue(String value){
        return toMap().get(value);
    }
	
	public  String nameToCode(String name){
        CRMX_REGION_RANK_TYPE[] values = CRMX_REGION_RANK_TYPE.values();
        for(CRMX_REGION_RANK_TYPE val:values){
            if(StringUtils.equals(name,val.code_name)){
                return val.code_type;
            }
        }
        return "";
    }


}
