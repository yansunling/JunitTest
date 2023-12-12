package com.squirrel.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "约车单状态")
public enum BOOK_DOC_STATUS implements Cacheable<BOOK_DOC_STATUS > {
    DEFAULT("",""),
    BOOK_DOC_STATUS_1(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_1,"已约车待派车"),
    BOOK_DOC_STATUS_2(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_2,"已退回"),//对应退回完结
    BOOK_DOC_STATUS_3(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_3,"已派车待到达"),
    BOOK_DOC_STATUS_4(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_4,"已完结"),//对应正常完结
    BOOK_DOC_STATUS_5(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_5,"已作废"),//对应作废完结
    BOOK_DOC_STATUS_6(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_6,"接货失败"),//对应接货失败完结
    BOOK_DOC_STATUS_7(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_7,"接货成功"),
    BOOK_DOC_STATUS_8(BOOK_DOC_STATUS.C_BOOK_DOC_STATUS_8,"已开单");

    public final static String C_BOOK_DOC_STATUS_1 = "1";
    public final static String C_BOOK_DOC_STATUS_2 = "2";
    public final static String C_BOOK_DOC_STATUS_3 = "3";
    public final static String C_BOOK_DOC_STATUS_4 = "4";
    public final static String C_BOOK_DOC_STATUS_5 = "5";
    public final static String C_BOOK_DOC_STATUS_6 = "6";
    public final static String C_BOOK_DOC_STATUS_7 = "7";
    public final static String C_BOOK_DOC_STATUS_8 = "8";


    @EnumValue
    private String code_type;
    private String code_name;
    BOOK_DOC_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,BOOK_DOC_STATUS> toMap(){
        Map<String, BOOK_DOC_STATUS> toReturn = new HashMap<>();
        BOOK_DOC_STATUS[] values = BOOK_DOC_STATUS.values();
        for(BOOK_DOC_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static BOOK_DOC_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        BOOK_DOC_STATUS[] values = BOOK_DOC_STATUS.values();
        for (BOOK_DOC_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        BOOK_DOC_STATUS[] values = BOOK_DOC_STATUS.values();
        for (BOOK_DOC_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return BOOK_DOC_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
