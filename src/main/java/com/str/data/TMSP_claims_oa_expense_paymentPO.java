package com.str.data;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.yd.common.exttype.Money;
import com.yd.tmsp.constants.CLAIMS_PAYMENT_TYPE;
import com.yd.tmsp.constants.CLAIMS_PAYMENT_WAY;
import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_table(name = "理赔付款报销信息")
@TableName(value = "tmsp_claims_oa_expense_payment", autoResultMap = true)
public class TMSP_claims_oa_expense_paymentPO extends Model<TMSP_claims_oa_expense_paymentPO> {

    @TableId
    @CJ_column(name = "流水号")
    private String serial_no;


    @CJ_column(name = "理赔报销oa流水号")
    private String expense_serial_no;


    @CJ_column(name = "费用明细")
    private String detail_name;


    @CJ_column(name = "冲账方式")
    private CLAIMS_PAYMENT_WAY claims_payment_way;


    @CJ_column(name = "支付类型")
    private CLAIMS_PAYMENT_TYPE claims_payment_type;


    @CJ_column(name = "金额")
    private Money claims_payment_amt;


    @CJ_column(name = "运单数量")
    private int order_num;


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

