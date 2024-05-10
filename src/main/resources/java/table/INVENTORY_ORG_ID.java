package com.yd.tmsp.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dy.components.annotations.CJ_domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yd.common.cache.Cacheable;
import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;


@CJ_domain(name = "开单库存部门")
public enum INVENTORY_ORG_ID implements Cacheable<INVENTORY_ORG_ID> {
    DEFAULT("",""),
    INVENTORY_ORG_ID_250108010301(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108010301,"义乌西转运中心"),
    INVENTORY_ORG_ID_250108020401(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108020401,"通泰转运场"),
    INVENTORY_ORG_ID_250108020402(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108020402,"永源转运场"),
    INVENTORY_ORG_ID_250108020601(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108020601,"温州西转运场"),
    INVENTORY_ORG_ID_250108030301(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108030301,"织里转运场"),
    INVENTORY_ORG_ID_250109010401(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109010401,"北郊转运中心"),
    INVENTORY_ORG_ID_250109010403(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109010403,"闵行转运场"),
    INVENTORY_ORG_ID_250109020301(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109020301,"杭州北转运中心"),
    INVENTORY_ORG_ID_250109020302(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109020302,"湖州西转运场"),
    INVENTORY_ORG_ID_250109030403(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109030403,"无锡转运场"),
    INVENTORY_ORG_ID_250109030404(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109030404,"常州转运场"),
    INVENTORY_ORG_ID_250109040401(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109040401,"钱清转运场"),
    INVENTORY_ORG_ID_250109040402(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109040402,"柯东汽运转运场"),
    INVENTORY_ORG_ID_250109040403(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109040403,"柯西汽运转运场"),
    INVENTORY_ORG_ID_250109040601(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109040601,"宁波北转运场"),
    INVENTORY_ORG_ID_250109040602(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109040602,"余姚西转运场"),
    INVENTORY_ORG_ID_250109020303(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250109020303,"嘉兴东转运场"),
    INVENTORY_ORG_ID_250108020303(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250108020303,"台州康洋营业部"),
    INVENTORY_ORG_ID_25011007020103(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_25011007020103,"西南商贸城分拨场"),
    INVENTORY_ORG_ID_250110030301(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250110030301,"改貌分拨场"),
    INVENTORY_ORG_ID_250110010301(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250110010301,"金马村分拨场"),
    INVENTORY_ORG_ID_250110020601(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250110020601,"跑马山分拨场"),




    //INVENTORY_ORG_ID_25011101010201(INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_25011101010201,"乌东分拨场"),

    INVENTORY_ORG_ID_250115(com.yd.tmsp.constants.INVENTORY_ORG_ID.C_INVENTORY_ORG_ID_250115,"义乌汽运枢纽"),;
    public final static String C_INVENTORY_ORG_ID_250109020303="250109020303";
    public final static String C_INVENTORY_ORG_ID_250108010301="250108010301";
    public final static String C_INVENTORY_ORG_ID_250108020401="250108020401";
    public final static String C_INVENTORY_ORG_ID_250108020402="250108020402";
    public final static String C_INVENTORY_ORG_ID_250108020601="250108020601";
    public final static String C_INVENTORY_ORG_ID_250108030301="250108030301";
    public final static String C_INVENTORY_ORG_ID_250109010401="250109010401";
    public final static String C_INVENTORY_ORG_ID_250109010403="250109010403";
    public final static String C_INVENTORY_ORG_ID_250109020301="250109020301";
    public final static String C_INVENTORY_ORG_ID_250109020302="250108030304";
    public final static String C_INVENTORY_ORG_ID_250109030403="250109030403";
    public final static String C_INVENTORY_ORG_ID_250109030404="250109030404";
    public final static String C_INVENTORY_ORG_ID_250109040401="250109040401";
    public final static String C_INVENTORY_ORG_ID_250109040402="250109040402";
    public final static String C_INVENTORY_ORG_ID_250109040403="250109040403";
    public final static String C_INVENTORY_ORG_ID_250109040601="250109040601";

    public final static String C_INVENTORY_ORG_ID_25011007020103="25011007020103";
    public final static String C_INVENTORY_ORG_ID_250110030301="250110030301";
    public final static String C_INVENTORY_ORG_ID_250110010301="250110010301";
    public final static String C_INVENTORY_ORG_ID_250110020601="250110020601";






    public final static String  C_INVENTORY_ORG_ID_250108020303 = "250108020303";
    //public final static String C_INVENTORY_ORG_ID_25011101010201="25011101010201";


    public final static String C_INVENTORY_ORG_ID_250109040602="250109040602";
    public final static String C_INVENTORY_ORG_ID_250115="250115";;


    @EnumValue
    private String code_type;
    private String code_name;
    INVENTORY_ORG_ID(String code_type, String code_name){
        this.code_type = code_type;
        this.code_name = code_name;
    }
    public static Map<String, INVENTORY_ORG_ID> toMap(){
        Map<String, INVENTORY_ORG_ID> toReturn = new HashMap<>();
        com.yd.tmsp.constants.INVENTORY_ORG_ID[] values = com.yd.tmsp.constants.INVENTORY_ORG_ID.values();
        for(com.yd.tmsp.constants.INVENTORY_ORG_ID val:values){
            toReturn.put(val.code_type, val);
        }
        return toReturn;

    }
    @JsonCreator
    public static com.yd.tmsp.constants.INVENTORY_ORG_ID ofValue(String value){
        return toMap().get(value);
    }
    public static String code2name(String code_type) {
        com.yd.tmsp.constants.INVENTORY_ORG_ID[] values = com.yd.tmsp.constants.INVENTORY_ORG_ID.values();
        for (com.yd.tmsp.constants.INVENTORY_ORG_ID val : values) {
            if(StringUtils.equals(val.codeType(),code_type)){
                return val.codeName();
            }
        }
        return null;
    }

    public static String name2code(String code_name) {
        com.yd.tmsp.constants.INVENTORY_ORG_ID[] values = com.yd.tmsp.constants.INVENTORY_ORG_ID.values();
        for (com.yd.tmsp.constants.INVENTORY_ORG_ID val : values) {
            if(StringUtils.equals(val.codeName(),code_name)){
                return val.codeType();
            }
        }
        return null;
    }

    public String getDomainId() {
        return com.yd.tmsp.constants.INVENTORY_ORG_ID.class.getName();
    }
    
    @JsonValue
    public String codeType() {
        return this.code_type;
    }

    public String codeName() {
        return this.code_name;
    }
}
