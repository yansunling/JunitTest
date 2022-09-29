package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "推送状态")
public enum CRMX_PUSH_STATUS {

    DEFAULT("",""),
    INIT("int","未推送"),
    SUCCESS("success","推送成功"),
    FAIL("fail","推送失败"),
    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_PUSH_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_PUSH_STATUS toEnum(String value){
        CRMX_PUSH_STATUS[] values = CRMX_PUSH_STATUS.values();
        for(CRMX_PUSH_STATUS temp:values){
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
