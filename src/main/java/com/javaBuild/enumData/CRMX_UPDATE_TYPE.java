package com.javaBuild.enumData;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "更改类型")
public enum CRMX_UPDATE_TYPE implements Cacheable {

    UPDATE_TYPE_1("1","修改基本信息"),
    UPDATE_TYPE_2("2","新增地址"),
    UPDATE_TYPE_3("3","修改地址"),
    UPDATE_TYPE_4("4","删除地址"),
    UPDATE_TYPE_5("5","新增联系人"),
    UPDATE_TYPE_6("6","修改联系人"),
    UPDATE_TYPE_7("7","删除联系人"),
    UPDATE_TYPE_8("8","销售APP修改基本信息"),



    ;


    @EnumValue
    private String code_type;
    private String code_name;
    CRMX_UPDATE_TYPE(String code_type, String code_name){
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
