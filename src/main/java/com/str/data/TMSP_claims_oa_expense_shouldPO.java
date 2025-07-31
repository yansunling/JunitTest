package com.str.data;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.exttype.Money;
import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_table(name = "理赔冲应收单明细")
@TableName(value = "tmsp_claims_oa_expense_should", autoResultMap = true)
public class TMSP_claims_oa_expense_shouldPO extends Model<TMSP_claims_oa_expense_shouldPO> {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;

    @CJ_column(name = "理赔报销流水号")
    private String expense_serial_no;
    @CJ_column(name = "付款报销流水号")
    private String payment_serial_no;


    @CJ_column(name = "应收单号")
    private String inorder_should_id;


    @CJ_column(name = "冲理赔金额")
    private Money clear_amt;


    @CJ_column(name = "创建人")
    private String create_user_id;


    @CJ_column(name = "创建时间")
    private Timestamp create_time;


    @Version
    @CJ_column(name = "版本号")
    private int version;







    public Object[] toKeys() {
        return new Object[]{
                serial_no
        };
    }

}

