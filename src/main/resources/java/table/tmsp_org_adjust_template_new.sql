-- 《'<老机构名称>'('<老机构ID>')》  调整为  《'<新机构名称>'('<新机构ID>')》
-- 老机构ID:'<老机构ID>', 新机构ID:'<新机构ID>', 新机构上级ID:'<新机构上级ID>', 新机构小区ID:'<新机构小区ID>', 新机构大区ID:'<新机构大区ID>'


-- HCM
update hcm.hcm_user_info set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_emp_ent set org_power = '<新机构ID>' where org_power in('<老机构ID>');
update hcm.hcm_emp_ent set dept = '<新机构ID>' where dept in('<老机构ID>');
replace INTO hcm.hcm_org_position_rel_rule(serial_no, position_id, position_name, org_id, org_name, job_category, rank, nature, staffing_num, entry_num, remark, is_delete, version, update_user_id, update_time, create_user_id, create_time) select serial_no, position_id, position_name, '<新机构ID>', '<新机构名称>', job_category, rank, nature, staffing_num, entry_num, remark, is_delete, version, update_user_id, update_time, create_user_id, create_time from hcm.hcm_org_position_rel_rule where org_id in('<老机构ID>');
update hcm.hcm_checkin_logs set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_checkin_report set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_checkin_report_month set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_checkin_user set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_act_record set old_dept = '<新机构ID>' where old_dept in('<老机构ID>');
update hcm.hcm_act_record set new_dept = '<新机构ID>' where new_dept in('<老机构ID>');
update hcm.hcm_act_tra set old_company = '<新机构ID>' where old_company in('<老机构ID>');
update hcm.hcm_injury_info set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_jx_permission set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_jx_result set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update hcm.hcm_jx_result set org_id4 = '<新机构ID>' where org_id4 in('<老机构ID>');
update hcm.hcm_jx_result set org_id8 = '<新机构ID>' where org_id8 in('<老机构ID>');

update hcm.hcm_org_rec set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update hcm.hcm_org_rec set org_rel_rec = CONCAT(org_rel_rec,',','<新机构ID>') where org_rel_rec like CONCAT('%','<老机构ID单个>','%');
update hcm.hcm_org_rec set sup_org_id = '<新机构ID>' where sup_org_id in('<老机构ID单个>');
update hcm.hcm_org_rec set root_org_id = '<新机构ID>' where root_org_id in('<老机构ID单个>');
update hcm.hcm_org_relation set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update hcm.hcm_org_relation set org_name = '<新机构名称>' where org_name in('<老机构名称>');
update hcm.hcm_org_relation set business_region_id = '<新机构大区ID>' where business_region_id in('<老机构大区ID>');
update hcm.hcm_org_relation set business_district_id = '<新机构小区ID>' where business_district_id in('<老机构小区ID>');
update hcm.hcm_org_relation set committee_id = '<新机构ID>' where committee_id in('<老机构ID单个>');

replace INTO hcm.hcm_org_rel select '<新机构ID>', rel_org_id, start_date, end_date, org_name, org_rel_type, create_date, creater, operator, op_time, send_status, remark, update_user_id, update_time, create_user_id, create_time from hcm.hcm_org_rel where org_id in('<老机构ID>');
replace INTO hcm.hcm_org_rel select org_id, '<新机构ID>', start_date, end_date, org_name, org_rel_type, create_date, creater, operator, op_time, send_status, remark, update_user_id, update_time, create_user_id, create_time   from hcm.hcm_org_rel where rel_org_id in('<老机构ID>');
replace INTO hcm.hcm_org_rel select org_id, rel_org_id, start_date, end_date, '<新机构名称>', org_rel_type, create_date, creater, operator, op_time, send_status, remark, update_user_id, update_time, create_user_id, create_time  from hcm.hcm_org_rel where org_name in('<老机构名称>');

update hcm.hcm_org_relation_all set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update hcm.hcm_org_relation_all set org_name = '<新机构名称>' where org_name in('<老机构名称>');
update hcm.hcm_org_relation_all set business_region_id = '<新机构大区ID>' where business_region_id in('<老机构大区ID>');
update hcm.hcm_org_relation_all set business_district_id = '<新机构小区ID>' where business_district_id in('<老机构小区ID>');
update hcm.hcm_org_relation_all set committee_id = '<新机构ID>' where committee_id in('<老机构ID单个>');
update hcm.hcm_org_info set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update hcm.hcm_org_info set org_name = '<新机构名称>' where org_name in('<老机构名称>');






-- PMP
update pmp.pmp_sendout_cross set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_normal set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_special set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_trip set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_unload set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_long set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_sendout_floor set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update pmp.pmp_base_sendout_area set org_id = '<新机构ID>' where org_id in('<老机构ID>');



