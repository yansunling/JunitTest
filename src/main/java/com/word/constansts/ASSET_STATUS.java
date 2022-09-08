package com.word.constansts;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yd.common.cache.Cacheable;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "资产状态")
public enum ASSET_STATUS implements Cacheable {

    USED("used","使用中"),
    UNUSED("unused","闲置中"),
    DISPOSAL("disposal","处置中"),
    CHANGE("change","异动中"),
    DISPOSED("disposed","已处置"),
    REPAIR("repair","报修中"),
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

    public static Map<String, ASSET_STATUS> toMap(){
        Map<String, ASSET_STATUS> toReturn = new HashMap<>();
        ASSET_STATUS[] values = ASSET_STATUS.values();
        for(ASSET_STATUS val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static ASSET_STATUS ofValue(String value){
        return toMap().get(value);
    }


}
