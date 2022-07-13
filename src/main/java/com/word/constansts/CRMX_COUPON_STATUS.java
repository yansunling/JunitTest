package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;



@CJ_domain(name = "优惠券状态")
public enum CRMX_COUPON_STATUS implements Cacheable {

    USABLE("usable","可使用"),
    USED("used","已使用"),
    FROZEN("frozen","已冻结"),
    EXPIRED("expired","已到期");

    @EnumValue
    private String code_type;
    private String code_name;


    CRMX_COUPON_STATUS(String code_type, String code_name){
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
