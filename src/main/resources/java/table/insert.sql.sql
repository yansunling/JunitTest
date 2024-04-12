insert ignore into hcm.hcm_org_rec(org_id,org_rel_rec,sup_org_id,root_org_id,org_level,operator,op_time,remark,update_user_id,update_time,create_user_id,create_time)
select ref0.new_org_id,main.org_rel_rec,ref1.new_org_id,main.root_org_id,main.org_level,main.operator,main.op_time,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from hcm.hcm_org_rec main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.sup_org_id;



insert ignore into hcm.hcm_org_rel(org_id,rel_org_id,start_date,end_date,org_name,org_rel_type,create_date,creater,operator,op_time,send_status,remark,update_user_id,update_time,create_user_id,create_time)
select ref0.new_org_id,ref1.new_org_id,main.start_date,main.end_date,main.org_name,main.org_rel_type,main.create_date,main.creater,main.operator,main.op_time,main.send_status,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from hcm.hcm_org_rel main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.rel_org_id;



insert ignore into hcm.hcm_org_relation_all(org_id,org_name,org_nature,business_region_id,business_district_id,committee_id,sup_org_id,sup_org_nature)
select ref0.new_org_id,main.org_name,main.org_nature,ifull(ref1.new_org_id,''),ifnull(ref2.new_org_id,''),ifnull(ref3.new_org_id,''),ref4.new_org_id,main.sup_org_nature
from hcm.hcm_org_relation_all main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
left join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.business_region_id
left join tmsp.tmsp_org_new_old_ref ref2 on ref2.old_org_id=main.business_district_id
left join tmsp.tmsp_org_new_old_ref ref3 on ref3.old_org_id=main.committee_id
inner join tmsp.tmsp_org_new_old_ref ref4 on ref4.old_org_id=main.sup_org_id;



insert ignore into hcm.hcm_org_info(org_id,org_name,org_charge,org_adress,org_status,create_date,creater,operator,op_time,send_status,org_type,country_code,prov_code,city_code,area_code,person_budget,entry_number,org_nature,tax_subject,nature,depart_nature,version,remark,update_user_id,update_time,create_user_id,create_time)
select ref0.new_org_id,ref0.new_org_name,main.org_charge,main.org_adress,main.org_status,main.create_date,main.creater,main.operator,main.op_time,main.send_status,main.org_type,main.country_code,main.prov_code,main.city_code,main.area_code,main.person_budget,main.entry_number,main.org_nature,main.tax_subject,main.nature,main.depart_nature,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from hcm.hcm_org_info main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id;



insert ignore into hcm.hcm_org_relation(org_id,org_name,org_nature,business_region_id,business_district_id,committee_id,sup_org_id,sup_org_nature)
select ref0.new_org_id,ref0.new_org_name,main.org_nature,ref0.business_region_id,ref0.business_district_id,'',ref4.new_org_id,main.sup_org_nature
from hcm.hcm_org_relation main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
inner join tmsp.tmsp_org_new_old_ref ref4 on ref4.old_org_id=main.sup_org_id;






