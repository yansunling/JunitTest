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
public class MarketingBaseCustomerPO {

    @TableId
    @CJ_column(name = "客户id")
    private String customer_id;




    @CJ_column(name = "客户名称")
    private String customer_name;




    @CJ_column(name = "企业全称")
    private String comp_name;




    @CJ_column(name = "企业座机")
    private String other_contact;




    @CJ_column(name = "客户所属部门")
    private String org_id;




    @CJ_column(name = "分配销售员")
    private String cust_salesman_id;




    @CJ_column(name = "发货线路")
    private String main_lines_name;




    @CJ_column(name = "客户分类")
    private String customer_type;




    @CJ_column(name = "收发货类型")
    private String send_recv_flag;




    @CJ_column(name = "发货决定权")
    private String delivery_decision;




    @CJ_column(name = "客户来源")
    private String cust_source;




    @CJ_column(name = "货物品类")
    private String good_type_name;




    @CJ_column(name = "货物品类")
    private String peer_name;




    @CJ_column(name = "新客户")
    private String new_flag;




    @CJ_column(name = "是否需作废")
    private String need_cancel_flag;




    @CJ_column(name = "需作废原因")
    private String need_cancel_reason;




    @CJ_column(name = "是否已作废")
    private String cancel_flag;




    @CJ_column(name = "作废原因")
    private String cancel_reason;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作人")
    private String op_user_id;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    @CJ_column(name = "操作时间")
    private Timestamp update_time;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建人")
    private String creator;




    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "创建时间")
    private Timestamp create_time;










}
