package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "客户评价")
public enum CRMX_FEEDBACK_EVALUATE  {

    DEFAULT("",""),
    EVALUATE_1("1","非常满意"),
    EVALUATE_2("2","满意"),
    EVALUATE_3("3","一般"),
    EVALUATE_4("4","差"),
    EVALUATE_5("5","非常差"),

    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_FEEDBACK_EVALUATE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_FEEDBACK_EVALUATE toEnum(String value){
        CRMX_FEEDBACK_EVALUATE[] values = CRMX_FEEDBACK_EVALUATE.values();
        for(CRMX_FEEDBACK_EVALUATE temp:values){
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
