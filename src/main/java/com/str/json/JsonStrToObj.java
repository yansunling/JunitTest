package com.str.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.str.data.TMSP_claims_oa_record_applyVO;
import com.yd.common.cache.Cacheable;
import com.yd.common.exttype.Money;
import com.yd.common.exttype.Volume;
import com.yd.common.exttype.Weight;
import com.yd.common.jackson.*;
import lombok.SneakyThrows;

public class JsonStrToObj {
    public static void main(String[] args) throws Exception{
        String json="{\"bear_infos\":[{\"bear_rate\":100,\"business_region_id\":\"35010103\",\"resp_bear_amount\":{\"mode\":\"F\",\"val\":0},\"resp_org_id\":\"35010103120502\",\"resp_user_id\":\"T1740\",\"resp_user_name\":\"邹小波\"}],\"claims_order\":{\"account_type\":\"ACCOUNT_TYPE_03\",\"actual_fee_amount\":{\"mode\":\"Y\",\"val\":0},\"apply_amt\":{\"mode\":\"Y\",\"val\":160.00},\"arr_org_id\":\"350101040301\",\"arrive_time\":1751751377000,\"back_flag\":\"IS_NOT_0\",\"bank_name\":\"中国农业银行新疆分行营业部河南路支行\",\"card_id\":\"6228480898802690179\",\"claims_bg_type\":\"OA_CLAIMS_BG_TYPE_DAMAGE\",\"claims_cust_grade_name\":\"核心客户\",\"claims_customer_id\":\"2221949\",\"claims_customer_name\":\"刘开华\",\"claims_customer_type\":\"CLAIMS_CUSTOMER_TYPE_2\",\"claims_data_source\":\"OA_CLAIMS_DATA_SOURCE_SEND\",\"claims_phone_number\":\"13882639458\",\"claims_reason\":\"中转客户刘开华2025-07-02日由义乌西转运场发的凉席外破内损5条产生赔款160元，赔款金领域中转部已垫付，赔款打给程孝龙   中国农业银行新疆分行营业部河南路支行\\t6228480898802690179\",\"claims_sm_type\":\"OA_CLAIMS_SM_TYPE_11\",\"claims_type\":\"CLAIMS_TYPE_NORMAL\",\"delivery_no\":\"1Y11W1148-30K\",\"except_count\":1,\"expense_class\":\"营销成本\",\"expense_detail_type\":\"营销成本(日常)\",\"expense_type\":\"费用报销\",\"goods_count\":30,\"goods_names\":\"竹凉席\",\"goods_type_names\":\"普货\",\"host\":\"https://kp.tuolong56.com\",\"insure_value\":{\"mode\":\"Y\",\"val\":0},\"load_date\":1751385600000,\"loss_amt\":{\"mode\":\"Y\",\"val\":0},\"order_close_type\":\"SIGNIN_STATUS_1\",\"order_debt_amount\":{\"mode\":\"Y\",\"val\":0},\"order_id\":\"602175430\",\"order_pay_type\":\"ORDER_PAY_TYPE_RECV\",\"premium\":{\"mode\":\"Y\",\"val\":0},\"reach_fee_amount\":{\"mode\":\"Y\",\"val\":769},\"recv_cust_grade_name\":\"核心客户\",\"recv_cust_name\":\"刘开华\",\"recv_customer_id\":\"2221949\",\"recv_phone_number\":\"13882639458\",\"send_cust_grade_name\":\"核心客户\",\"send_cust_name\":\"孙明货运出租\",\"send_customer_id\":\"1401890\",\"send_org_id\":\"35010103120502\",\"send_phone_number\":\"18957907957\",\"signin_time\":1751807472000,\"ticket_org_id\":\"3501010312050101\",\"ticket_time\":1751416149000,\"trans_doc_type\":\"TRANS_WAY_XINGBAO\",\"transfer_city\":\"阿克苏市\",\"turn_comp_name\":\"金领域物流\",\"user_name\":\"程孝龙\"},\"cust_back_note\":[{\"file_id\":\"qmsv_6cc0a566-9b04-485f-800c-ba199eef1fd0_1\",\"file_name\":\"收条.png\"},{\"file_id\":\"qmsv_4e9428a4-32df-4725-96b3-40e51c51a8b2_1\",\"file_name\":\"证明.jpg\"},{\"file_id\":\"qmsv_fad238fb-96b8-42a2-90c0-55fe45ef4ff2_1\",\"file_name\":\"付款.jpg\"},{\"file_id\":\"qmsv_e6dd2027-7b84-484a-9732-7dbc4fb2bc70_1\",\"file_name\":\"托运单.png\"},{\"file_id\":\"qmsv_0577168b-acd9-4bd3-a232-af101e05a85b_1\",\"file_name\":\"da353cebf5968a01e93875c06335ce1.jpg\"}],\"cust_balance_note\":[{\"file_id\":\"qmsv_35ee55dd-84c3-42fa-95d9-512820fd010a_1\",\"file_name\":\"收条.png\"}],\"cust_sale_note\":[],\"cust_sign_note\":[],\"except_files\":[{\"file_id\":\"portal_tms_46ee8ce9-707c-481c-a9ed-595b5ec34df4_1\",\"file_name\":\"异常附件1\"},{\"file_id\":\"portal_tms_46ee8ce9-707c-481c-a9ed-595b5ec34df4_3\",\"file_name\":\"异常附件2\"},{\"file_id\":\"portal_tms_46ee8ce9-707c-481c-a9ed-595b5ec34df4_2\",\"file_name\":\"异常附件3\"}],\"except_infos\":[{\"data_source\":\"DATA_SOURCE_APP_REPORT\",\"except_clear_status\":\"EXCEPT_CLEAR_STATUS_INIT\",\"except_clear_type\":\"DEFAULT\",\"except_count\":4,\"except_goods_price\":{\"mode\":\"Y\",\"val\":160},\"except_goods_value\":{\"mode\":\"Y\",\"val\":160},\"except_resp_org_id\":\"350101040301\",\"except_time\":1751810127000,\"except_type_id\":\"EXCEPT_TYPE_ID_B01\"}],\"expense_other_note\":[],\"expense_payment\":[{\"claims_payment_amt\":{\"mode\":\"Y\",\"val\":160},\"claims_payment_type\":\"CLAIMS_PAYMENT_TYPE_TRANSFER\",\"claims_payment_way\":\"CLAIMS_PAYMENT_WAY_EXPENSE\",\"detail_name\":\"赔款理赔\",\"order_num\":0,\"version\":0}],\"expense_should\":[],\"other_note\":[],\"rectify_note\":[],\"turn_comp_note\":[{\"file_id\":\"qmsv_f47b7788-1b05-4c5c-aed8-49e19037f392_1\",\"file_name\":\"托运单.png\"}],\"wx_pay_note\":[{\"file_id\":\"qmsv_a59e3cc1-c8dd-44da-b6ff-da6f28138627_1\",\"file_name\":\"付款.jpg\"}]} ";
        TMSP_claims_oa_record_applyVO applyVO = JSON.parseObject(json, TMSP_claims_oa_record_applyVO.class);

        System.out.println(toJSon(applyVO));
    }
    @SneakyThrows
    private static String toJSon(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Money.class, MoneySerializer.INSTANCE);
        simpleModule.addKeySerializer(Money.class, MoneySerializer.INSTANCE);
        simpleModule.addDeserializer(Money.class, MoneyDeserializer.INSTANCE);
        simpleModule.addSerializer(Weight.class, WeightSerializer.INSTANCE);
        simpleModule.addKeySerializer(Weight.class, WeightSerializer.INSTANCE);
        simpleModule.addDeserializer(Weight.class, WeightDeserializer.INSTANCE);
        simpleModule.addSerializer(Volume.class, VolumeSerializer.INSTANCE);
        simpleModule.addKeySerializer(Volume.class, VolumeSerializer.INSTANCE);
        simpleModule.addDeserializer(Volume.class, VolumeDeserializer.INSTANCE);
        simpleModule.addSerializer(Cacheable.class, EnumSerializer.INSTANCE);
        objectMapper.registerModule(simpleModule);
        String json = objectMapper.writeValueAsString(obj);
        return json;
    }
}
