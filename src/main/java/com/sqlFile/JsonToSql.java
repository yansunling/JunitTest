package com.sqlFile;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yd.utils.common.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.bouncycastle.util.Times;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToSql {
    public static void main(String[] args) {
        String json="{\"add_account_period\":30,\"add_amount\":150000.0,\"address\":\"方丈港村六里桥兜28号\",\"apply_arrears_amount\":220000.0,\"apply_reason\":\"\",\"area_code\":330503,\"belong_company\":\"浙江驮龙东晋弘物流有限公司\",\"city_code\":330500,\"contactor\":\"罗淑芳\",\"contract_name\":\"\",\"contract_no\":\"YWHT202107090003\",\"contract_status\":\"valid\",\"create_time\":1625791250000,\"creator\":\"T0089\",\"creator_name\":\"李佳\",\"cust_grade\":\"1\",\"cust_mobile\":\"\",\"cust_type\":\"3\",\"customer_all_name\":\"湖州祥龙老六供应链管理有限公司\",\"customer_id\":\"1391521\",\"customer_idcard\":\"\",\"customer_name\":\"祥龙物流\",\"effect_date\":\"2022-06-01\",\"end_date\":\"2023-06-01\",\"industry_sup_type\":\"A10\",\"industry_type\":\"A10001\",\"init_account_period\":60,\"init_arrears_amount\":70000.0,\"input_source\":\"send\",\"invoice_date\":\"10\",\"invoice_flag\":\"0\",\"longest_account_period\":60,\"month_invoicing_days\":\"0\",\"op_user_id\":\"T0012\",\"op_user_name\":\"李佳\",\"other_contact\":\"0572-3021900\",\"payment_date\":\"15\",\"payment_way\":\"0\",\"print_flag\":\"\",\"prov_code\":330000,\"reconci_date\":\"5\",\"remark\":\"\",\"salesman_id\":\"T0187\",\"serial_no\":\"210709084050545248\",\"sign_date\":\"2022-06-01\",\"sign_main\":\"1\",\"tax_egistration_no\":\"91330503MA28CGWK4W\",\"three_months_invoice_amount\":204257.0,\"update_time\":1655705900000}";

        Map<String,Object> map = JSON.parseObject(json, Map.class);
        List<String> columns=new ArrayList<>();
        List<String> values=new ArrayList<>();

        map.forEach((column,value)->{
            columns.add(column);
            String temp=value+"";
            if(StringUtils.equalsIgnoreCase("null",temp)||StringUtils.isBlank(temp)){
                values.add("null");
            }else{
                if(value instanceof Long){
                    if(Long.parseLong(temp)>1425791250000L){
                        Timestamp timestamp = new Timestamp(Long.parseLong(temp));
                        temp = DateUtils.format(timestamp,"yyyy-MM-dd HH:mm:ss");
                    }
                }
                temp=temp.replace("'","''");
                values.add("'"+temp+"'");
            }
        });
        String tableName="crm.crm_contract_info";
        String sql="insert into "+tableName+"("+StringUtil.join(columns.toArray(),",")+")values("+ StringUtil.join(values.toArray(),",")+");";
        System.out.println(sql);

        System.out.println(new Timestamp(1655705900000L));

    }
}