-- TMSP
update tmsp.tmsp_tcp_turn_comp set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.foc_plugins_review_rule_user set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_send_trans_turn set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.tmsp_send_trans_turn set end_site_name = '<新机构名称>' where end_site_name in('<老机构名称>');
update tmsp.tmsp_send_trans_cust set end_site_name = '<新机构名称>' where end_site_name in('<老机构名称>');
update tmsp.tmsp_send_trans_cust set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.tmsp_send_order_turn set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.tmsp_send_order_turn set end_site_name = '<新机构名称>' where end_site_name in('<老机构名称>');
update tmsp.tmsp_send_order_cust set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.tmsp_send_order_cust set end_site_name = '<新机构名称>' where end_site_name in ('<老机构名称>');
update tmsp.tmsp_alter_order_flow_record set review_org_id = '<新机构ID>' where review_org_id in('<老机构ID>');
update tmsp.tmsp_alter_order_report set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_back_doc set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_back_doc set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID>');
update tmsp.tmsp_back_handover_doc set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_back_handover_doc set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID>');
update tmsp.tmsp_back_handover_doc set above_org_id = '<新机构ID>' where above_org_id in('<老机构ID>');
update tmsp.tmsp_back_handover_doc set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID>');
update tmsp.tmsp_base_alter_order_audit set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_base_alter_order_audit set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update tmsp.tmsp_base_fin set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_base_fin set arr_org_id = '<新机构ID>' where arr_org_id in('<老机构ID>');
update tmsp.tmsp_base_operator_emp set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_base_send_region set region_org_id = '<新机构ID>' where region_org_id in('<老机构ID>');
update tmsp.tmsp_base_vehicle_type set arrive_org_id = CONCAT(arrive_org_id,',','<新机构ID>') where arrive_org_id regexp '<老机构ID集合>';
update tmsp.tmsp_stock_info_transit set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_info_transit set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_claims_judge_org set judge_org_id = '<新机构ID>' where judge_org_id in('<老机构ID>');
update tmsp.tmsp_claims_judge_org set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_claims_judge_user set judge_org_id = '<新机构ID>' where judge_org_id in('<老机构ID>');
update tmsp.tmsp_claims_profile set apply_org_id = '<新机构ID>' where apply_org_id in('<老机构ID>');
update tmsp.tmsp_claims_profile set judge_org_id = '<新机构ID>' where judge_org_id in('<老机构ID>');
update tmsp.tmsp_claims_profile set rejecte_org_id = '<新机构ID>' where rejecte_org_id in('<老机构ID>');
update tmsp.tmsp_claims_profile set finish_org_id = '<新机构ID>' where finish_org_id in('<老机构ID>');
update tmsp.tmsp_claims_profile set invalid_org_id = '<新机构ID>' where invalid_org_id in('<老机构ID>');
update tmsp.tmsp_close_sign set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_close_sign set sign_org_id = '<新机构ID>' where sign_org_id in('<老机构ID>');
update tmsp.tmsp_close_sign_rate set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_close_transfer_fee set create_org_id = '<新机构ID>' where create_org_id in('<老机构ID>');
update tmsp.tmsp_except_info set except_resp_org_id = '<新机构ID>' where except_resp_org_id in('<老机构ID>');
update tmsp.tmsp_except_info set excep_org_id = '<新机构ID>' where excep_org_id in('<老机构ID>');
update tmsp.tmsp_hand_book_doc set book_org_id = '<新机构ID>' where book_org_id in('<老机构ID>');
update tmsp.tmsp_hand_book_doc set close_org_id = '<新机构ID>' where close_org_id in('<老机构ID>');
update tmsp.tmsp_hand_book_doc set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');
update tmsp.tmsp_hand_book_doc set plan_reach_orgid = '<新机构ID>' where plan_reach_orgid in('<老机构ID>');
update tmsp.tmsp_hand_doc set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_hand_doc set arr_org_id = '<新机构ID>' where arr_org_id in('<老机构ID>');
update tmsp.tmsp_hand_doc set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');
update tmsp.tmsp_hand_doc_alter set create_org_id = '<新机构ID>' where create_org_id in('<老机构ID>');
update tmsp.tmsp_hand_doc_alter_info set arr_org_id_before = '<新机构ID>' where arr_org_id_before in('<老机构ID>');
update tmsp.tmsp_hand_doc_alter_info set arr_org_id_after = '<新机构ID>' where arr_org_id_after in('<老机构ID>');
update tmsp.tmsp_hand_doc_alter_info set create_org_id = '<新机构ID>' where create_org_id in('<老机构ID>');

update tmsp.tmsp_hand_schedule_car set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_car set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_car set route_way_id = REPLACE(route_way_id,'<替换老机构ID集合>','<新机构ID>') where route_way_id regexp '<老机构ID集合>' ;
update tmsp.tmsp_hand_schedule_car set route_way=REPLACE(route_way,'<替换老机构名称集合>','<新机构名称>') where route_way_id regexp '<老机构ID集合>' ;


update tmsp.tmsp_hand_schedule_car_detail set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_carplan set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_externalplan set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_train set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_train set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_schedule_trainplan set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_hand_seal_info set seal_org_id = '<新机构ID>' where seal_org_id in('<老机构ID>');
update tmsp.tmsp_hand_seal_info set unseal_org_id = '<新机构ID>' where unseal_org_id in('<老机构ID>');
update tmsp.tmsp_hand_trans_doc set trans_org_id = '<新机构ID>' where trans_org_id in('<老机构ID>');
update tmsp.tmsp_hand_trans_doc set last_arr_org_id = '<新机构ID>' where last_arr_org_id in('<老机构ID>');
update tmsp.tmsp_hand_trans_doc_fin set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_hand_trans_doc_fin set arr_org_id = '<新机构ID>' where arr_org_id in('<老机构ID>');
update tmsp.tmsp_hand_unload_scan_detail set scan_org_id = '<新机构ID>' where scan_org_id in('<老机构ID>');
update tmsp.tmsp_hand_unload_task set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID>');
update tmsp.tmsp_hand_unload_task set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID>');
update tmsp.tmsp_msg_dyapp_record set busi_org_id = '<新机构ID>' where busi_org_id in('<老机构ID>');
update tmsp.tmsp_net_end_org set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_net_order_import_authority set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_net_org_barcode set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');



