package com.sqlFile.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "自动配载状态")
public enum AUTO_LOADING_STATUS implements Cacheable<AUTO_LOADING_STATUS > {
    DEFAULT("",""),
    AUTO_LOADING_STATUS_INIT(AUTO_LOADING_STATUS.C_AUTO_LOADING_STATUS_INIT,"待自动配载"),
    AUTO_LOADING_STATUS_FAIL(AUTO_LOADING_STATUS.C_AUTO_LOADING_STATUS_FAIL,"自动配载失败"),
    AUTO_LOADING_STATUS_AUTOED(AUTO_LOADING_STATUS.C_AUTO_LOADING_STATUS_AUTOED,"已自动配载"),
    AUTO_LOADING_STATUS_NOTNEED(AUTO_LOADING_STATUS.C_AUTO_LOADING_STATUS_NOTNEED,"不需自动配载");

    public final static String C_AUTO_LOADING_STATUS_INIT = "init";
    public final static String C_AUTO_LOADING_STATUS_FAIL = "fail";
    public final static String C_AUTO_LOADING_STATUS_AUTOED = "autoed";
    public final static String C_AUTO_LOADING_STATUS_NOTNEED = "notneed";


    @EnumValue
    private String code_type;
    private String code_name;
    AUTO_LOADING_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String,AUTO_LOADING_STATUS> toMap(){
        Map<String, AUTO_LOADING_STATUS> toReturn = new HashMap<>();
        AUTO_LOADING_STATUS[] values = AUTO_LOADING_STATUS.values();
        for(AUTO_LOADING_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static AUTO_LOADING_STATUS ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        AUTO_LOADING_STATUS[] values = AUTO_LOADING_STATUS.values();
        for (AUTO_LOADING_STATUS val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        AUTO_LOADING_STATUS[] values = AUTO_LOADING_STATUS.values();
        for (AUTO_LOADING_STATUS val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return AUTO_LOADING_STATUS.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
