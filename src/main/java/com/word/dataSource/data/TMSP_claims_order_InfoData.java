package com.word.dataSource.data;

import com.dy.components.annotations.CJ_column;
import com.yd.common.exttype.Money;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TMSP_claims_order_InfoData {
    @CJ_column(name = "运单号")
    private String order_id;
    @CJ_column(name = "次运号")
    private String delivery_no;
    @CJ_column(name = "异常件数")
    private Integer except_count;
    @CJ_column(name = "货物品名")
    private String goods_names;

    @CJ_column(name = "货物品类")
    private String goods_type_names;

    @CJ_column(name = "开单部门")

    private String ticket_org_id;
    @CJ_column(name = "开单时间")
    private Timestamp ticket_time;
    @CJ_column(name = "中转城市")
    private String transfer_city;
    @CJ_column(name = "装车部门")

    private String send_org_id;
    @CJ_column(name = "装车时间")
    private Timestamp load_date;
    @CJ_column(name = "到达部门")
    private String arr_org_id;
    @CJ_column(name = "到达时间")
    private Timestamp arrive_time;
    @CJ_column(name = "干线运输方式")
    private String trans_doc_type;
    @CJ_column(name = "保价声明价值")
    private Money insure_value;
    @CJ_column(name = "发货客户编码")
    private String send_customer_id;
    @CJ_column(name = "发货客户名称")
    private String send_cust_name;
    @CJ_column(name = "发货人手机号")
    private String send_phone_number;
    @CJ_column(name = "收货客户编码")
    private String recv_customer_id;
    @CJ_column(name = "收货客户名称")
    private String recv_cust_name;
    @CJ_column(name = "收货客户手机号")
    private String recv_phone_number;
    @CJ_column(name = "签收类型")
    private String order_close_type;
    @CJ_column(name = "中转承运商")
    private String turn_comp_name;
    @CJ_column(name = "保价费")
    private Money premium;
    @CJ_column(name = "索赔客户类别")
    private String claims_customer_type;
    @CJ_column(name = "开单付款方式")
    private String order_pay_type;
    @CJ_column(name = "现付金额")
    private Money actual_fee_amount;
    @CJ_column(name = "预付临欠")
    private Money order_debt_amount;
    @CJ_column(name = "到付金额")
    private Money reach_fee_amount;
    @CJ_column(name = "发货客户等级")
    private String send_cust_grade_name;
    @CJ_column(name = "收货客户等级")
    private String recv_cust_grade_name;
    @CJ_column(name = "回单标记")
    private String back_flag;
    @CJ_column(name = "理赔类别")
    private String claims_type;

    @CJ_column(name = "理赔大类")
    private String claims_bg_type;

    @CJ_column(name = "理赔小类")
    private String claims_sm_type;

    @CJ_column(name = "申请理赔金额")
    private Money apply_amt;
    @CJ_column(name = "定损金额")
    private Money loss_amt;

    @CJ_column(name = "理赔事由描述")
    private String claims_reason;

    @CJ_column(name = "签收时间")
    private Timestamp signin_time;

    @CJ_column(name = "客户名称编码")
    private String claims_customer_id;
    @CJ_column(name = "理赔客户名称")
    private String claims_customer_name;
    @CJ_column(name = "理赔客户联系方式")
    private String claims_phone_number;
    @CJ_column(name = "理赔客户星级")
    private String claims_cust_grade_name;

    @CJ_column(name = "理赔数据来源")
    private String claims_data_source;


    @CJ_column(name = "关联理赔审批流程")
    private String claims_process_number;

    @CJ_column(name = "事故理赔流水号")
    private String oa_claims_serial_no;

    @CJ_column(name = "收款人名称")
    private String user_name;


    @CJ_column(name = "开户行名称")
    private String bank_name;


    @CJ_column(name = "银行卡号")
    private String card_id;


    @CJ_column(name = "账户性质")
    private String account_type;
    @CJ_column(name = "域名")
    private String host;

    @CJ_column(name = "理赔事故请求id")
    private String claims_request_id;

    @CJ_column(name = "报销类型")
    private String 	expense_type="费用报销";

    @CJ_column(name = "报销种类")
    private String 	expense_class="营销成本";
    @CJ_column(name = "具体类型")
    private String 	expense_detail_type="营销成本(日常)";



}