update tmsp.tmsp_net_org_switch_log set org_id_before = '<新机构ID>' where org_id_before in('<老机构ID>');
update tmsp.tmsp_net_org_switch_log set org_id_after = '<新机构ID>' where org_id_after in('<老机构ID>');
update tmsp.tmsp_net_org_unload_resp set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_net_org_unload_resp set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_net_org_user set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_net_predict_vehicle_amt set site_org_id = '<新机构ID>' where site_org_id in('<老机构ID>');
update tmsp.tmsp_net_predict_vehicle_amt set site_org_name = '<新机构名称>' where site_org_name in('<老机构名称>');
update tmsp.tmsp_net_receive_range set book_org_id = '<新机构ID>' where book_org_id in('<老机构ID单个>');
update tmsp.tmsp_net_receive_range set last_org_id = '<新机构ID>' where last_org_id in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set stock_org_id = '<新机构ID>' where stock_org_id in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set pass_org_id1 = '<新机构ID>' where pass_org_id1 in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set pass_org_id2 = '<新机构ID>' where pass_org_id2 in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set pass_org_id3 = '<新机构ID>' where pass_org_id3 in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set direct_org_id = '<新机构ID>' where direct_org_id in('<老机构ID单个>');
update tmsp.tmsp_net_route_line set destination_org_id = '<新机构ID>' where destination_org_id in('<老机构ID单个>');
update tmsp.tmsp_net_self_line set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_order_detail set stock_org_id = '<新机构ID>' where stock_org_id in('<老机构ID>');
update tmsp.tmsp_order_detail set stock_org_ids = '<新机构ID>' where stock_org_ids in('<老机构ID>');
update tmsp.tmsp_order_detail set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_order_pre set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_order_pre set last_org_id = '<新机构ID>' where last_org_id in('<老机构ID>');
update tmsp.tmsp_order_pre set recv_fail_org  = '<新机构ID>' where recv_fail_org  in('<老机构ID>');
update tmsp.tmsp_order_pre set recv_succ_org  = '<新机构ID>' where recv_succ_org  in('<老机构ID>');
update tmsp.tmsp_claims_oa_bear set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_claims_oa_expense_record set apply_org_id = '<新机构ID>' where apply_org_id in('<老机构ID>');
update tmsp.tmsp_order_profile set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update tmsp.tmsp_order_profile set sign_org_id = '<新机构ID>' where sign_org_id in('<老机构ID>');
update tmsp.tmsp_order_profile set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update tmsp.tmsp_order_profile set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update tmsp.tmsp_order_profile set income_org_name = '<新机构名称>' where income_org_name in('<老机构名称>');
update tmsp.tmsp_order_track_monitor set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update tmsp.tmsp_order_track_monitor set sign_org_id = '<新机构ID>' where sign_org_id in('<老机构ID>');
update tmsp.tmsp_order_track_monitor set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update tmsp.tmsp_order_track_monitor set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update tmsp.tmsp_order_track_monitor set track_org_id = concat(track_org_id,',','<新机构ID>')  where track_org_id regexp '<老机构ID集合>';
update tmsp.tmsp_pda_device_info set current_org_id = '<新机构ID>' where current_org_id in('<老机构ID>');
update tmsp.tmsp_pda_device_info set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_pda_out set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_pda_pair_out_detail set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_pda_stock_count_task set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_railway_wagon_items set create_org_id = '<新机构ID>' where create_org_id in('<老机构ID>');
update tmsp.tmsp_send_app_doc set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_stock_all set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_all_assign_log set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_cargo set pre_org_id = '<新机构ID>' where pre_org_id in('<老机构ID>');
update tmsp.tmsp_stock_cargo set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_check set check_org_id = '<新机构ID>' where check_org_id in('<老机构ID>');
update tmsp.tmsp_stock_check_task set task_org_id = '<新机构ID>' where task_org_id in('<老机构ID>');
update tmsp.tmsp_stock_check_task_rule set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_stock_info set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_info_transit set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_info_transit set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_stock_io_items set out_org_id = '<新机构ID>' where out_org_id in('<老机构ID>');
update tmsp.tmsp_stock_io_items set in_org_id = '<新机构ID>' where in_org_id in('<老机构ID>');
update tmsp.tmsp_stock_io_log set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_stock_note set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_take_order set ship_org_id = '<新机构ID>' where ship_org_id in('<老机构ID>');
update tmsp.tmsp_take_order set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update tmsp.tmsp_take_order set book_org_id = '<新机构ID>' where book_org_id in('<老机构ID>');
update tmsp.tmsp_take_order_log set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_tcp_driver set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_tcp_vehicle set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_tcp_vehicle set belong_region = '<新机构大区ID>',belong_org_id = '<新机构ID>' where belong_org_id in('<老机构ID>');
update tmsp.tmsp_tcp_vendor set create_org_id = '<新机构ID>' where create_org_id in('<老机构ID>');
update tmsp.tmsp_tcp_vendor set update_org_id = '<新机构ID>' where update_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_apply set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_apply set apply_org_id = '<新机构ID>' where apply_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_import set next_org_id = '<新机构ID>' where next_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_import set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_stock set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_turn_external_stock set apply_resp_org_id = '<新机构ID>' where apply_resp_org_id in('<老机构ID>');
update tmsp.tmsp_turn_order set other_org_id = '<新机构ID>' where other_org_id in('<老机构ID>');
update tmsp.tmsp_turn_order set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update tmsp.tmsp_msg_model set msg_model_org = '<新机构ID>' where msg_model_org in('<老机构ID>');
update tmsp.tmsp_claims_profile set apply_big_area = '<新机构大区ID>' where judge_org_id = '<新机构ID>';

