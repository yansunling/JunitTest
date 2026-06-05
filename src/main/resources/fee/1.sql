INSERT INTO tmsp.tmsp_order_fee(serial_no, order_id, fee_type, should_in_amount, actual_in_amount, price_ver, item_ver, discount_ver, price_remark, version, remark, update_user_id, update_time, create_user_id, create_time) VALUES (uuid_short(), '运单号', '1003', 运费, 运费, '', '', '', '', 1, '', 'T3477',now(), 'T3477', now());
update tmsp.tmsp_order_fee set should_in_amount=should_in_amount-运费,actual_in_amount=actual_in_amount-运费 where order_id='运单号' and fee_type='1000';
update tmsp.tmsp_order_fee_detail set fee_should_1000=fee_should_1000-运费,fee_should_1000=fee_should_1000-运费, fee_should_1003=运费,fee_actual_1003=运费  where order_id='运单号';
update tmsp.tmsp_order_profile set receive_type='except' where order_id='运单号';
