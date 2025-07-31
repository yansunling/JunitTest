package com.str.data;


import com.dy.components.annotations.CJ_column;
import com.word.dataSource.data.TMSP_claims_oa_bearData;
import lombok.Data;

import java.util.List;

/**
 *  理赔通过运单ID查询运单信息
 */
@Data
public class TMSP_claims_oa_record_applyVO {

    @CJ_column(name = "异常信息")
    private List<TMSP_claims_oa_record_apply_exceptData> except_infos;
    @CJ_column(name = "运单信息")
    private TMSP_claims_order_InfoData claims_order;
    @CJ_column(name = "客户签收单")
    private List<TMSP_claims_oa_record_FileData> cust_sign_note;
    @CJ_column(name = "客户回单")
    private List<TMSP_claims_oa_record_FileData> cust_back_note;
    @CJ_column(name = "客户销货清单")
    private List<TMSP_claims_oa_record_FileData> cust_sale_note;
    @CJ_column(name = "中转托运部运单")
    private List<TMSP_claims_oa_record_FileData> turn_comp_note;
    @CJ_column(name = "其他")
    private List<TMSP_claims_oa_record_FileData> other_note;
    @CJ_column(name = "理赔款承担部门")
    private List<TMSP_claims_oa_bearData> bear_infos;
    @CJ_column(name = "异常附件图片")
    private List<TMSP_claims_oa_record_FileData> except_files;
    @CJ_column(name = "整改报告")
    private List<TMSP_claims_oa_record_FileData> rectify_note;
    @CJ_column(name = "坏账申请")
    private String bank_order;

    @CJ_column(name = "微信支付付款凭证")
    private List<TMSP_claims_oa_record_FileData> wx_pay_note;

    @CJ_column(name = "客户的对账单")
    private List<TMSP_claims_oa_record_FileData> cust_balance_note;

    @CJ_column(name = "理赔报销其他")
    private List<TMSP_claims_oa_record_FileData> expense_other_note;

    @CJ_column(name = "付款报销信息")
    private List<TMSP_claims_oa_expense_paymentPO> expense_payment;

    @CJ_column(name = "理赔冲应收明细")
    private List<TMSP_claims_oa_expense_shouldPO> expense_should;






}