update tmsp.tmsp_base_box set own_org_id = '<新机构ID>' where own_org_id in('<老机构ID>');
update tmsp.tmsp_base_box set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_base_box set latest_org_id = '<新机构ID>' where latest_org_id in('<老机构ID>');
update tmsp.tmsp_send_order_cust_record set end_site_id = '<新机构ID>' where end_site_id in('<老机构ID>');
update tmsp.tmsp_send_order_cust_record set end_site_name = '<新机构名称>' where end_site_name in('<老机构名称>');
update tmsp.tmsp_base_depart_msg set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update tmsp.tmsp_base_turn_transfer_fee set site_org_id = '<新机构ID>' where site_org_id in('<老机构ID>');
update tmsp.tmsp_base_turn_transfer_fee set site_org_name = '<新机构名称>' where site_org_name in('<老机构名称>');
update tmsp.tmsp_claims_hand_doc_detail set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update tmsp.tmsp_net_car_stopload_his set load_org_id = '<新机构ID>' where load_org_id in('<老机构ID>');
update tmsp.tmsp_net_car_stopload_his set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set start_org_id = '<新机构ID>' where start_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set passing_one_org_id = '<新机构ID>' where passing_one_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set passing_two_org_id = '<新机构ID>' where passing_two_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set passing_three_org_id = '<新机构ID>' where passing_three_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set passing_four_org_id = '<新机构ID>' where passing_four_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set end_org_id = '<新机构ID>' where end_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set evaluate_line_end  = '<新机构ID>' where evaluate_line_end  in('<老机构ID>');
update tmsp.tmsp_net_trans_evaluate_schedule set evaluate_line_start  = '<新机构ID>' where evaluate_line_start  in('<老机构ID>');
update tmsp.tmsp_net_trans_time_item_schedule set load_org_id = '<新机构ID>' where load_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_time_item_schedule set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');
update tmsp.tmsp_net_trans_time_schedule set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set send_org_id = '<新机构ID>' where send_org_id in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set arr_org_id = '<新机构ID>' where arr_org_id in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_send_org1 = '<新机构ID>' where before_send_org1 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_send_org1_name = '<新机构名称>' where before_send_org1_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set before_arr_org1 = '<新机构ID>' where before_arr_org1 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_arr_org1_name = '<新机构名称>' where before_arr_org1_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set before_send_org2 = '<新机构ID>' where before_send_org2 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_send_org2_name = '<新机构名称>' where before_send_org2_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set before_arr_org2 = '<新机构ID>' where before_arr_org2 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_arr_org2_name = '<新机构名称>' where before_arr_org2_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set before_send_org3 = '<新机构ID>' where before_send_org3 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_send_org3_name = '<新机构名称>' where before_send_org3_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set before_arr_org3 = '<新机构ID>' where before_arr_org3 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set before_arr_org3_name = '<新机构名称>' where before_arr_org3_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_send_org1 = '<新机构ID>' where after_send_org1 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_send_org1_name = '<新机构名称>' where after_send_org1_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_arr_org1 = '<新机构ID>' where after_arr_org1 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_arr_org1_name = '<新机构名称>' where after_arr_org1_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_send_org2 = '<新机构ID>' where after_send_org2 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_send_org2_name = '<新机构名称>' where after_send_org2_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_arr_org2 = '<新机构ID>' where after_arr_org2 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_arr_org2_name = '<新机构名称>' where after_arr_org2_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_send_org3 = '<新机构ID>' where after_send_org3 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_send_org3_name = '<新机构名称>' where after_send_org3_name in('<老机构名称>');
update tmsp.tmsp_order_route_org_detail set after_arr_org3 = '<新机构ID>' where after_arr_org3 in('<老机构ID>');
update tmsp.tmsp_order_route_org_detail set after_arr_org3_name = '<新机构名称>' where after_arr_org3_name in('<老机构名称>');
update tmsp.tmsp_turn_transfer_fee set site_org_id = '<新机构ID>' where site_org_id in('<老机构ID>');
update tmsp.tmsp_turn_transfer_fee set site_org_name = '<新机构名称>' where site_org_name in('<老机构名称>');
update tmsp.tmsp_net_rail_group set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');
update tmsp.tmsp_net_rail_group_item set load_org_id = '<新机构ID>' where load_org_id in('<老机构ID>');
update tmsp.tmsp_net_rail_group_item set arrive_org_id = '<新机构ID>' where arrive_org_id in('<老机构ID>');



