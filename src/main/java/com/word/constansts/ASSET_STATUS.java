package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;


@CJ_domain(name = "资产状态")
public enum ASSET_STATUS {

    USED("used","使用中"),
    UNUSED("unused","闲置中"),
    DISPOSAL("disposal","已处置"),
    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_STATUS(String code_type, String code_name){
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
