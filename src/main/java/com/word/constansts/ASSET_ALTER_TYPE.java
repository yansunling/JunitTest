package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;


@CJ_domain(name = "变更类型")
public enum ASSET_ALTER_TYPE {

    UPDATE("update","修改"),
    CHANGE("change","异动"),
    DISPOSAL("disposal","异动"),
    REPAIR("repair","报修"),

    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_ALTER_TYPE(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }


}