-- TMSP insert
update tmsp.tmsp_net_org_hr set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update tmsp.tmsp_net_org_hr set hr_org_id = '<新机构ID>' where hr_org_id in('<老机构ID单个>');
replace INTO tmsp.tmsp_net_site_depart select serial_no, '<新机构ID>', depart_org_id, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_site_depart where site_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_site_depart select serial_no, site_org_id,'<新机构ID>', version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_site_depart where depart_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_debt_limit select serial_no, '<新机构ID>', max_limit, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org_debt_limit  where depart_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_remote_stock select serial_no, '<新机构ID>', inventory_org_id, activate_status, effect_time, disabled_time, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org_remote_stock where ticket_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_remote_stock select serial_no, ticket_org_id,'<新机构ID>', activate_status, effect_time, disabled_time, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org_remote_stock where inventory_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_stock select serial_no, stock_pos_id, '<新机构ID>', stock_pos_name, sup_stock_pos_id, stock_pos_level, stock_position_no, is_sync, sync_org_id, driver_id, vehicle_id, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time,last_city from tmsp.tmsp_net_org_stock where org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_stock select serial_no, stock_pos_id, org_id, stock_pos_name, sup_stock_pos_id, stock_pos_level, stock_position_no, is_sync,  '<新机构ID>', driver_id, vehicle_id, activate_status, version, remark, update_user_id, update_time, create_user_id, create_time,last_city from tmsp.tmsp_net_org_stock where sync_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_updown select serial_no, '<新机构ID>', next_org_id, hand_type, product_type, last_city, transfer_city, trans_way, schedule, order_cashing_time, last_cashing_arrive_time, last_send_time, transit_hour, arrive_time, route_nature, validity_start_time, validity_end_time, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_updown  where org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_updown select serial_no,  org_id,  '<新机构ID>', hand_type, product_type, last_city, transfer_city, trans_way, schedule, order_cashing_time, last_cashing_arrive_time, last_send_time, transit_hour, arrive_time, route_nature, validity_start_time, validity_end_time, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_updown  where next_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_product_line select serial_no, '<新机构ID>', product_type, last_city, is_area_net, income_org_id, effective_date, expiry_date, activate_status, delivery_gis_flag, share_ratio, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org_product_line where depart_org_id in('<老机构ID>');
update tmsp.tmsp_net_org_product_line set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org_ext(serial_no, org_id, brand, enable_remote_stock, driver_prefix, sign_radius, is_recode_recive, recive_price, recive_price_car, recive_price_cube, recive_price_weight, is_record_stock, resp_tel, org_name_tl, prov_code_tl, city_code_tl, area_code_tl, org_address_tl, longitude_tl, latitude_tl, org_name_zx, prov_code_zx, city_code_zx, area_code_zx, org_address_zx, longitude_zx, latitude_zx, is_lht, is_start_print, is_delivery_line, delivery_serial_start, cust_area, is_show_manufacturer, print_brand_name, version, remark, update_user_id, update_time, create_user_id, create_time,sale_edit_mode,print_template,is_loader,is_unload_register,plan_verify)  select serial_no, '<新机构ID>', brand, enable_remote_stock, driver_prefix, sign_radius, is_recode_recive, recive_price, recive_price_car, recive_price_cube, recive_price_weight, is_record_stock, resp_tel, org_name_tl, prov_code_tl, city_code_tl, area_code_tl, org_address_tl, longitude_tl, latitude_tl, org_name_zx, prov_code_zx, city_code_zx, area_code_zx, org_address_zx, longitude_zx, latitude_zx, is_lht, is_start_print, is_delivery_line, delivery_serial_start, cust_area,is_show_manufacturer, print_brand_name, version, remark, update_user_id, update_time, create_user_id, create_time,sale_edit_mode,print_template,is_loader,is_unload_register,plan_verify from tmsp.tmsp_net_org_ext where org_id in('<老机构ID>');
replace INTO tmsp.tmsp_net_org select serial_no, '<新机构ID>', org_code, '<新机构名称>', org_short_name, org_status, resp_user_id, org_type, net_station_type, country_code, prov_code, city_code, area_code, org_address, longitude, latitude, org_tel, '', '<新机构大区ID>', '<新机构小区ID>', shutdown_user_id, shutdown_time, sale_mode, '<新机构ID>', send_decision, is_self_car, is_local_net, own_site_org_id, enable_order, send_city, send_city_name, enable_trans, enable_stock, enable_last, enable_rail, last_city, enable_aging, version, remark, update_user_id, update_time, create_user_id, create_time from tmsp.tmsp_net_org where org_id in('<老机构ID>');
update tmsp.tmsp_net_org set own_site_org_id = '<新机构ID>' where own_site_org_id in('<老机构ID>');
replace into  tmsp.tmsp_own_driver_salary select serial_no,salary_month,'<新机构ID>',driver_salary,loader_salary,update_user_id,update_time,create_user_id,create_time from tmsp.tmsp_own_driver_salary where org_id in('<老机构ID>');





-- TMM
update tmm.tmm_report_trans_time set load_org1 = '<新机构ID>' where load_org1 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org1 = '<新机构ID>' where unload_org1 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org2 = '<新机构ID>' where load_org2 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org2 = '<新机构ID>' where unload_org2 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org3 = '<新机构ID>' where load_org3 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org3 = '<新机构ID>' where unload_org3 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org4 = '<新机构ID>' where load_org4 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org4 = '<新机构ID>' where unload_org4 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org5 = '<新机构ID>' where load_org5 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org5 = '<新机构ID>' where unload_org5 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org6 = '<新机构ID>' where load_org6 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org6 = '<新机构ID>' where unload_org6 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org7 = '<新机构ID>' where load_org7 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org7 = '<新机构ID>' where unload_org7 in('<老机构ID>');
update tmm.tmm_report_trans_time set load_org8 = '<新机构ID>' where load_org8 in('<老机构ID>');
update tmm.tmm_report_trans_time set unload_org8 = '<新机构ID>' where unload_org8 in('<老机构ID>');




