package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;


@CJ_domain(name = "")
public enum CRMX_DENSITY_TYPE {

    DENSITY_TYPE_0("0","0-77","13以上"),
    DENSITY_TYPE_1("1","77-111","9-13"),
    DENSITY_TYPE_2("2","111-143","7-9"),
    DENSITY_TYPE_3("3","143-200","5-7"),
    DENSITY_TYPE_4("4","200-280","3.57-5"),
    DENSITY_TYPE_5("5","280-400","2.5-3.57"),
    DENSITY_TYPE_6("6","400-800","1.25-2.5"),
    DENSITY_TYPE_7("7","800-1500","0.67-1.25"),
    DENSITY_TYPE_8("8","1500-99999","0-0.67"),


    ;

    @EnumValue
    private String code_type;
    private String density_name;
    private String bubble_name;


    CRMX_DENSITY_TYPE(String code_type, String density_name, String bubble_name){
        this.code_type = code_type;
        this.density_name = density_name;
        this.bubble_name = bubble_name;

    }
    public String codeType() {
        return this.code_type;
    }

    public String densityName() {
        return density_name;
    }

    public String bubbleName() {
        return bubble_name;
    }
}
