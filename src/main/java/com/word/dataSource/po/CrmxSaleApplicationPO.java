package com.word.dataSource.po;

import com.other.annotation.MyNotEmpty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.word.constansts.CRMX_AUDIT_STATUS;
import com.yd.common.data.CIPBasePO;
import lombok.Data;

import java.sql.Timestamp;

@Data
@CJ_table(name = "跨大区申请表")
@TableName(value = "crm_sale_application", autoResultMap = true)
public class CrmxSaleApplicationPO extends CIPBasePO {

    @TableId
    @CJ_column(name = "申请流水号")
    private String serial_no;

    @MyNotEmpty(message = "客户编码为空")
    @CJ_column(name = "客户编码")
    private String customer_id;


    @MyNotEmpty(message = "所跨大区为空")
    @CJ_column(name = "所跨大区")
    private String region;


    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "申请人")
    private String creator;


    @TableField(fill = FieldFill.INSERT)
    @CJ_column(name = "申请时间")
    private Timestamp create_time;


    @MyNotEmpty(message = "申请原因为空")
    @CJ_column(name = "申请原因")
    private String application_reason;


    @CJ_column(name = "审批状态")
    private CRMX_AUDIT_STATUS approval_status= CRMX_AUDIT_STATUS.INIT;


    @CJ_column(name = "审批人")
    private String approver;


    @CJ_column(name = "审批时间")
    private Timestamp approval_time;


    @CJ_column(name = "审批说明")
    private String approval_comment;







    @Override
    public Object[] toKeys() {
        return new Object[]{serial_no};
    }
}
