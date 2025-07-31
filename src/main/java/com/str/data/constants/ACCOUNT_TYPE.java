package com.str.data.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "账户性质")
public enum ACCOUNT_TYPE implements Cacheable<ACCOUNT_TYPE > {
    DEFAULT("",""),
    ACCOUNT_TYPE_01(ACCOUNT_TYPE.C_ACCOUNT_TYPE_01,"备用金账户"),
    ACCOUNT_TYPE_02(ACCOUNT_TYPE.C_ACCOUNT_TYPE_02,"内部个人账户"),
    ACCOUNT_TYPE_03(ACCOUNT_TYPE.C_ACCOUNT_TYPE_03,"外部个人账户"),
    ACCOUNT_TYPE_04(ACCOUNT_TYPE.C_ACCOUNT_TYPE_04,"内部对公账户"),
    ACCOUNT_TYPE_05(ACCOUNT_TYPE.C_ACCOUNT_TYPE_05,"外部对公账户"),
    ACCOUNT_TYPE_06(ACCOUNT_TYPE.C_ACCOUNT_TYPE_06,"结算账户(财务专用)");

    public final static String C_ACCOUNT_TYPE_01 = "01";
    public final static String C_ACCOUNT_TYPE_02 = "02";
    public final static String C_ACCOUNT_TYPE_03 = "03";
    public final static String C_ACCOUNT_TYPE_04 = "04";
    public final static String C_ACCOUNT_TYPE_05 = "05";
    public final static String C_ACCOUNT_TYPE_06 = "06";


    @EnumValue
    private String code_type;
    private String code_name;
    ACCOUNT_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,ACCOUNT_TYPE> toMap(){
        Map<String, ACCOUNT_TYPE> toReturn = new HashMap<>();
        ACCOUNT_TYPE[] values = ACCOUNT_TYPE.values();
        for(ACCOUNT_TYPE val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static ACCOUNT_TYPE ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        ACCOUNT_TYPE[] values = ACCOUNT_TYPE.values();
        for (ACCOUNT_TYPE val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        ACCOUNT_TYPE[] values = ACCOUNT_TYPE.values();
        for (ACCOUNT_TYPE val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return ACCOUNT_TYPE.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
