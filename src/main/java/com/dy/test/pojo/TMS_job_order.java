package com.dy.test.pojo;

import com.yd.utils.mq.ChannelMessage;

public class TMS_job_order extends  ChannelMessage{
		
		private static final long serialVersionUID = 6034430420264183867L;
		//运单号
		private String ticket_id;
		//是否内部带货
		private String self_goods_flag;
		//是否内部带货单据
		private String self_bill_flag;
		//是否异常货物
		private String excep_flag;
		//状态
		private String ticket_status;
		//审核状态
		private String audit_status;
		//开单营业部
		private String ticket_org_id;
		//收货部门
		private String receive_org_id;
		//目的部门
		private String send_org_id;
		//手机号（发货人)
		private String send_mobile;
		//发货人
		private String send_cust_name;
		//固定电话（发货人)
		private String send_contact;
		//客户编码（发货人)
		private String send_customer_id;
		//省（发货人）
		private int send_prov_code;
		//市（发货人）
		private int send_city_code;
		//区县（发货人）
		private int send_area_code;
		//详细地址（发货人)
		private String send_address;
		//收货人客户编号
		private String recv_customer_id;
		//手机号（收货人姓名)
		private String recv_cust_name;
		//收货人手机号
		private String recv_mobile;
		//固定电话（收发货人)
		private String recv_contact;
		//省（收货人）
		private int recv_prov_code;
		//市（收货人）
		private int recv_city_code;
		//区县（收货人)
		private int recv_area_code;
		//详细地址（收货人)
		private String recv_address;
		//地址备注
		private String recv_addr_remark;
		//计价方案
		private String pricing_mode;
		//运输方式
		private String trans_type;
		//送货方式
		private String receive_type;
		//下单方式
		private String order_type;
		//约车单号
		private String book_doc_id;
		//是否返货
		private String is_re_goods;
		//原单号
		private String old_ticket_id;
		//货物名称
		private String goods_name;
		//件数
		private int goods_count;
		//重量
		private double goods_weight;
		//长宽高
		private String goods_lwh;
		//体积
		private double goods_cube;
		//贵重物品
		private String valuables_flag;
		//包装类型
		private String package_type;
		//注意事项
		private String trans_attention;
		//折扣类型
		private String discount_type;
		//折扣方案
		private String discount_scheme;
		//声明保价
		private double goods_value;
		//保价费
		private double insure_fee;
		//综合信息费
		private double synthesize_value;
		//燃油附加费
		private double fuel_value;
		//包装费
		private double package_value;
		//是否代收货款
		private String cod_flag;
		//代收货款类型
		private String cod_type;
		//代收货款
		private double cod_amount;
		//代收手续费
		private double cod_charges;
		//收款人
		private String cod_account_name;
		//开户行
		private String cod_bank;
		//收款账户
		private String cod_account;
		//是否返单
		private String return_tip_flag;
		//返单类型
		private String return_tip_type;
		//回单费
		private double return_tip_value;
		//上楼费
		private double upstairs_value;
		//是否进仓
		private String warehouse_flag;
		//进仓费
		private double warehouse_value;
		//其他费用
		private double other_value;
		//付款方式
		private String pay_mode;
		//干线运费
		private double line_fee;
		//接货费
		private double receive_fee;
		//送货费
		private double send_fee;
		//超远派送费
		private double send_long_fee;
		//劳务费
		private double labour_fee;
		//整车报价
		private double vehicle_fee;
		//整车利润
		private double vehicle_profit;
		//整车操作费
		private double vehicle_op_fee;
		//整车额外利润
		private double vehicle_extra_profit;
		//现付金额
		private double start_pay_amount;
		//到付金额
		private double arrive_pay_amount;
		//临欠金额
		private double owing_amount;
		//月结金额
		private double month_amount;
		//应收总额
		private double total_fee;
		//预估毛利润
		private double plan_revenue;
		//运单所属部门
		private String business_org_id;

	    public String getTicket_id() {
			return ticket_id;
		}



		public void setTicket_id(String ticket_id) {
			this.ticket_id = ticket_id;
		}



		public String getSelf_goods_flag() {
			return self_goods_flag;
		}



