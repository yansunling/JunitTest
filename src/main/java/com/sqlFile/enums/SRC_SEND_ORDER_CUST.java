package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "客户配载运单数据来源")
public enum SRC_SEND_ORDER_CUST implements Cacheable<SRC_SEND_ORDER_CUST> {
    DEFAULT("",""),
    SRC_SEND_ORDER_CUST_HAND(SRC_SEND_ORDER_CUST.C_SRC_SEND_ORDER_CUST_HAND,"交接"),
    SRC_SEND_ORDER_CUST_ORDER(SRC_SEND_ORDER_CUST.C_SRC_SEND_ORDER_CUST_ORDER,"运单补录"),
    SRC_SEND_ORDER_CUST_EXCEPT(SRC_SEND_ORDER_CUST.C_SRC_SEND_ORDER_CUST_EXCEPT,"异常补齐"),
    SRC_SEND_ORDER_CUST_PDA(SRC_SEND_ORDER_CUST.C_SRC_SEND_ORDER_CUST_PDA,"pda出库");

    public final static String C_SRC_SEND_ORDER_CUST_HAND = "hand";
    public final static String C_SRC_SEND_ORDER_CUST_ORDER = "order";
    public final static String C_SRC_SEND_ORDER_CUST_EXCEPT = "except";
    public final static String C_SRC_SEND_ORDER_CUST_PDA = "pda";


    @EnumValue
    private String code_type;
    private String code_name;
    SRC_SEND_ORDER_CUST(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String, SRC_SEND_ORDER_CUST> toMap(){
        Map<String, SRC_SEND_ORDER_CUST> toReturn = new HashMap<>();
        SRC_SEND_ORDER_CUST[] values = SRC_SEND_ORDER_CUST.values();
        for(SRC_SEND_ORDER_CUST val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static SRC_SEND_ORDER_CUST ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        SRC_SEND_ORDER_CUST[] values = SRC_SEND_ORDER_CUST.values();
        for (SRC_SEND_ORDER_CUST val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        SRC_SEND_ORDER_CUST[] values = SRC_SEND_ORDER_CUST.values();
        for (SRC_SEND_ORDER_CUST val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return SRC_SEND_ORDER_CUST.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
