package com.javaBase.sql;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class SqlTest {
    public static void main(String[] args) throws Exception{
        StringBuffer querySql = new StringBuffer();
        querySql.append("select \n" +
                "main.create_user_id AS create_user_id ,  -- 申请人 \n" +
                "main.claims_id AS claims_id ,  -- 理赔编号 \n" +
                "main.claims_info AS claims_info ,  -- 理赔类型 \n" +
                "main.claims_status AS claims_status ,  -- 理赔单状态 \n" +
                "main.order_id AS order_id ,  -- 运单号 \n" +
                "main.delivery_no AS delivery_no ,  -- 次运号 \n" +
                "main.goods_count AS goods_count ,  -- 件数 \n" +
                "main.claims_count AS claims_count ,  -- 理赔件数 \n" +
                "main.ticket_org_id AS ticket_org_id ,  -- 开单部门 \n" +
                "main.ticket_time AS ticket_time ,  -- 开单日期 \n" +
                "hand.send_org_id AS send_org_id ,  -- 装车部门 \n" +
                "date_format(hand.load_date, '%Y-%m-%d %H:%i:%s') AS load_date ,  -- 装车日期 \n" +
                "hand.arr_org_id AS arr_org_id ,  -- 到达部门 \n" +
                "date_format(hand.arrive_time, '%Y-%m-%d %H:%i:%s') AS arrive_time ,  -- 到达日期 \n" +
                "main.transfer_city AS transfer_city ,  -- 二次中转城市 \n" +
                "trans.trans_doc_type AS trans_doc_type ,  -- 运输方式 \n" +
                "main.claims_customer_type AS claims_customer_type ,  -- 索赔客户类型 \n" +
                "main.customer_name AS customer_name ,  -- 索赔客户名称 \n" +
                "main.customer_id AS customer_id ,  -- 索赔客户编码 \n" +
                "main.send_customer_id AS send_customer_id ,  -- 发件客户id \n" +
                "main.customer_contact_way AS customer_contact_way ,  -- 索赔客户联系方式 \n" +
                "main.income_name AS income_name ,  -- 收款人 \n" +
                "main.income_contact_way AS income_contact_way ,  -- 收款人联系方式 \n" +
                "main.certificate_type AS certificate_type ,  -- 证件类型 \n" +
                "main.certificate_no AS certificate_no ,  -- 证件号 \n" +
                "main.bank_name AS bank_name ,  -- 收款人开户行 \n" +
                "main.card_id AS card_id ,  -- 收款人银行账号 \n" +
                "main.order_pay_type AS order_pay_type ,  -- 付款方式 \n" +
                "main.order_close_type AS order_close_type ,  -- 签收类型 \n" +
                "main.insure_value AS insure_value ,  -- 保价声明价值 \n" +
                "main.damage_amt AS damage_amt ,  -- 货损金额 \n" +
                "main.apply_amt AS apply_amt ,  -- 申请理赔金额 \n" +
                "main.judge_amt AS judge_amt ,  -- 保价理赔金额 \n" +
                "main.extra_amt AS extra_amt ,  -- 多赔金额 \n" +
                "main.apply_time AS apply_time ,  -- 申请日期 \n" +
                "main.apply_org_id AS apply_org_id ,  -- 申请部门 \n" +
                "main.apply_user_id AS apply_user_id ,  -- 申请人工号 \n" +
                "main.apply_big_area AS apply_big_area ,  -- 申请大区 \n" +
                "main.claims_reason AS claims_reason ,  -- 理赔类型描述 \n" +
                "main.claims_bg_type AS claims_bg_type ,  -- 理赔大类 \n" +
                "main.claims_sm_type AS claims_sm_type ,  -- 理赔小类 \n" +
                "main.rejecte_remark AS rejecte_remark -- 退回原因 \n" +
                "from (\n" +
                "select \n" +
                "main.create_user_id AS create_user_id ,  \n" +
                "main.claims_id AS claims_id ,  \n" +
                "main.claims_info AS claims_info ,  \n" +
                "main.claims_status AS claims_status ,  \n" +
                "main.order_id AS order_id ,  \n" +
                "profile.delivery_no AS delivery_no ,  \n" +
                "profile.goods_count AS goods_count ,  \n" +
                " sum(detail.claims_count) AS claims_count ,  \n" +
                "profile.ticket_org_id AS ticket_org_id ,  \n" +
                "date_format(profile.ticket_time, '%Y-%m-%d %H:%i:%s') AS ticket_time ,  \n" +
                "profile.transfer_city AS transfer_city ,    \n" +
                "main.claims_customer_type AS claims_customer_type ,  \n" +
                "main.customer_name AS customer_name ,  \n" +
                "main.customer_id AS customer_id ,  \n" +
                "profile.send_customer_id AS send_customer_id ,  \n" +
                "main.customer_contact_way AS customer_contact_way ,  \n" +
                "main.income_name AS income_name ,  \n" +
                "main.income_contact_way AS income_contact_way ,  \n" +
                "main.certificate_type AS certificate_type ,  \n" +
                "main.certificate_no AS certificate_no ,  \n" +
                "main.bank_name AS bank_name ,  \n" +
                "main.card_id AS card_id ,  \n" +
                "profile.order_pay_type AS order_pay_type ,  \n" +
                "profile.order_close_type AS order_close_type ,  \n" +
                "profile.insure_value AS insure_value ,  \n" +
                "round(main.damage_amt / 100, 2) AS damage_amt ,  \n" +
                "round(main.apply_amt / 100, 2) AS apply_amt ,  \n" +
                "round(main.judge_amt / 100, 2) AS judge_amt ,  \n" +
                "round(main.extra_amt / 100, 2) AS extra_amt ,  \n" +
                "date_format(main.apply_time, '%Y-%m-%d %H:%i:%s') AS apply_time ,  \n" +
                "main.apply_org_id AS apply_org_id ,  \n" +
                "main.apply_user_id AS apply_user_id ,  \n" +
                "main.apply_big_area AS apply_big_area ,  \n" +
                "main.claims_reason AS claims_reason ,  \n" +
                "main.claims_bg_type AS claims_bg_type ,  \n" +
                "main.claims_sm_type AS claims_sm_type ,  \n" +
                "main.rejecte_remark AS rejecte_remark ,\n" +
                "main.create_time as create_time\n" +
                "from tmsp.tmsp_claims_profile  main  \n" +
                "left join tmsp.tmsp_claims_detail detail on detail.claims_id=main.claims_id \n" +
                "left join tmsp.tmsp_order_profile profile on profile.order_id = main.order_id \n" +
                "group by main.claims_id \n" +
                ")  main  \n" +
                "left join tmsp.tmsp_stock_io_items stock on stock.order_id = main.order_id \n" +
                "left join tmsp.tmsp_hand_doc hand on hand.doc_id = stock.doc_id \n" +
                "left join tmsp.tmsp_hand_trans_doc trans on trans.trans_doc_id = stock.trans_doc_id \n" +
                " where main.order_id = ? \n" +
                " and main.customer_name = ? \n" +
                " and main.create_time >= ? \n" +
                " and main.create_time <= ? \n" +
                " and main.claims_id = ? \n" +
                " and main.claims_status = ? \n" +
                " and hand.send_org_id = ? \n" +
                " and hand.arr_org_id = ? \n" +
                " and main.apply_user_id = ? \n" +
                " group by main.claims_id \n" +
                "order by main.create_time desc \n");

        Statement statement = CCJSqlParserUtil.parse(querySql.toString());
        Select selectStatement = (Select) statement;


        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
//        System.out.println(tableList.get(0));


        String value="\"test\"";
        value = value.replaceAll("\"", "");
        System.out.println(value);

    }
}