		public void setSelf_goods_flag(String self_goods_flag) {
			this.self_goods_flag = self_goods_flag;
		}



		public String getSelf_bill_flag() {
			return self_bill_flag;
		}



		public void setSelf_bill_flag(String self_bill_flag) {
			this.self_bill_flag = self_bill_flag;
		}



		public String getExcep_flag() {
			return excep_flag;
		}



		public void setExcep_flag(String excep_flag) {
			this.excep_flag = excep_flag;
		}



		public String getTicket_status() {
			return ticket_status;
		}



		public void setTicket_status(String ticket_status) {
			this.ticket_status = ticket_status;
		}



		public String getAudit_status() {
			return audit_status;
		}



		public void setAudit_status(String audit_status) {
			this.audit_status = audit_status;
		}



		public String getTicket_org_id() {
			return ticket_org_id;
		}



		public void setTicket_org_id(String ticket_org_id) {
			this.ticket_org_id = ticket_org_id;
		}



		public String getReceive_org_id() {
			return receive_org_id;
		}



		public void setReceive_org_id(String receive_org_id) {
			this.receive_org_id = receive_org_id;
		}



		public String getSend_org_id() {
			return send_org_id;
		}



		public void setSend_org_id(String send_org_id) {
			this.send_org_id = send_org_id;
		}



		public String getSend_mobile() {
			return send_mobile;
		}



		public void setSend_mobile(String send_mobile) {
			this.send_mobile = send_mobile;
		}



		public String getSend_cust_name() {
			return send_cust_name;
		}



		public void setSend_cust_name(String send_cust_name) {
			this.send_cust_name = send_cust_name;
		}



		public String getSend_contact() {
			return send_contact;
		}



		public void setSend_contact(String send_contact) {
			this.send_contact = send_contact;
		}



		public String getSend_customer_id() {
			return send_customer_id;
		}



		public void setSend_customer_id(String send_customer_id) {
			this.send_customer_id = send_customer_id;
		}



		public int getSend_prov_code() {
			return send_prov_code;
		}



		public void setSend_prov_code(int send_prov_code) {
			this.send_prov_code = send_prov_code;
		}



		public int getSend_city_code() {
			return send_city_code;
		}



		public void setSend_city_code(int send_city_code) {
			this.send_city_code = send_city_code;
		}



		public int getSend_area_code() {
			return send_area_code;
		}



		public void setSend_area_code(int send_area_code) {
			this.send_area_code = send_area_code;
		}



		public String getSend_address() {
			return send_address;
		}



		public void setSend_address(String send_address) {
			this.send_address = send_address;
		}



		public String getRecv_cust_name() {
			return recv_cust_name;
		}



		public void setRecv_cust_name(String recv_cust_name) {
			this.recv_cust_name = recv_cust_name;
		}



		public String getRecv_customer_id() {
			return recv_customer_id;
		}



		public void setRecv_customer_id(String recv_customer_id) {
			this.recv_customer_id = recv_customer_id;
		}



		public String getRecv_contact() {
			return recv_contact;
		}



		public void setRecv_contact(String recv_contact) {
			this.recv_contact = recv_contact;
		}



		public int getRecv_prov_code() {
			return recv_prov_code;
		}



		public void setRecv_prov_code(int recv_prov_code) {
			this.recv_prov_code = recv_prov_code;
		}



		public int getRecv_city_code() {
			return recv_city_code;
		}



		public void setRecv_city_code(int recv_city_code) {
			this.recv_city_code = recv_city_code;
		}



		public int getRecv_area_code() {
			return recv_area_code;
		}



		public void setRecv_area_code(int recv_area_code) {
			this.recv_area_code = recv_area_code;
		}



		public String getRecv_address() {
			return recv_address;
		}



		public void setRecv_address(String recv_address) {
			this.recv_address = recv_address;
		}



		public String getRecv_addr_remark() {
			return recv_addr_remark;
		}



		public void setRecv_addr_remark(String recv_addr_remark) {
			this.recv_addr_remark = recv_addr_remark;
		}



		public String getPricing_mode() {
			return pricing_mode;
		}



		public void setPricing_mode(String pricing_mode) {
			this.pricing_mode = pricing_mode;
		}