insert ignore into tmsp.tmsp_net_org_product_line(serial_no,depart_org_id,product_type,last_city,is_area_net,income_org_id,effective_date,expiry_date,activate_status,delivery_gis_flag,share_ratio,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,main.product_type,main.last_city,main.is_area_net,ref1.new_org_id,main.effective_date,main.expiry_date,main.activate_status,main.delivery_gis_flag,main.share_ratio,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_product_line main
       inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.depart_org_id
       inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.income_org_id;



insert ignore into tmsp.tmsp_net_site_depart(serial_no,site_org_id,depart_org_id,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,ref1.new_org_id,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_site_depart main
       inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.site_org_id
       inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.depart_org_id;



insert ignore into tmsp.tmsp_net_updown(serial_no,org_id,next_org_id,hand_type,product_type,last_city,transfer_city,trans_way,schedule,order_cashing_time,last_cashing_arrive_time,last_send_time,transit_hour,arrive_time,route_nature,validity_start_time,validity_end_time,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,ref1.new_org_id,main.hand_type,main.product_type,main.last_city,main.transfer_city,main.trans_way,main.schedule,main.order_cashing_time,main.last_cashing_arrive_time,main.last_send_time,main.transit_hour,main.arrive_time,main.route_nature,main.validity_start_time,main.validity_end_time,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_updown main
       inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
       inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.next_org_id;



insert ignore into tmsp.tmsp_net_org(serial_no,org_id,org_code,org_name,org_short_name,org_status,resp_user_id,org_type,net_station_type,country_code,prov_code,city_code,area_code,org_address,longitude,latitude,org_tel,committee_id,business_region_id,business_district_id,shutdown_user_id,shutdown_time,sale_mode,send_hr_org,send_decision,is_self_car,is_local_net,own_site_org_id,enable_order,send_city,send_city_name,enable_trans,enable_stock,enable_last,enable_rail,last_city,enable_aging,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,main.org_code,ref0.new_org_name,main.org_short_name,main.org_status,main.resp_user_id,main.org_type,main.net_station_type,main.country_code,main.prov_code,main.city_code,main.area_code,main.org_address,main.longitude,main.latitude,main.org_tel,ref1.new_org_id,ref2.new_org_id,ref3.new_org_id,main.shutdown_user_id,main.shutdown_time,main.sale_mode,main.send_hr_org,main.send_decision,main.is_self_car,main.is_local_net,main.own_site_org_id,main.enable_order,main.send_city,main.send_city_name,main.enable_trans,main.enable_stock,main.enable_last,main.enable_rail,main.last_city,main.enable_aging,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
left join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.committee_id
left join tmsp.tmsp_org_new_old_ref ref2 on ref2.old_org_id=main.business_region_id
left join tmsp.tmsp_org_new_old_ref ref3 on ref3.old_org_id=main.business_district_id;



insert ignore into tmsp.tmsp_net_org_ext(serial_no,org_id,brand,enable_remote_stock,driver_prefix,sign_radius,is_recode_recive,recive_price,recive_price_car,recive_price_cube,recive_price_weight,is_record_stock,resp_tel,org_name_tl,prov_code_tl,city_code_tl,area_code_tl,org_address_tl,longitude_tl,latitude_tl,org_name_zx,prov_code_zx,city_code_zx,area_code_zx,org_address_zx,longitude_zx,latitude_zx,is_lht,is_start_print,is_delivery_line,delivery_serial_start,cust_area,print_brand_name,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,main.brand,main.enable_remote_stock,main.driver_prefix,main.sign_radius,main.is_recode_recive,main.recive_price,main.recive_price_car,main.recive_price_cube,main.recive_price_weight,main.is_record_stock,main.resp_tel,main.org_name_tl,main.prov_code_tl,main.city_code_tl,main.area_code_tl,main.org_address_tl,main.longitude_tl,main.latitude_tl,main.org_name_zx,main.prov_code_zx,main.city_code_zx,main.area_code_zx,main.org_address_zx,main.longitude_zx,main.latitude_zx,main.is_lht,main.is_start_print,main.is_delivery_line,main.delivery_serial_start,main.cust_area,main.print_brand_name,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_ext main
       inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id;



insert ignore into tmsp.tmsp_net_org_debt_limit(serial_no,depart_org_id,max_limit,activate_status,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,main.max_limit,main.activate_status,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_debt_limit main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.depart_org_id;



insert ignore into tmsp.tmsp_net_org_hr(serial_no,org_id,hr_org_id,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,ref1.new_org_id,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_hr main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.hr_org_id;



insert ignore into tmsp.tmsp_net_org_remote_stock(serial_no,ticket_org_id,inventory_org_id,activate_status,effect_time,disabled_time,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),ref0.new_org_id,ref1.new_org_id,main.activate_status,main.effect_time,main.disabled_time,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_remote_stock main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.ticket_org_id
inner join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.inventory_org_id;



insert ignore into tmsp.tmsp_net_org_stock(serial_no,stock_pos_id,org_id,stock_pos_name,sup_stock_pos_id,stock_pos_level,stock_position_no,is_sync,sync_org_id,driver_id,vehicle_id,activate_status,version,remark,update_user_id,update_time,create_user_id,create_time)
select uuid_short(),main.stock_pos_id,ref0.new_org_id,main.stock_pos_name,main.sup_stock_pos_id,main.stock_pos_level,main.stock_position_no,main.is_sync,main.sync_org_id,main.driver_id,main.vehicle_id,main.activate_status,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from tmsp.tmsp_net_org_stock main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id;
















delete from hcm.hcm_org_info where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_rec where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_relation where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_relation_all where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_rel where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_product_line where depart_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_product_line where income_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_site_depart where site_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_site_depart where depart_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_updown where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_updown where next_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org where committee_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org where business_region_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org where business_district_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_ext where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_debt_limit where depart_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_hr where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_hr where hr_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_remote_stock where ticket_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_remote_stock where inventory_org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from tmsp_net_org_stock where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);


