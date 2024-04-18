

rename table tmsp.tmsp_order_route_org_detail to tmsp.tmsp_order_route_org_detail_old;


CREATE TABLE tmsp.`tmsp_order_route_org_detail` (
  `order_id` varchar(32) NOT NULL DEFAULT '' COMMENT '运单号',
  `send_org_id` varchar(32) NOT NULL DEFAULT '' COMMENT '发出站点',
  `arr_org_id` varchar(32) NOT NULL DEFAULT '' COMMENT '到达站点',
  `before_send_org1` varchar(32) DEFAULT '' COMMENT '前1装车部门',
  `before_send_org1_name` varchar(32) DEFAULT '' COMMENT '前1装车部门名称',
  `before_arr_org1` varchar(32) DEFAULT '' COMMENT '前1到达部门',
  `before_arr_org1_name` varchar(32) DEFAULT '' COMMENT '前1到达部门名称',
  `before_send_org2` varchar(32) DEFAULT '' COMMENT '前2装车部门',
  `before_send_org2_name` varchar(32) DEFAULT '' COMMENT '前2装车部门名称',
  `before_arr_org2` varchar(32) DEFAULT '' COMMENT '前2到达部门',
  `before_arr_org2_name` varchar(32) DEFAULT '' COMMENT '前2到达部门名称',
  `before_send_org3` varchar(32) DEFAULT '' COMMENT '前3装车车部门',
  `before_send_org3_name` varchar(32) DEFAULT '' COMMENT '前3装车部门名称',
  `before_arr_org3` varchar(32) DEFAULT '' COMMENT '前3到达部门',
  `before_arr_org3_name` varchar(32) DEFAULT '' COMMENT '前3到达部门名称',
  `after_send_org1` varchar(32) DEFAULT '' COMMENT '后1装车部门',
  `after_send_org1_name` varchar(32) DEFAULT '' COMMENT '后1装车部门名称',
  `after_arr_org1` varchar(32) DEFAULT '' COMMENT '后1到达部门',
  `after_arr_org1_name` varchar(32) DEFAULT '' COMMENT '后1到达部门名称',
  `after_send_org2` varchar(32) DEFAULT '' COMMENT '后2装车部门',
  `after_send_org2_name` varchar(32) DEFAULT '' COMMENT '后2装车部门名称',
  `after_arr_org2` varchar(32) DEFAULT '' COMMENT '后2到达部门',
  `after_arr_org2_name` varchar(32) DEFAULT '' COMMENT '后2到达部门名称',
  `after_send_org3` varchar(32) DEFAULT '' COMMENT '后3装车车部门',
  `after_send_org3_name` varchar(32) DEFAULT '' COMMENT '后3装车部门名称',
  `after_arr_org3` varchar(32) DEFAULT '' COMMENT '后3到达部门',
  `after_arr_org3_name` varchar(32) DEFAULT '' COMMENT '后3到达部门名称',
  PRIMARY KEY (`order_id`,`send_org_id`,`arr_org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

call tmsp.update_table(33);