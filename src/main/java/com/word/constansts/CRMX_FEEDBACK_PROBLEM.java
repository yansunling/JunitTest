package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang.StringUtils;


@CJ_domain(name = "反馈问题")
public enum CRMX_FEEDBACK_PROBLEM {

    DEFAULT("",""),
    PROBLEM_1("1","联系方式"),
    PROBLEM_2("2","地址"),
    PROBLEM_3("3","时效"),
    PROBLEM_4("4","价格"),
    PROBLEM_5("5","计量"),
    PROBLEM_6("6","品质"),
    PROBLEM_7("7","服务"),
    PROBLEM_8("8","其他"),

    ;

    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_FEEDBACK_PROBLEM(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }

    @JsonCreator
    public static CRMX_FEEDBACK_PROBLEM toEnum(String value){
        CRMX_FEEDBACK_PROBLEM[] values = CRMX_FEEDBACK_PROBLEM.values();
        for(CRMX_FEEDBACK_PROBLEM temp:values){
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
