package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yd.common.cache.Cacheable;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "路由模式")
public enum ASSET_TASK_MODE implements Cacheable {
    DEFAULT("",""),
    TASK_PROCESS_1("1","归属部门"),
    TASK_PROCESS_2("2","使用部门"),
    TASK_PROCESS_3("3","负责人"),
    TASK_PROCESS_4("4","使用人"),
    TASK_PROCESS_5("5","资产"),

    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_TASK_MODE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static ASSET_TASK_MODE toEnum(String value){
        ASSET_TASK_MODE[] values = ASSET_TASK_MODE.values();
        for(ASSET_TASK_MODE temp:values){
            if(StringUtils.equals(temp.code_type,value)){
                return temp;
            }
        }
        return DEFAULT;
    }

    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }


}