		public String getTrans_type() {
			return trans_type;
		}



		public void setTrans_type(String trans_type) {
			this.trans_type = trans_type;
		}



		public String getReceive_type() {
			return receive_type;
		}



		public void setReceive_type(String receive_type) {
			this.receive_type = receive_type;
		}



		public String getOrder_type() {
			return order_type;
		}



		public void setOrder_type(String order_type) {
			this.order_type = order_type;
		}



		public String getBook_doc_id() {
			return book_doc_id;
		}



		public void setBook_doc_id(String book_doc_id) {
			this.book_doc_id = book_doc_id;
		}



		public String getIs_re_goods() {
			return is_re_goods;
		}



		public void setIs_re_goods(String is_re_goods) {
			this.is_re_goods = is_re_goods;
		}



		public String getOld_ticket_id() {
			return old_ticket_id;
		}



		public void setOld_ticket_id(String old_ticket_id) {
			this.old_ticket_id = old_ticket_id;
		}



		public String getGoods_name() {
			return goods_name;
		}



		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}



		public int getGoods_count() {
			return goods_count;
		}



		public void setGoods_count(int goods_count) {
			this.goods_count = goods_count;
		}



		public double getGoods_weight() {
			return goods_weight;
		}



		public void setGoods_weight(double goods_weight) {
			this.goods_weight = goods_weight;
		}



		public String getGoods_lwh() {
			return goods_lwh;
		}



		public void setGoods_lwh(String goods_lwh) {
			this.goods_lwh = goods_lwh;
		}



		public double getGoods_cube() {
			return goods_cube;
		}



		public void setGoods_cube(double goods_cube) {
			this.goods_cube = goods_cube;
		}



		public String getValuables_flag() {
			return valuables_flag;
		}



		public void setValuables_flag(String valuables_flag) {
			this.valuables_flag = valuables_flag;
		}



		public String getPackage_type() {
			return package_type;
		}



		public void setPackage_type(String package_type) {
			this.package_type = package_type;
		}



		public String getTrans_attention() {
			return trans_attention;
		}



		public void setTrans_attention(String trans_attention) {
			this.trans_attention = trans_attention;
		}



		public String getDiscount_type() {
			return discount_type;
		}



		public void setDiscount_type(String discount_type) {
			this.discount_type = discount_type;
		}



		public String getDiscount_scheme() {
			return discount_scheme;
		}



		public void setDiscount_scheme(String discount_scheme) {
			this.discount_scheme = discount_scheme;
		}



		public double getGoods_value() {
			return goods_value;
		}



		public void setGoods_value(double goods_value) {
			this.goods_value = goods_value;
		}



		public double getInsure_fee() {
			return insure_fee;
		}



		public void setInsure_fee(double insure_fee) {
			this.insure_fee = insure_fee;
		}



		public double getSynthesize_value() {
			return synthesize_value;
		}



		public void setSynthesize_value(double synthesize_value) {
			this.synthesize_value = synthesize_value;
		}



		public double getFuel_value() {
			return fuel_value;
		}



		public void setFuel_value(double fuel_value) {
			this.fuel_value = fuel_value;
		}



		public double getPackage_value() {
			return package_value;
		}



		public void setPackage_value(double package_value) {
			this.package_value = package_value;
		}



		public String getCod_flag() {
			return cod_flag;
		}



		public void setCod_flag(String cod_flag) {
			this.cod_flag = cod_flag;
		}



		public String getCod_type() {
			return cod_type;
		}



		public void setCod_type(String cod_type) {
			this.cod_type = cod_type;
		}



		public double getCod_amount() {
			return cod_amount;
		}



		public void setCod_amount(double cod_amount) {
			this.cod_amount = cod_amount;
		}



		public double getCod_charges() {
			return cod_charges;
		}



		public void setCod_charges(double cod_charges) {
			this.cod_charges = cod_charges;
		}



		public String getCod_account_name() {
			return cod_account_name;
		}



		public void setCod_account_name(String cod_account_name) {
			this.cod_account_name = cod_account_name;
		}



		public String getCod_bank() {
			return cod_bank;
		}



		public void setCod_bank(String cod_bank) {
			this.cod_bank = cod_bank;
		}



		public String getCod_account() {
			return cod_account;
		}



		public void setCod_account(String cod_account) {
			this.cod_account = cod_account;
		}



		public String getReturn_tip_flag() {
			return return_tip_flag;
		}



		public void setReturn_tip_flag(String return_tip_flag) {
			this.return_tip_flag = return_tip_flag;
		}



		public String getReturn_tip_type() {
			return return_tip_type;
		}



		public void setReturn_tip_type(String return_tip_type) {
			this.return_tip_type = return_tip_type;
		}



		public double getReturn_tip_value() {
			return return_tip_value;
		}



		public void setReturn_tip_value(double return_tip_value) {
			this.return_tip_value = return_tip_value;
		}



		public double getUpstairs_value() {
			return upstairs_value;
		}



		public void setUpstairs_value(double upstairs_value) {
			this.upstairs_value = upstairs_value;
		}



		public String getWarehouse_flag() {
			return warehouse_flag;
		}



		public void setWarehouse_flag(String warehouse_flag) {
			this.warehouse_flag = warehouse_flag;
		}



		public double getWarehouse_value() {
			return warehouse_value;
		}



		public void setWarehouse_value(double warehouse_value) {
			this.warehouse_value = warehouse_value;
		}



		public double getOther_value() {
			return other_value;
		}



		public void setOther_value(double other_value) {
			this.other_value = other_value;
		}



		public String getPay_mode() {
			return pay_mode;
		}



		public void setPay_mode(String pay_mode) {
			this.pay_mode = pay_mode;
		}



		public double getLine_fee() {
			return line_fee;
		}



		public void setLine_fee(double line_fee) {
			this.line_fee = line_fee;
		}



		public double getReceive_fee() {
			return receive_fee;
		}



		public void setReceive_fee(double receive_fee) {
			this.receive_fee = receive_fee;
		}



		public double getSend_fee() {
			return send_fee;
		}



		public void setSend_fee(double send_fee) {
			this.send_fee = send_fee;
		}



		public double getSend_long_fee() {
			return send_long_fee;
		}



		public void setSend_long_fee(double send_long_fee) {
			this.send_long_fee = send_long_fee;
		}



		public double getLabour_fee() {
			return labour_fee;
		}



		public void setLabour_fee(double labour_fee) {
			this.labour_fee = labour_fee;
		}



		public double getVehicle_fee() {
			return vehicle_fee;
		}



		public void setVehicle_fee(double vehicle_fee) {
			this.vehicle_fee = vehicle_fee;
		}



		public double getVehicle_profit() {
			return vehicle_profit;
		}



		public void setVehicle_profit(double vehicle_profit) {
			this.vehicle_profit = vehicle_profit;
		}



		public double getVehicle_op_fee() {
			return vehicle_op_fee;
		}



		public void setVehicle_op_fee(double vehicle_op_fee) {
			this.vehicle_op_fee = vehicle_op_fee;
		}



		public double getVehicle_extra_profit() {
			return vehicle_extra_profit;
		}



		public void setVehicle_extra_profit(double vehicle_extra_profit) {
			this.vehicle_extra_profit = vehicle_extra_profit;
		}



		public double getStart_pay_amount() {
			return start_pay_amount;
		}



		public void setStart_pay_amount(double start_pay_amount) {
			this.start_pay_amount = start_pay_amount;
		}



		public double getArrive_pay_amount() {
			return arrive_pay_amount;
		}



		public void setArrive_pay_amount(double arrive_pay_amount) {
			this.arrive_pay_amount = arrive_pay_amount;
		}



		public double getOwing_amount() {
			return owing_amount;
		}



		public void setOwing_amount(double owing_amount) {
			this.owing_amount = owing_amount;
		}



		public double getMonth_amount() {
			return month_amount;
		}



		public void setMonth_amount(double month_amount) {
			this.month_amount = month_amount;
		}



		public double getTotal_fee() {
			return total_fee;
		}



		public void setTotal_fee(double total_fee) {
			this.total_fee = total_fee;
		}



		public double getPlan_revenue() {
			return plan_revenue;
		}



		public void setPlan_revenue(double plan_revenue) {
			this.plan_revenue = plan_revenue;
		}



		public String getRecv_mobile() {
			return recv_mobile;
		}



		public void setRecv_mobile(String recv_mobile) {
			this.recv_mobile = recv_mobile;
		}



		public String getBusiness_org_id() {
			return business_org_id;
		}



		public void setBusiness_org_id(String business_org_id) {
			this.business_org_id = business_org_id;
		}



		@Override
		public String toString() {
			return "TMS_job_order [ticket_id=" + ticket_id
					+ ", self_goods_flag=" + self_goods_flag
					+ ", self_bill_flag=" + self_bill_flag + ", excep_flag="
					+ excep_flag + ", ticket_status=" + ticket_status
					+ ", audit_status=" + audit_status + ", ticket_org_id="
					+ ticket_org_id + ", receive_org_id=" + receive_org_id
					+ ", send_org_id=" + send_org_id + ", send_mobile="
					+ send_mobile + ", send_cust_name=" + send_cust_name
					+ ", send_contact=" + send_contact + ", send_customer_id="
					+ send_customer_id + ", send_prov_code=" + send_prov_code
					+ ", send_city_code=" + send_city_code
					+ ", send_area_code=" + send_area_code + ", send_address="
					+ send_address + ", recv_customer_id=" + recv_customer_id
					+ ", recv_cust_name=" + recv_cust_name + ", recv_mobile="
					+ recv_mobile + ", recv_contact=" + recv_contact
					+ ", recv_prov_code=" + recv_prov_code
					+ ", recv_city_code=" + recv_city_code
					+ ", recv_area_code=" + recv_area_code + ", recv_address="
					+ recv_address + ", recv_addr_remark=" + recv_addr_remark
					+ ", pricing_mode=" + pricing_mode + ", trans_type="
					+ trans_type + ", receive_type=" + receive_type
					+ ", order_type=" + order_type + ", book_doc_id="
					+ book_doc_id + ", is_re_goods=" + is_re_goods
					+ ", old_ticket_id=" + old_ticket_id + ", goods_name="
					+ goods_name + ", goods_count=" + goods_count
					+ ", goods_weight=" + goods_weight + ", goods_lwh="
					+ goods_lwh + ", goods_cube=" + goods_cube
					+ ", valuables_flag=" + valuables_flag + ", package_type="
					+ package_type + ", trans_attention=" + trans_attention
					+ ", discount_type=" + discount_type + ", discount_scheme="
					+ discount_scheme + ", goods_value=" + goods_value
					+ ", insure_fee=" + insure_fee + ", synthesize_value="
					+ synthesize_value + ", fuel_value=" + fuel_value
					+ ", package_value=" + package_value + ", cod_flag="
					+ cod_flag + ", cod_type=" + cod_type + ", cod_amount="
					+ cod_amount + ", cod_charges=" + cod_charges
					+ ", cod_account_name=" + cod_account_name + ", cod_bank="
					+ cod_bank + ", cod_account=" + cod_account
					+ ", return_tip_flag=" + return_tip_flag
					+ ", return_tip_type=" + return_tip_type
					+ ", return_tip_value=" + return_tip_value
					+ ", upstairs_value=" + upstairs_value
					+ ", warehouse_flag=" + warehouse_flag
					+ ", warehouse_value=" + warehouse_value + ", other_value="
					+ other_value + ", pay_mode=" + pay_mode + ", line_fee="
					+ line_fee + ", receive_fee=" + receive_fee + ", send_fee="
					+ send_fee + ", send_long_fee=" + send_long_fee
					+ ", labour_fee=" + labour_fee + ", vehicle_fee="
					+ vehicle_fee + ", vehicle_profit=" + vehicle_profit
					+ ", vehicle_op_fee=" + vehicle_op_fee
					+ ", vehicle_extra_profit=" + vehicle_extra_profit
					+ ", start_pay_amount=" + start_pay_amount
					+ ", arrive_pay_amount=" + arrive_pay_amount
					+ ", owing_amount=" + owing_amount + ", month_amount="
					+ month_amount + ", total_fee=" + total_fee
					+ ", plan_revenue=" + plan_revenue + ", business_org_id="
					+ business_org_id + "]";
		}



		
		


		


}
