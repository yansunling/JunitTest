package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "到站库存原因")
public enum STOCK_REASON implements Cacheable<STOCK_REASON > {
    DEFAULT("",""),
    STOCK_REASON_11(STOCK_REASON.C_STOCK_REASON_11,"有票无货"),
    STOCK_REASON_12(STOCK_REASON.C_STOCK_REASON_12,"无法接通"),
    STOCK_REASON_13(STOCK_REASON.C_STOCK_REASON_13,"暂不收货"),
    STOCK_REASON_14(STOCK_REASON.C_STOCK_REASON_14,"客户不要货"),
    STOCK_REASON_15(STOCK_REASON.C_STOCK_REASON_15,"异常在库"),
    STOCK_REASON_20(STOCK_REASON.C_STOCK_REASON_20,"等通知"),
    STOCK_REASON_31(STOCK_REASON.C_STOCK_REASON_31,"自营线量不足"),
    STOCK_REASON_33(STOCK_REASON.C_STOCK_REASON_33,"分流不及时"),
    STOCK_REASON_34(STOCK_REASON.C_STOCK_REASON_34,"通知未提"),
    STOCK_REASON_40(STOCK_REASON.C_STOCK_REASON_40,"行包停装"),
    STOCK_REASON_41(STOCK_REASON.C_STOCK_REASON_41,"车皮基数不足"),
    STOCK_REASON_42(STOCK_REASON.C_STOCK_REASON_42,"重泡失衡"),
    STOCK_REASON_43(STOCK_REASON.C_STOCK_REASON_43,"超品类待装整车"),
    STOCK_REASON_44(STOCK_REASON.C_STOCK_REASON_44,"危险品待外发");

    public final static String C_STOCK_REASON_11 = "11";
    public final static String C_STOCK_REASON_12 = "12";
    public final static String C_STOCK_REASON_13 = "13";
    public final static String C_STOCK_REASON_14 = "14";
    public final static String C_STOCK_REASON_15 = "15";
    public final static String C_STOCK_REASON_20 = "20";
    public final static String C_STOCK_REASON_31 = "31";
    public final static String C_STOCK_REASON_33 = "33";
    public final static String C_STOCK_REASON_34 = "34";
    public final static String C_STOCK_REASON_40 = "40";
    public final static String C_STOCK_REASON_41 = "41";
    public final static String C_STOCK_REASON_42 = "42";
    public final static String C_STOCK_REASON_43 = "43";
    public final static String C_STOCK_REASON_44 = "44";


    @EnumValue
    private String code_type;
    private String code_name;
    STOCK_REASON(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,STOCK_REASON> toMap(){
        Map<String, STOCK_REASON> toReturn = new HashMap<>();
        STOCK_REASON[] values = STOCK_REASON.values();
        for(STOCK_REASON val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static STOCK_REASON ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        STOCK_REASON[] values = STOCK_REASON.values();
        for (STOCK_REASON val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        STOCK_REASON[] values = STOCK_REASON.values();
        for (STOCK_REASON val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return STOCK_REASON.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
