package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "配载状态")
public enum LOADING_STATUS implements Cacheable<LOADING_STATUS > {
    DEFAULT("",""),
    LOADING_STATUS_TOLOAD(LOADING_STATUS.C_LOADING_STATUS_TOLOAD,"待配载"),
    LOADING_STATUS_LOADED(LOADING_STATUS.C_LOADING_STATUS_LOADED,"已配载"),
    LOADING_STATUS_INIT(LOADING_STATUS.C_LOADING_STATUS_INIT,"待预约"),
    LOADING_STATUS_NOTNEED(LOADING_STATUS.C_LOADING_STATUS_NOTNEED,"暂不配载"),
    LOADING_STATUS_TOSUBMIT(LOADING_STATUS.C_LOADING_STATUS_TOSUBMIT,"待提交配载");

    public final static String C_LOADING_STATUS_TOLOAD = "toload";
    public final static String C_LOADING_STATUS_LOADED = "loaded";
    public final static String C_LOADING_STATUS_INIT = "init";
    public final static String C_LOADING_STATUS_NOTNEED = "notneed";
    public final static String C_LOADING_STATUS_TOSUBMIT = "tosubmit";


    @EnumValue
    private String code_type;
    private String code_name;
    LOADING_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,LOADING_STATUS> toMap(){
        Map<String, LOADING_STATUS> toReturn = new HashMap<>();
        LOADING_STATUS[] values = LOADING_STATUS.values();
        for(LOADING_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static LOADING_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        LOADING_STATUS[] values = LOADING_STATUS.values();
        for (LOADING_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        LOADING_STATUS[] values = LOADING_STATUS.values();
        for (LOADING_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return LOADING_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