-- CRM
update crm.crm_base_customer set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.cip_admin_hr_org set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_compete_similar_analysis set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_contract_attach set upload_org_id = '<新机构ID>' where upload_org_id in('<老机构ID>');
update crm.crm_org_dept_user_rel set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_emp set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_group set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_position set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_salesman set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_report_goods set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update crm.crm_report_goods_detailed set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update crm.crm_report_month_extension set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update crm.crm_sale_pipeline set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_sms_record set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_group set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_position set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_salesman set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_org_salesman set sales_group = '<新机构ID>' where sales_group in('<老机构ID>');
update crm.crm_relate_warn set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_report_month_extension set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update crm.crm_report_resource_log set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_report_target set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update crm.crm_sale_pipeline set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update crm.crm_base_grade set resp_busi_region='<新机构大区ID>' where resp_busi_region in('<老机构大区ID>');
update crm.crm_base_customer_delivery_config set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update crm.crm_base_customer_update_apply set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update crm.crm_base_cust_visiting set visit_org_id = '<新机构ID>' where visit_org_id in('<老机构ID>');
update crm.crm_base_customer set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update crm.crm_base_cust_goods_name_limit set org_id = '<新机构ID>',business_region_id = '<新机构大区ID>' where org_id in('<老机构ID单个>');
replace into  crm.crm_base_customer_spanned_area select serial_no, '<新机构大区ID>', prov_code, city_code, area_code, creator, create_time, op_user_id, update_time from crm.crm_base_customer_spanned_area  where business_region_id in('<老机构大区ID>');
replace INTO crm.crm_peer_competition  select serial_no, '<新机构ID>', company_name, scale, compete_relation, end_station_num, remark, creator, create_time, op_user_id, update_time from crm.crm_peer_competition where org_id in('<老机构ID>');
replace INTO crm.crm_customer_area(serial_no, area_name, org_id, op_user_id, update_time, creator, create_time) select serial_no, area_name,  '<新机构ID>', op_user_id, update_time, creator, create_time from crm.crm_customer_area where org_id in('<老机构ID>');


-- 主键特殊处理
INSERT IGNORE INTO crm.crm_org_dept SELECT '<新机构ID>' AS org_id, '<新机构小区ID>' AS sup_org_id, '<新机构名称>', main.org_type, main.org_charge, main.org_level, main.org_status, main.country_code, main.prov_code, main.city_code, main.area_code, main.org_adress, main.op_user_id, main.op_user_name, main.update_time, main.creator, main.creator_name, main.create_time, main.org_bigarea, main.org_area, main.root_flag, main.pinyin_search, main.is_effective, main.enable_customer FROM crm.crm_org_dept AS main WHERE org_id IN('<老机构ID>');
DELETE FROM crm.crm_org_dept WHERE org_id IN('<老机构ID>');



-- MPP
update mpp2.mpp2_arrive_storeage_price_ver set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_auth_apply set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_auth_code_info set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_auth_dept_link set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_cost_base set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_cost_base set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_cost_base set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_cost_base_his set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_cost_base_his set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_cost_base_his set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_cost_baset_item set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_cost_baset_item set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_cost_baset_item_his set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_cost_baset_item_his set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_customer_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_depart_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_depart_item set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_depart_item_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_discount_freight_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_discount_freight_item set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_discount_freight_item_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_expression_prise set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_fixed_price_ver set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_fixed_price_ver set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update mpp2.mpp2_increment_collect_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_collect_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_collect_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_collect_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_discharge_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_handle_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_insured_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_position_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_position_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_position_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_position_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_position_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_position_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receipt_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_receive_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_send_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_send_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_send_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_send_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_send_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_send_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_service_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_service_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_service_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_service_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_service_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_service_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_storage_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_storage_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_storage_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_storage_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_transfer_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_transfer_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_transfer_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_transfer_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc_h set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_upstairs_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_wait_disc set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_wait_disc set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_increment_wait_disc_h set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_increment_wait_disc_h set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_offer_external set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_count set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_count set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_his set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_count_his set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_his set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_item set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_count_item set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_item set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_item_his set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_count_item_his set creator_org_id = '<新机构ID>' where creator_org_id in('<老机构ID>');
update mpp2.mpp2_prise_count_item_his set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update mpp2.mpp2_prise_dept_ver set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_dept_ver_his set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_ver_cust set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_ver_cust set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update mpp2.mpp2_prise_ver_cust_his set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_prise_ver_cust_his set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update mpp2.mpp2_apply_approve_oa_rel set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update mpp2.mpp2_apply_price set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_apply_price set inventory_org_id = '<新机构ID>' where inventory_org_id in('<老机构ID>');
update mpp2.mpp2_apply_price set apply_org_id = '<新机构ID>' where apply_org_id in('<老机构ID>');
update mpp2.mpp2_increment_base_fee set depart_org = '<新机构ID>' where depart_org in('<老机构ID>');

