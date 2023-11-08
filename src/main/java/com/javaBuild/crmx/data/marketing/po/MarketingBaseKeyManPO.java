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
@CJ_table(name = "客户联系人")
@TableName(value = "marketing_base_key_man", autoResultMap = true)
public class MarketingBaseKeyManPO {

    @TableId
    @CJ_column(name = "联系人id")
    private String serial_no;




    @CJ_column(name = "所属客户id")
    private String customer_id;




    @CJ_column(name = "联系人名称")
    private String contact_name;




    @CJ_column(name = "联系人手机")
    private String contact_mobile;




    @CJ_column(name = "联系人座机")
    private String contact_landline;




    @CJ_column(name = "联系人职务")
    private String contact_post;




    @CJ_column(name = "备注")
    private String remark;




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
