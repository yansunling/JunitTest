package com.str.json;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class StringToJSon {
    public static void main(String[] args) {
        String str="(serial_no=269d8356e97648999111eb251beae0b7, send_order_cust_id=269d8356e97648999111eb251beae0b7, stock_io_id=67125f3be4b08b359dbe9e4e, send_trans_cust_id=2410220040, send_id=, load_date=2024-10-19, end_arrival_date=2099-12-31, order_id=601709479, end_site_id=350101060301, end_site_name=改貌分拨场, stock_pos_id=改貌库, send_date=2024-10-22, send_time_period=DEFAULT, vehicle_type_id=, vehicle_type_name=, goods_count=15, goods_weight=1435.0, goods_volume=17.647, vehicle_length=0.0, vehicle_weigtht=0.0, vehicle_volume=0.000, driver_id=, driver_name=, vehicle_driver_fixed=IS_NOT_0, cust_send_remark=, actual_addr_id=241010214629462963, actual_addr_detail=观山湖区观山湖区翁井村2组，贵阳九州翔丰环保科技有限公司, send_region_id=D012770, new_send_region_id=D012770, send_region_name=观山湖南派区, new_send_region_name=观山湖南派区, actual_customer_id=1472885, actual_customer_name=李诗娟, actual_receiver_name=李诗娟, actual_receiver_mobile=15186973552, actual_receiver_tel=, expect_book_date=2024-10-22, book_time=2024-10-22 09:21:03.0, pulling_back=IS_NOT_0, no_recieve_reason=, load_return=IS_NOT_0, return_reason=, receive_status=RECEIVE_STATUS_INIT, cust_loading_status=LOADING_STATUS_LOADED, auto_loading_status=AUTO_LOADING_STATUS_NOTNEED, load_batch=IS_NOT_0, send_batch=IS_NOT_0, actual_lon=106.565833, actual_lat=26.557987, last_sign_time=2024-10-13 14:04:18.0, out_status=OUT_STATUS_INIT, stock_reason=DEFAULT, order_remark=, addsend_fee=0, predict_trans_amt=0, estimate_floor=0, actual_delivery=0, actual_floor=0, send_distance_type=DEFAULT, send_distance=0, parking=0, road_bridge=0, safe_check=0, other_fee=0, trans_amt=0, carprice_remark=, src_send_order_cust=SRC_SEND_ORDER_CUST_HAND, box_asset_num=, version=0, remark=+完善地址信息:D012770+, update_user_id=T0140, update_time=2024-10-22 14:22:59.0, create_user_id=T2903, create_time=2024-10-22 09:19:51.0, rel_send_trans_cust_id=), TMSP_send_order_custPO(serial_no=a91a7e0b4a5244ba8247841ba13f5837, send_order_cust_id=a91a7e0b4a5244ba8247841ba13f5837, stock_io_id=67125e9ae4b0fa3653afe0a0, send_trans_cust_id=2410220040, send_id=, load_date=2024-10-19, end_arrival_date=2099-12-31, order_id=601709479, end_site_id=350101060301, end_site_name=改貌分拨场, stock_pos_id=改貌库, send_date=2024-10-22, send_time_period=DEFAULT, vehicle_type_id=, vehicle_type_name=, goods_count=110, goods_weight=10526.0, goods_volume=43.000, vehicle_length=0.0, vehicle_weigtht=0.0, vehicle_volume=0.000, driver_id=, driver_name=, vehicle_driver_fixed=IS_NOT_0, cust_send_remark=, actual_addr_id=241010214629462963, actual_addr_detail=观山湖区观山湖区翁井村2组，贵阳九州翔丰环保科技有限公司, send_region_id=D012770, new_send_region_id=D012770, send_region_name=观山湖南派区, new_send_region_name=观山湖南派区, actual_customer_id=1472885, actual_customer_name=李诗娟, actual_receiver_name=李诗娟, actual_receiver_mobile=15186973552, actual_receiver_tel=, expect_book_date=2024-10-22, book_time=2024-10-22 09:21:03.0, pulling_back=IS_NOT_0, no_recieve_reason=, load_return=IS_NOT_0, return_reason=, receive_status=RECEIVE_STATUS_INIT, cust_loading_status=LOADING_STATUS_LOADED, auto_loading_status=AUTO_LOADING_STATUS_NOTNEED, load_batch=IS_NOT_0, send_batch=IS_NOT_0, actual_lon=0.0, actual_lat=0.0, last_sign_time=2024-10-13 14:04:18.0, out_status=OUT_STATUS_INIT, stock_reason=DEFAULT, order_remark=, addsend_fee=0, predict_trans_amt=0, estimate_floor=0, actual_delivery=0, actual_floor=0, send_distance_type=DEFAULT, send_distance=0, parking=0, road_bridge=0, safe_check=0, other_fee=0, trans_amt=0, carprice_remark=, src_send_order_cust=SRC_SEND_ORDER_CUST_HAND, box_asset_num=, version=0, remark=+同步所有地址核实:D012770+, update_user_id=T0140, update_time=2024-10-22 14:22:59.0, create_user_id=T2903, create_time=2024-10-22 09:19:51.0, rel_send_trans_cust_id=P2410220065)";
        str = str.replace("{", "").replace("}", "").replace("(", "").replace(")", "");
        String[] split = str.split(",");
        Map<String,String> map=new HashMap<>();
        for(String value:split){
            System.out.println(value);
            String[] newStrs = value.trim().split("=");
            if(newStrs.length==2){
                map.put(newStrs[0],newStrs[1]);
            }


        }
        System.out.println(JSON.toJSONString(map));


    }
}
