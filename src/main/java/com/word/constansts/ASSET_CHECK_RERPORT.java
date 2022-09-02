package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "资产盘点结果")
public enum ASSET_CHECK_RERPORT implements Cacheable {

    NO_REPORT("no_report","未上报"),
    NORMAL("normal","正常无误"),
    EXCEPT("except","异常差错"),

    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_CHECK_RERPORT(String code_type, String code_name){
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
