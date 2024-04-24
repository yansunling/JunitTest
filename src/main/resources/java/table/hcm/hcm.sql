delete from hcm.hcm_org_info where org_id like '25%';
delete from hcm.hcm_org_rel where org_id like '25%' or  rel_org_id like '25%';
delete  from hcm.hcm_org_rec where org_id like '25%';
delete  from hcm.hcm_org_relation where org_id like '25%';
delete  from hcm.hcm_org_relation_all where org_id like '25%';
INSERT ignore INTO hcm.hcm_org_relation(org_id, org_name, org_nature, business_region_id, business_district_id) select main.org_id,main.org_name,main.org_nature,left(main.org_id,8),left(main.org_id,10) from hcm.hcm_org_info main where org_status='1';
INSERT ignore INTO hcm.hcm_org_relation_all(org_id, org_name, org_nature, business_region_id, business_district_id) select main.org_id,main.org_name,main.org_nature,left(main.org_id,8),left(main.org_id,10) from hcm.hcm_org_info main where org_status='1';


replace INTO hcm.hcm_org_info select '<新机构ID>', '<新机构名称>', org_charge, org_adress, org_status, create_date, creater, operator, op_time, send_status, org_type, country_code, prov_code, city_code, area_code, person_budget, entry_number, org_nature, tax_subject, nature, depart_nature, version, remark, update_user_id, update_time, create_user_id, create_time from hcm.hcm_org_info where org_id in('<老机构ID>');
insert ignore into hcm.hcm_org_info values ('<新机构ID>', '<新机构名称>', '', '', '1', '2024-05-05', NULL, '', now(), 0, NULL, NULL, '310000', '310100', '310118', 0, 0, 'shtl', 'shtl', NULL, '1', 0, '', '', now(), now(), now());




