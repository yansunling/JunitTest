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
select ref0.new_org_id,main.org_name,main.org_charge,main.org_adress,main.org_status,main.create_date,main.creater,main.operator,main.op_time,main.send_status,main.org_type,main.country_code,main.prov_code,main.city_code,main.area_code,main.person_budget,main.entry_number,main.org_nature,main.tax_subject,main.nature,main.depart_nature,main.version,main.remark,main.update_user_id,main.update_time,main.create_user_id,main.create_time
from hcm.hcm_org_info main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id;



insert ignore into hcm.hcm_org_relation(org_id,org_name,org_nature,business_region_id,business_district_id,committee_id,sup_org_id,sup_org_nature)
select ref0.new_org_id,main.org_name,main.org_nature,ifull(ref1.new_org_id,''),ifnull(ref2.new_org_id,''),ifnull(ref3.new_org_id,''),ref4.new_org_id,main.sup_org_nature
from hcm.hcm_org_relation main
inner join tmsp.tmsp_org_new_old_ref ref0 on ref0.old_org_id=main.org_id
left join tmsp.tmsp_org_new_old_ref ref1 on ref1.old_org_id=main.business_region_id
left join tmsp.tmsp_org_new_old_ref ref2 on ref2.old_org_id=main.business_district_id
left join tmsp.tmsp_org_new_old_ref ref3 on ref3.old_org_id=main.committee_id
inner join tmsp.tmsp_org_new_old_ref ref4 on ref4.old_org_id=main.sup_org_id;





delete from hcm.hcm_org_info where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_rec where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_relation where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_relation_all where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);
delete from hcm.hcm_org_rel where org_id in(select old_org_id from tmsp.tmsp_org_new_old_ref);


