insert INTO tmsp.tmsp_net_updown select uuid_short(), '<新机构ID>', next_org_id, hand_type, product_type, last_city, transfer_city, trans_way, schedule, order_cashing_time, last_cashing_arrive_time, last_send_time, transit_hour, arrive_time, route_nature, validity_start_time, validity_end_time, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_updown  where org_id in('<老机构ID>');


INSERT INTO tmsp.tmsp_base_operator_emp(serial_no, operator_id, operator_name, emp_id, emp_joinage, emp_birthdata, emp_age, emp_joindata, operator_type, operator_remark, org_id, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time)
select uuid_short(), uuid_short(), operator_name, emp_id, emp_joinage, emp_birthdata, emp_age, emp_joindata, operator_type, operator_remark, '<新机构ID>', activate_status, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_base_operator_emp where  org_id in('<老机构ID>');



INSERT INTO tmsp.tmsp_tcp_vehicle(serial_no, vehicle_id, vehicle_attr, vehicle_law_id, vehicle_type, vehicle_type_id, vendor_id, org_id, belong_org_id, gps_no, vehicle_brand, purchase_date, purchase_value, traffic_insurance_no, traffic_insurance_date, vehicle_frame_no, engine_no, operation_no, engine_power, tail_plate_is, vehicle_trailer_id, net_weight, side_door, temp_flag, belong_depart, belong_region, tcp_busi_type, is_driver, oil_card, etc_card, driver_id, activate_status, is_complete, version, remark, update_user_id, update_time, create_user_id, create_time, financial_depreciation_amount, fix_cost, kilo_oil_cost)
select uuid_short(), uuid_short(), vehicle_attr, vehicle_law_id, vehicle_type, vehicle_type_id, vendor_id, '<新机构ID>', belong_org_id, gps_no, vehicle_brand, purchase_date, purchase_value, traffic_insurance_no, traffic_insurance_date, vehicle_frame_no, engine_no, operation_no, engine_power, tail_plate_is, vehicle_trailer_id, net_weight, side_door, temp_flag, belong_depart, belong_region, tcp_busi_type, is_driver, oil_card, etc_card, driver_id, activate_status, is_complete, version, remark, update_user_id, update_time, create_user_id, create_time, financial_depreciation_amount, fix_cost, kilo_oil_cost from tmsp.tmsp_tcp_vehicle where org_id in('<老机构ID>');


INSERT INTO tmsp.tmsp_net_org_product_line(serial_no, depart_org_id, product_type, last_city, is_area_net, income_org_id, effective_date, expiry_date, activate_status, delivery_gis_flag, share_ratio, version, remark, update_user_id, update_time, create_user_id, create_time)
select UUID_SHORT(), '<新机构ID>', product_type, last_city, is_area_net, income_org_id, effective_date, expiry_date, activate_status, delivery_gis_flag, share_ratio, version, remark, update_user_id, update_time, create_user_id, now()
from tmsp.tmsp_net_org_product_line where depart_org_id='<老机构ID>';



INSERT INTO tmsp.tmsp_net_org_remote_stock(serial_no, ticket_org_id, inventory_org_id, activate_status, effect_time, disabled_time, version, remark, update_user_id, update_time, create_user_id, create_time)
select uuid_short(), '<新机构ID>', inventory_org_id, activate_status, effect_time, disabled_time, version, remark, update_user_id, update_time, create_user_id, now() from tmsp.tmsp_net_org_remote_stock where ticket_org_id='<老机构ID>';


INSERT INTO tmsp.tmsp_net_site_depart(serial_no, site_org_id, depart_org_id, version, remark, update_user_id, update_time, create_user_id, create_time)
select uuid_short(), site_org_id, '<新机构ID>', version, remark, update_user_id, update_time, create_user_id, now() from  tmsp.tmsp_net_site_depart where depart_org_id='<老机构ID>';


INSERT INTO tmsp.tmsp_net_receive_range(serial_no, book_org_id, last_org_id, customer_id, customer_name, trans_user_id, version, remark, update_user_id, update_time, create_user_id, create_time)
select uuid_short(), '<新机构ID>', last_org_id, customer_id, customer_name, trans_user_id, version, remark, update_user_id, update_time, create_user_id, now() from tmsp.tmsp_net_receive_range where book_org_id='<老机构ID>';


INSERT INTO tmsp.tmsp_net_org_debt_limit(serial_no, depart_org_id, max_limit, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time)
select uuid_short(),'<新机构ID>', max_limit, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org_debt_limit where depart_org_id='<老机构ID>';