-- 小区
update mpp.mpp_prise_dept_ver set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_dept_ver_his set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_cust_version set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_count_billing set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_ver_cust set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_unique_fee set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_ver_cust_his set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp.mpp_prise_cust_disc set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');
update mpp2.mpp2_apply_approve_trunk set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_apply_approve_trunk_h set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_base_cost set depart_org = '<新机构ID>',big_area='<新机构大区ID>',small_area='<新机构小区ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_base_cost_h set depart_org = '<新机构ID>',big_area='<新机构大区ID>',small_area='<新机构小区ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_increment_base_cust_fee set depart_org = '<新机构ID>',big_area = '<新机构大区ID>' where depart_org in('<老机构ID>');
update mpp2.mpp2_arrive_storeage_price_ver set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_auth_apply set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_auth_dept_link set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_cost_base set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_cost_base_his set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_customer_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_customer_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_depart_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_depart_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_freight_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_freight_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_fixed_price_ver set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_discharge_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_discharge_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_insured_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_insured_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_position_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_position_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_send_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_send_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_upstairs_disc set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_upstairs_disc_h set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_count set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_count_his set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_dept_ver set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_dept_ver_his set small_area = '<新机构小区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_send_lht set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',depart_org = '<新机构ID>' where depart_org in('<老机构ID单个>');


update mpp2.mpp2_arrive_storeage_price_ver set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_auth_apply set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_auth_dept_link set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_cost_base set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_cost_base_his set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_customer_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_customer_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_depart_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_depart_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_freight_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_discount_freight_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_fixed_price_ver set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_discharge_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_discharge_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_insured_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_insured_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_position_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_position_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_receive_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_receive_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_send_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_send_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_upstairs_disc set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_increment_upstairs_disc_h set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_offer_external set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_count set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_count_his set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_dept_ver set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';
update mpp2.mpp2_prise_dept_ver_his set big_area = '<新机构大区ID>' where depart_org = '<新机构ID>';





-- BMSP
update bmsp.bmsp_report_daily_staff set daily_dept = '<新机构ID>' where daily_dept in('<老机构ID>');
update bmsp.bmsp_borrow_statement_info set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_report_income_day set start_org = '<新机构ID>' where start_org in('<老机构ID>');
update bmsp.bmsp_report_income_day set income_dept = '<新机构ID>' where income_dept in('<老机构ID>');
update bmsp.bmsp_report_income_day set collection_dept = '<新机构ID>' where collection_dept in('<老机构ID>');
update bmsp.bmsp_borrow_info set borrow_user_org = '<新机构ID>' where borrow_user_org in('<老机构ID>');
update bmsp.bmsp_bank_channel set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_archives_house_contract set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_archives_purchase_contract set big_area = '<新机构大区ID>',small_area = '<新机构小区ID>',org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_inorder set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_inorder_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_outcost set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_outcost set fee_bear_orgid = '<新机构ID>' where fee_bear_orgid in('<老机构ID>');
update bmsp.bmsp_balance_outcost_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_outorder set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_balance_outorder set fee_bear_orgid  = '<新机构ID>' where fee_bear_orgid  in('<老机构ID>');
update bmsp.bmsp_balance_outorder_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_bank_claim_detail set belong_orgid = '<新机构ID>' where belong_orgid in('<老机构ID>');
update bmsp.bmsp_bank_claim_detail set claim_org_id = '<新机构ID>' where claim_org_id in('<老机构ID>');
update bmsp.bmsp_bank_payment set op_org_id = '<新机构ID>' where op_org_id in('<老机构ID>');
update bmsp.bmsp_invoice_apply set invoice_org_id = '<新机构ID>' where invoice_org_id in('<老机构ID>');
update bmsp.bmsp_invoice_apply set invoice_org_name = '<新机构名称>' where invoice_org_name in('<老机构名称>');
update bmsp.bmsp_invoice_balance set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_oa_payment set charges_org_id = '<新机构ID>' where charges_org_id in('<老机构ID>');
update bmsp.bmsp_oa_payment set apply_org_id = '<新机构ID>' where apply_org_id in('<老机构ID>');
update bmsp.bmsp_oa_payment set depart_id = '<新机构ID>' where depart_id in('<老机构ID>');

update bmsp.bmsp_oa_payment_item set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');
update bmsp.bmsp_pre_doc set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_pre_doc set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update bmsp.bmsp_report_inorder_cust set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update bmsp.bmsp_report_inorder_cust set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update bmsp.bmsp_report_trans_load_log set last_org_id_before = '<新机构ID>' where last_org_id_before in('<老机构ID>');
update bmsp.bmsp_report_trans_load_log set last_org_id_after = '<新机构ID>' where last_org_id_after in('<老机构ID>');
update bmsp.bmsp_report_trans_load_log set last_org_id = '<新机构ID>' where last_org_id in('<老机构ID>');
update bmsp.bmsp_should_badorder set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_busi_cash set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_inorder set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_inorder set income_org_id = '<新机构ID>' where income_org_id in('<老机构ID>');
update bmsp.bmsp_should_inorder_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_outcost set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_outcost set fee_bear_orgid = '<新机构ID>' where fee_bear_orgid in('<老机构ID>');
update bmsp.bmsp_should_outcost set outpay_org_id = '<新机构ID>' where outpay_org_id in('<老机构ID>');
update bmsp.bmsp_should_outcost_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_outcost_mixload set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_outorder set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_should_outorder set outpay_org_id = '<新机构ID>' where outpay_org_id in('<老机构ID>');
update bmsp.bmsp_should_outorder_clear set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update bmsp.bmsp_ticket_arrive set print_org_id = '<新机构ID>' where print_org_id in('<老机构ID>');
update bmsp.bmsp_ticket_arrive_cancel set print_org_id = '<新机构ID>' where print_org_id in('<老机构ID>');
update bmsp.bmsp_ticket_arrive_cancel set cancel_org_id = '<新机构ID>' where cancel_org_id in('<老机构ID>');
update bmsp.bmsp_ticket_arrive_clear set resp_org_id = '<新机构ID>' where resp_org_id in('<老机构ID>');




