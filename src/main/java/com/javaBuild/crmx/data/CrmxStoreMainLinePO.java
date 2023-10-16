package com.javaBuild.crmx.data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "储备客户线路")
@TableName(value = "crm_store_main_line", autoResultMap = true)
public class CrmxStoreMainLinePO {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;




    @CJ_column(name = "客户编码")
    private String customer_id;




    @CJ_column(name = "线路")
    private String line_name;




    @CJ_column(name = "价格")
    private String line_price;




    @CJ_column(name = "发货渠道")
    private String delivery_channel;




    @CJ_column(name = "时效")
    private String delivery_time;




    @CJ_column(name = "交货方式")
    private String delivery_method;




    @CJ_column(name = "其他")
    private String delivery_remark;




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
