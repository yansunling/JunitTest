package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "资产审批状态")
public enum ASSET_APPLY_STATUS implements Cacheable {

    INIT("init","提交"),
    PASS("pass","已通过"),
    REJECT("reject","已驳回"),

    ;


    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_APPLY_STATUS(String code_type, String code_name){
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
