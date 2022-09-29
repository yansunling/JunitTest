package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "反馈来源")
public enum CRMX_FEEDBACK_SOURCE {

    DEFAULT("",""),
    FEEDBACK_SOURCE_TL("tl","驮龙客户小程序"),
    FEEDBACK_SOURCE_ZX("zx","中骁客户小程序"),
    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_FEEDBACK_SOURCE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_FEEDBACK_SOURCE toEnum(String value){
        CRMX_FEEDBACK_SOURCE[] values = CRMX_FEEDBACK_SOURCE.values();
        for(CRMX_FEEDBACK_SOURCE temp:values){
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
