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
@CJ_table(name = "客户拜访信息")
@TableName(value = "marketing_base_cust_visit", autoResultMap = true)
public class MarketingBaseCustVisitPO {

    @TableId
    @CJ_column(name = "拜访流水号")
    private String serial_no;




    @CJ_column(name = "客户id")
    private String customer_id;




    @CJ_column(name = "拜访方式")
    private String visit_way;




    @CJ_column(name = "拜访执行人工号")
    private String visit_joiner_id;




    @CJ_column(name = "拜访日期")
    private Timestamp visit_date;




    @CJ_column(name = "拜访时间")
    private String visit_time;




    @CJ_column(name = "拜访说明")
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
