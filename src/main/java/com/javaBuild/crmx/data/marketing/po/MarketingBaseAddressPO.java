package com.javaBuild.crmx.data.marketing.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "客户地址信息")
@TableName(value = "marketing_base_address", autoResultMap = true)
public class MarketingBaseAddressPO {

    @TableId
    @CJ_column(name = "地址流水")
    private String serial_no;




    @CJ_column(name = "客户id")
    private String customer_id;




    @CJ_column(name = "地址类型")
    private String address_type;




    @CJ_column(name = "省编码")
    private String prov_code;




    @CJ_column(name = "市编码")
    private String city_code;




    @CJ_column(name = "区编码")
    private String area_code;




    @CJ_column(name = "详细地址")
    private String address;




    @CJ_column(name = "备注")
    private String remark;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String creator;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作人")
    private String op_user_id;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作时间")
    private Timestamp update_time;










}
