package com.javaBuild.enumData;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "审批状态")
public enum CRMX_AUDIT_STATUS implements Cacheable {

    INIT("init","待审批"),
    PASS("pass","审批通过"),
    REJECT("reject","审批退回"),

    ;


    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_AUDIT_STATUS(String code_type, String code_name){
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