-- AUTH
update auth.auth_account_subject_info set subject_org_id = '<新机构ID>' where subject_org_id in('<老机构ID>');
update auth.auth_account_subject_session set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update auth.auth_user_position set org_id = '<新机构ID>' where org_id in('<老机构ID>');
update auth.auth_user_userinfo set org_id = '<新机构ID>', org_power = '<新机构ID>' where org_id in('<老机构ID>');
update auth.auth_user_org set org_id = '<新机构ID>' where org_id in('<老机构ID单个>');
update auth.auth_user_org set parent_org_id = '<新机构ID>' where parent_org_id in('<老机构ID单个>');
update auth.auth_user_org set org_name = '<新机构名称>' where org_name in('<老机构名称>');
update auth.auth_permission_settings_rule set settings_value = REPLACE(settings_value,'<替换老机构ID集合>','<新机构ID>') where settings_value regexp '<老机构ID集合>' and settings_value like '25%' ;
-- 唯一索引特殊处理
INSERT IGNORE INTO auth.auth_permission_settings_value SELECT main.company_id,main.name_space_id,main.app_id,main.item_code,main.user_id,'<新机构ID>' AS setting_value,main.setting_value_remark,main.standard,main.remark,main.op_user_id,main.op_user_name,main.update_time,main.creator,main.creator_name,main.create_time FROM auth.auth_permission_settings_value AS main WHERE main.setting_value in('<老机构ID>');
DELETE FROM auth.auth_permission_settings_value WHERE setting_value in('<老机构ID>');



-- BDS
update bds.bds_backdoc_different set different_org_id = '<新机构ID>' where different_org_id in('<老机构ID>');
update bds.bds_backdoc_different set above_org_id = '<新机构ID>' where above_org_id in('<老机构ID>');
update bds.bds_backdoc_different set different_clear_org_id = '<新机构ID>' where different_clear_org_id in('<老机构ID>');
update bds.bds_backdoc_different_clear set different_clear_org_id = '<新机构ID>' where different_clear_org_id in('<老机构ID>');
update bds.bds_backdoc_handover set above_org_id = '<新机构ID>' where above_org_id in('<老机构ID>');
update bds.bds_backdoc_handover set next_org_id = '<新机构ID>' where next_org_id in('<老机构ID>');
update bds.bds_backdoc_handover_file set upload_org_id = '<新机构ID>' where upload_org_id in('<老机构ID>');
update bds.bds_backdoc_info set ticket_org_id = '<新机构ID>' where ticket_org_id in('<老机构ID>');
update bds.bds_backdoc_info set backdoc_load_org_id = '<新机构ID>' where backdoc_load_org_id in('<老机构ID>');
update bds.bds_backdoc_info set backdoc_arrive_org_id = '<新机构ID>' where backdoc_arrive_org_id in('<老机构ID>');
update bds.bds_backdoc_photo_info set upload_org_id = '<新机构ID>' where upload_org_id in('<老机构ID>');
update bds.bds_backdoc_print_record set print_org_id = '<新机构ID>' where print_org_id in('<老机构ID>');
update bds.bds_backdoc_route set ticket_org_id = '<新机构ID>',region_id='<新机构大区ID>',district_id='<新机构小区ID>' where ticket_org_id in('<老机构ID>');
update bds.bds_backdoc_route set return_sure_org_id = '<新机构ID>' where return_sure_org_id in('<老机构ID>');
update bds.bds_backdoc_stock set stock_org_id = '<新机构ID>' where stock_org_id in('<老机构ID>');
update bds.bds_backdoc_stock set different_org_id = '<新机构ID>' where different_org_id in('<老机构ID>');
update bds.bds_backdoc_stock set above_org_id = '<新机构ID>' where above_org_id in('<老机构ID>');
update bds.bds_backdoc_stock set next_org_id = '<新机构ID>' where next_org_id in('<老机构ID>');
update bds.bds_backdoc_track set above_org_id = '<新机构ID>' where above_org_id in('<老机构ID>');
update bds.bds_backdoc_track set next_org_id = '<新机构ID>' where next_org_id in('<老机构ID>');
update bds.bds_backdoc_track set operate_org_id = '<新机构ID>' where operate_org_id in('<老机构ID>');

delete from tmsp.tmsp_take_order_model where user_id in(select emp_id from hcm.hcm_emp_ent where dept='<新机构ID>') and model_info regexp '<老机构ID集合>';

update isp.isp_work_issues_info set source_org_id='<新机构ID>',source_org_name='<新机构名称>' where source_org_id in('<新机构ID>');