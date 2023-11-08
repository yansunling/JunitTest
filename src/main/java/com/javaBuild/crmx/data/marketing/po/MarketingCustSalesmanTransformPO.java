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
@CJ_table(name = "客户分配记录")
@TableName(value = "marketing_cust_salesman_transform", autoResultMap = true)
public class MarketingCustSalesmanTransformPO {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;




    @CJ_column(name = "客户id")
    private String customer_id;




    @CJ_column(name = "分配前销售")
    private String before_cust_salesman_id;




    @CJ_column(name = "分配后销售")
    private String after_cust_salesman_id;




    @CJ_column(name = "分配说明")
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
