package com.word.asset.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dy.components.annotations.CJ_column;
import com.dy.components.annotations.CJ_table;
import com.dy.components.annotations.enums.MysqlColumnTypeEnum;
import com.yd.common.exttype.Money;
import lombok.Data;

import java.sql.Timestamp;


@Data
@CJ_table(name = "理赔交接定责明细")
@TableName(value = "tmsp_claims_hand_doc_detail", autoResultMap = true)
public class TMSP_claims_hand_doc_detailPO extends Model<TMSP_claims_hand_doc_detailPO> {



    @CJ_column(name = "运单号")
    private String order_id;


    @CJ_column(name = "责任人工号")
    private String resp_user_id;


    @CJ_column(name = "责任部门")
    private String resp_org_id;


    @CJ_column(name = "罚款金额")
    private Money fine_amount;









    public Object[] toKeys() {
        return new Object[]{
                order_id
        };
    }

}

