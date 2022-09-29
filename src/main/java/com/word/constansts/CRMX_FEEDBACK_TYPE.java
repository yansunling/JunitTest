package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "反馈类型")
public enum CRMX_FEEDBACK_TYPE {

    DEFAULT("",""),
    FEEDBACK_TYPE_ORDER("order","运单反馈"),
    FEEDBACK_TYPE_CUSTOMER("customer","客户反馈"),
    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_FEEDBACK_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_FEEDBACK_TYPE toEnum(String value){
        CRMX_FEEDBACK_TYPE[] values = CRMX_FEEDBACK_TYPE.values();
        for(CRMX_FEEDBACK_TYPE temp:values){
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
