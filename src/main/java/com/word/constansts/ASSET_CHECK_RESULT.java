package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "资产核查结果")
public enum ASSET_CHECK_RESULT implements Cacheable {

    NO_REPORT("no_report","未上报"),
    NORMAL("normal","正常"),
    EXCEPT("except","异常"),
    LOSS("loss","盘亏"),
    SURPLUS("surplus","盘盈"),
    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_CHECK_RESULT(String code_type, String code_name){
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
