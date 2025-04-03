package com.sqlFile;

import cn.hutool.core.lang.ClassScanner;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.factory.CIPHandler;
import com.yd.common.cache.Cacheable;
import com.yd.common.exttype.Money;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class JsonToSqlEnum {
    public static void main(String[] args) throws Exception{
        String json="{\"actual_addr_detail\":\"呈贡区联运大道骏宝物流\",\"actual_addr_id\":\"2315c82195df43c1b4f70c0d490f18a5\",\"actual_customer_id\":\"1469604\",\"actual_customer_name\":\"王一鸣\",\"actual_delivery\":{\"mode\":\"F\",\"val\":0},\"actual_floor\":{\"mode\":\"F\",\"val\":0},\"actual_lat\":0.0,\"actual_lon\":0.0,\"actual_receiver_mobile\":\"13330437462\",\"actual_receiver_name\":\"王一鸣\",\"actual_receiver_tel\":\"\",\"addsend_fee\":{\"mode\":\"F\",\"val\":0},\"auto_loading_status\":\"AUTO_LOADING_STATUS_INIT\",\"book_time\":1743387410000,\"box_asset_num\":\"\",\"carprice_remark\":\"\",\"create_time\":1743303376000,\"create_user_id\":\"T3130\",\"cust_loading_status\":\"LOADING_STATUS_TOLOAD\",\"cust_send_remark\":\"\",\"driver_id\":\"\",\"driver_name\":\"\",\"end_arrival_date\":\"2099-12-31\",\"end_site_id\":\"350101050301\",\"end_site_name\":\"金马村分拨场\",\"estimate_floor\":{\"mode\":\"F\",\"val\":0},\"expect_book_date\":\"2025-03-30\",\"goods_count\":8,\"goods_volume\":{\"val\":0.900},\"goods_weight\":{\"val\":95.0},\"last_sign_time\":1742893813000,\"load_batch\":\"IS_NOT_0\",\"load_date\":\"2025-03-29\",\"load_return\":\"IS_NOT_0\",\"new_send_region_id\":\"D011643\",\"new_send_region_name\":\"王家营片区\",\"no_recieve_reason\":\"\",\"order_id\":\"602026546\",\"order_remark\":\"\",\"other_fee\":{\"mode\":\"F\",\"val\":0},\"out_status\":\"OUT_STATUS_INIT\",\"parking\":{\"mode\":\"F\",\"val\":0},\"predict_trans_amt\":{\"mode\":\"F\",\"val\":0},\"pulling_back\":\"IS_NOT_0\",\"receive_status\":\"RECEIVE_STATUS_INIT\",\"rel_send_trans_cust_id\":\"\",\"remark\":\"+核实地址生成:D011643++同步所有地址核实:D011643+\",\"return_reason\":\"\",\"road_bridge\":{\"mode\":\"F\",\"val\":0},\"safe_check\":{\"mode\":\"F\",\"val\":0},\"send_batch\":\"IS_NOT_0\",\"send_date\":\"2025-04-01\",\"send_distance\":0,\"send_distance_type\":\"DEFAULT\",\"send_id\":\"\",\"send_order_cust_id\":\"6562dfed7d4a4a5f9456468907518454\",\"send_region_id\":\"D011643\",\"send_region_name\":\"王家营片区\",\"send_time_period\":\"SEND_TIME_PERIOD_NIGHT\",\"send_trans_cust_id\":\"\",\"serial_no\":\"6562dfed7d4a4a5f9456468907518454\",\"src_send_order_cust\":\"SRC_SEND_ORDER_CUST_HAND\",\"stock_io_id\":\"67e89699e4b0fa3649b9ce5c\",\"stock_pos_id\":\"金马村\",\"stock_reason\":\"DEFAULT\",\"trans_amt\":{\"mode\":\"F\",\"val\":0},\"update_time\":1743387410000,\"update_user_id\":\"T0547\",\"vehicle_driver_fixed\":\"IS_NOT_0\",\"vehicle_length\":0.0,\"vehicle_type_id\":\"\",\"vehicle_type_name\":\"\",\"vehicle_volume\":{\"val\":0.000},\"vehicle_weigtht\":{\"val\":0.0},\"version\":0}";

        JSONObject jsonObject = JSON.parseObject(json);

        Set<String> keySet = jsonObject.keySet();
        List<String> columns=new ArrayList<>();
        List<Object> values=new ArrayList<>();

        Set<Class<?>> classSet = ClassScanner.scanPackageBySuper("com.sqlFile.enums",Cacheable.class);
        Map<String,String> enumsMap=new HashMap<>();
        classSet.forEach(clazz->{
            if(clazz.isEnum()){
                try {
                    Method method = clazz.getMethod("values");
                    Cacheable[] enums = (Cacheable[]) method.invoke(null);
                    for(Cacheable object:enums){
                        enumsMap.put(object.toString(),object.codeType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        keySet.forEach(key->{
            columns.add(key);
            Object object = jsonObject.get(key);
            if(object instanceof JSONObject){
                try {
                    Money money = JSONObject.parseObject(JSON.toJSONString(object), Money.class);
                    values.add(money.yuan2Fen());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }



            String value = enumsMap.get(object);
            if(value!=null){
                values.add(value);
                return;
            }
            if(key.indexOf("_time")>0||key.indexOf("_date")>0){
                try {
                    Timestamp timestamp = jsonObject.getTimestamp(key);
                    values.add(DateUtils.format(timestamp,"yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    System.out.println(key);
                    values.add(jsonObject.get(key));
                }
            }else{
                values.add(jsonObject.get(key));
            }
        });
        String table="tmsp.tmsp_send_order_cust";

        String sql="insert into "+table+"("+ StringUtils.join(",",columns.toArray()) +")value"
                +"('"+StringUtils.join("','",values.toArray())+"');";
        System.out.println(sql);


    }
}
