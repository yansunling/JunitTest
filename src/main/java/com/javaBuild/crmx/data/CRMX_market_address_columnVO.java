package com.javaBuild.crmx.data;

import com.dy.components.annotations.CJ_column;
import lombok.Data;

import java.io.Serializable;


@Data
public class CRMX_market_address_columnVO implements Serializable {

    @CJ_column(name = "专业市场模板id")
    private String template_id;

    @CJ_column(name = "专业市场名称")
    private String market_name;

    @CJ_column(name = "字段id")
    private String column_id;

    @CJ_column(name = "字段名")
    private String address_column;

    @CJ_column(name = "字段校验规则（无文字要求:a,用汉字:b,用阿拉伯数字:c,用大写英文字母:d,阿拉伯数字or英文字母:e,阿拉伯数字+英文字母:f,汉字或英文字母:g,阿拉伯数字OR汉字:h）")
    private String address_column_rule;

    @CJ_column(name = "字段是否必填（必填：1，非必填：0: '(非必填)，选填一个：2）")
    private int required_flag;

    @CJ_column(name = "字段顺序")
    private int column_order;

    @CJ_column(name = "字段值")
    private String value;

    @CJ_column(name = "地址类型")
    private String address_type;


    public CRMX_market_address_columnVO() {
    }

    public CRMX_market_address_columnVO(String column_id, String value) {
        this.column_id = column_id;
        this.value = value;
    }
}
