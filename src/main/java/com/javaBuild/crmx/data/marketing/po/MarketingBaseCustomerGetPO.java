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
@CJ_table(name = "客户信息")
@TableName(value = "marketing_base_customer", autoResultMap = true)
public class MarketingBaseCustomerGetPO {

    @TableId
    @CJ_column(name = "客户id")
    private String customer_id;











}
