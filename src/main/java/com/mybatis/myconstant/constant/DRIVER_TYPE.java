package com.mybatis.myconstant.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "司机类型")
public enum DRIVER_TYPE  {
    DEFAULT("",""),
    DRIVER_TYPE_SELF(DRIVER_TYPE.C_DRIVER_TYPE_SELF,"自有"),
    DRIVER_TYPE_RENT(DRIVER_TYPE.C_DRIVER_TYPE_RENT,"外请");

    public final static String C_DRIVER_TYPE_SELF = "self";
    public final static String C_DRIVER_TYPE_RENT = "rent";


    @EnumValue
    private String code_type;
    private String code_name;
    DRIVER_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
