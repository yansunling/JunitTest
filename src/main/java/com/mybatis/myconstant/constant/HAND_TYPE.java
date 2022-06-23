package com.mybatis.myconstant.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "交接类型")
public enum HAND_TYPE implements Cacheable<HAND_TYPE > {
    DEFAULT("",""),
    HAND_TYPE_RECEIVE(HAND_TYPE.C_HAND_TYPE_RECEIVE,"接货"),
    HAND_TYPE_EXTERNAL(HAND_TYPE.C_HAND_TYPE_EXTERNAL,"始发外请"),
    HAND_TYPE_OUTGO(HAND_TYPE.C_HAND_TYPE_OUTGO,"外发"),
    HAND_TYPE_SHORTQJ(HAND_TYPE.C_HAND_TYPE_SHORTQJ,"区间短驳"),
    HAND_TYPE_SHORTZN(HAND_TYPE.C_HAND_TYPE_SHORTZN,"站内短驳"),
    HAND_TYPE_DIRECT(HAND_TYPE.C_HAND_TYPE_DIRECT,"干线交接"),
    HAND_TYPE_TURN(HAND_TYPE.C_HAND_TYPE_TURN,"中转"),
    HAND_TYPE_SEND(HAND_TYPE.C_HAND_TYPE_SEND,"送货"),
    HAND_TYPE_TRANS(HAND_TYPE.C_HAND_TYPE_TRANS,"转货交接"),
    HAND_TYPE_BACK(HAND_TYPE.C_HAND_TYPE_BACK,"返货交接");

    public final static String C_HAND_TYPE_RECEIVE = "receive";
    public final static String C_HAND_TYPE_EXTERNAL = "external";
    public final static String C_HAND_TYPE_OUTGO = "outgo";
    public final static String C_HAND_TYPE_SHORTQJ = "shortqj";
    public final static String C_HAND_TYPE_SHORTZN = "shortzn";
    public final static String C_HAND_TYPE_DIRECT = "direct";
    public final static String C_HAND_TYPE_TURN = "turn";
    public final static String C_HAND_TYPE_SEND = "send";
    public final static String C_HAND_TYPE_TRANS = "trans";
    public final static String C_HAND_TYPE_BACK = "back";


    @EnumValue
    private String code_type;
    private String code_name;
    HAND_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,HAND_TYPE> toMap(){
        Map<String, HAND_TYPE> toReturn = new HashMap<>();
        HAND_TYPE[] values = HAND_TYPE.values();
        for(HAND_TYPE val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static HAND_TYPE ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        HAND_TYPE[] values = HAND_TYPE.values();
        for (HAND_TYPE val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        HAND_TYPE[] values = HAND_TYPE.values();
        for (HAND_TYPE val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return HAND_TYPE.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
