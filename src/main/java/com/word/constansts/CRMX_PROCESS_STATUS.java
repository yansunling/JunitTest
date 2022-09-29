package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "处理状态")
public enum CRMX_PROCESS_STATUS {

    DEFAULT("",""),
    INIT("init","未处理"),
    COMPLETE("complete","已处理"),
    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_PROCESS_STATUS(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_PROCESS_STATUS toEnum(String value){
        CRMX_PROCESS_STATUS[] values = CRMX_PROCESS_STATUS.values();
        for(CRMX_PROCESS_STATUS temp:values){
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
