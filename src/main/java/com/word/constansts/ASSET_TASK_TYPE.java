package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.yd.common.cache.Cacheable;


@CJ_domain(name = "盘点任务类型")
public enum ASSET_TASK_TYPE implements Cacheable {

    DEFAULT("",""),
    ASSET_TASK_APP("app","手机盘点"),
    ASSET_TASK_EXCEL("excel","表格盘点"),



    ;




    @EnumValue
    private String code_type;
    private String code_name;
    ASSET_TASK_TYPE(String code_type, String code_name){
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
