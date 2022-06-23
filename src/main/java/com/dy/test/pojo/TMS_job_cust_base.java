package com.dy.test.pojo;

import com.yd.utils.mq.ChannelMessage;

import java.sql.Timestamp;

public class TMS_job_cust_base extends  ChannelMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341L;


	/**
     * 客户编码
     */
    private String customer_id = "";
    /**
     * 客户联系人
     */
    
    private String cust_contact="";
    
    /**
     * 客户名称
     */
    private String customer_name = "";
    /**
     * 手机
     */
    private String cust_mobile = "";
    /**
     * 客户状态
     */
    private String cust_status = "";
    
    /**
     * 结算方式
     */
    private String monthly_pay_flag = "";
    /**
     * 所属营业部
     */
    private String resp_org_id = "";
    
    /**
     * 开发人
     */
    private String op_user = "";
    /**
     * 开发人工号
     */
    private String op_user_id = "";


    /**
     * 创建人
     */
    private String creator = "";
    
    /**
     * 创建时间
     */
    private Timestamp create_time;
    /**
     * 停用时间
     */
    private Timestamp stop_time;
    /**
     * 首次发货时间
     */
    private Timestamp first_delivery_time;
    
    
    /**
     * 固定电话
     */
    private String cust_phone = "";
    
    
    
    /**
     * 行业类型
     */
    private String industry_type = "";
    
    
    /**
     * 主营业务
     */
    private String main_business = "";
    
    /**
     * 已开单金额
     */
    private double billing_amount = 0;
    
    /**
     * 已结算金额
     */
    private double settle_amount = 0;
    
    /**
     * 信誉额度
     */
    private double owning_amount = 0;
    
    /**
     * 省
     */
    private int prov_code = 0;


    /**
     * 市
     */
    private int city_code = 0;


    /**
     * 区
     */
    private int area_code = 0;


    /**
     * 地址
     */
    private String cust_address = "";
    
    /**
     * 客户类别
     */
    private String customer_type = "";
    
    /**
     * 客户来源
     */
    private String cust_source = "";
    
    /**
     * 联系人编号
     */
    private String contacts_serial_no = "";
    
    /**
     * 联系人
     */
    private String contacts = "";


    /**
     * 公司职位
     */
    private String position = "";


    /**
     * 联系手机
     */
    private String mobile = "";


    /**
     * 联系电话
     */
    private String phone = "";
    
    /**
     * 传真
     */
    private String fax = "";
    
    /**
     * 邮箱
     */
    private String email = "";
    /**
     * 详细地址
     */
    private String contacts_address = "";


    /**
     * 是否默认联系人
     */
    private String contacts_default_flag = "";
    
    /**
     * 开票流水号
     */
    private String billing_serial_no = "";
    /**
     * 开票公司
     */
    private String company = "";
    

    /**
     * 发票类型
     */
    private String billing_type = "";


    /**
     * 客户税号
     */
    private String duty_num = "";


    /**
     * 开户行
     */
    private String bank = "";


    /**
     * 开票账户
     */
    private String account = "";


    /**
     * 收票人
     */
    private String ticket_collector = "";
    /**
     * 详细地址
     */
    private String billing_address = "";


    /**
     * 联系方式
     */
    private String telephone = "";


    /**
     * 是否默认开票信息
     */
    private String billing_default_flag = "";
    
    
    
    /**
     * 回访流水号
     */
    private String visit_serial_no = "";
    
    /**
     * 回访人名称
     */
    private String visiter_id = "";


    /**
     * 回访方式
     */
    private String visit_type = "";


    /**
     * 回访情况描述
     */
    private String visit_descr = "";
    /**
     * 联系人
     */
    private String visit_contacts = "";
    /**
     * 联系电话
     */
    private String visit_contacts_phone = "";
    
    /**
     * 备注
     */
    private String visit_remark = "";
    
	
    /**
     * 开户行
     */
    private String account_bank = "";

    /**
     * 开户人
     */
    private String account_holder = "";
    /**
     * 收款账号
     */
    private String account_num = "";
    
    /**
     * 行号
     */
    private String account_serial_no = "";
    /**
     * 是否默认账户
     */
    private String account_default_flag = "";
    /**
     * 回访时间
     */
    private Timestamp visit_time;
    
    /**
     * 是否落地配
     */
    private String landing_flag;
    
    
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCust_mobile() {
		return cust_mobile;
	}
	public void setCust_mobile(String cust_mobile) {
		this.cust_mobile = cust_mobile;
	}
	public String getCust_status() {
		return cust_status;
	}
	public void setCust_status(String cust_status) {
		this.cust_status = cust_status;
	}
	public String getMonthly_pay_flag() {
		return monthly_pay_flag;
	}
	public void setMonthly_pay_flag(String monthly_pay_flag) {
		this.monthly_pay_flag = monthly_pay_flag;
	}
	public String getOp_user() {
		return op_user;
	}
	public void setOp_user(String op_user) {
		this.op_user = op_user;
	}
	public String getOp_user_id() {
		return op_user_id;
	}
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getFirst_delivery_time() {
		return first_delivery_time;
	}
	public void setFirst_delivery_time(Timestamp first_delivery_time) {
		this.first_delivery_time = first_delivery_time;
	}
	public String getCust_phone() {
		return cust_phone;
	}
	public void setCust_phone(String cust_phone) {
		this.cust_phone = cust_phone;
	}
	public String getIndustry_type() {
		return industry_type;
	}
	public void setIndustry_type(String industry_type) {
		this.industry_type = industry_type;
	}
	public String getMain_business() {
		return main_business;
	}
	public void setMain_business(String main_business) {
		this.main_business = main_business;
	}
	public double getBilling_amount() {
		return billing_amount;
	}
	public void setBilling_amount(double billing_amount) {
		this.billing_amount = billing_amount;
	}
	public double getSettle_amount() {
		return settle_amount;
	}
	public void setSettle_amount(double settle_amount) {
		this.settle_amount = settle_amount;
	}
	public double getOwning_amount() {
		return owning_amount;
	}
	public void setOwning_amount(double owning_amount) {
		this.owning_amount = owning_amount;
	}
	public int getProv_code() {
		return prov_code;
	}
	public void setProv_code(int prov_code) {
		this.prov_code = prov_code;
	}
	public int getCity_code() {
		return city_code;
	}
	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}
	public int getArea_code() {
		return area_code;
	}
	public void setArea_code(int area_code) {
		this.area_code = area_code;
	}
	public String getCust_address() {
		return cust_address;
	}
	public void setCust_address(String cust_address) {
		this.cust_address = cust_address;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public String getCust_source() {
		return cust_source;
	}
	public void setCust_source(String cust_source) {
		this.cust_source = cust_source;
	}
	
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContacts_address() {
		return contacts_address;
	}
	public void setContacts_address(String contacts_address) {
		this.contacts_address = contacts_address;
	}
	public String getContacts_default_flag() {
		return contacts_default_flag;
	}
	public void setContacts_default_flag(String contacts_default_flag) {
		this.contacts_default_flag = contacts_default_flag;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBilling_type() {
		return billing_type;
	}
	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
	}
	public String getDuty_num() {
		return duty_num;
	}
	public void setDuty_num(String duty_num) {
		this.duty_num = duty_num;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTicket_collector() {
		return ticket_collector;
	}
	public void setTicket_collector(String ticket_collector) {
		this.ticket_collector = ticket_collector;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getVisit_serial_no() {
		return visit_serial_no;
	}
	public void setVisit_serial_no(String visit_serial_no) {
		this.visit_serial_no = visit_serial_no;
	}
	public String getVisiter_id() {
		return visiter_id;
	}
	public void setVisiter_id(String visiter_id) {
		this.visiter_id = visiter_id;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public String getVisit_descr() {
		return visit_descr;
	}
	public void setVisit_descr(String visit_descr) {
		this.visit_descr = visit_descr;
	}
	public String getVisit_contacts() {
		return visit_contacts;
	}
	public void setVisit_contacts(String visit_contacts) {
		this.visit_contacts = visit_contacts;
	}
	public String getVisit_contacts_phone() {
		return visit_contacts_phone;
	}
	public void setVisit_contacts_phone(String visit_contacts_phone) {
		this.visit_contacts_phone = visit_contacts_phone;
	}
	
	public String getAccount_bank() {
		return account_bank;
	}
	public void setAccount_bank(String account_bank) {
		this.account_bank = account_bank;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getAccount_serial_no() {
		return account_serial_no;
	}
	public void setAccount_serial_no(String account_serial_no) {
		this.account_serial_no = account_serial_no;
	}
	public String getAccount_default_flag() {
		return account_default_flag;
	}
	public void setAccount_default_flag(String account_default_flag) {
		this.account_default_flag = account_default_flag;
	}
	public String getContacts_serial_no() {
		return contacts_serial_no;
	}
	public void setContacts_serial_no(String contacts_serial_no) {
		this.contacts_serial_no = contacts_serial_no;
	}
	public String getBilling_serial_no() {
		return billing_serial_no;
	}
	public void setBilling_serial_no(String billing_serial_no) {
		this.billing_serial_no = billing_serial_no;
	}
	public String getBilling_default_flag() {
		return billing_default_flag;
	}
	public void setBilling_default_flag(String billing_default_flag) {
		this.billing_default_flag = billing_default_flag;
	}
	
	
	public String getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}
	public String getVisit_remark() {
		return visit_remark;
	}
	public void setVisit_remark(String visit_remark) {
		this.visit_remark = visit_remark;
	}
	public Timestamp getStop_time() {
		return stop_time;
	}
	public void setStop_time(Timestamp stop_time) {
		this.stop_time = stop_time;
	}
	public String getResp_org_id() {
		return resp_org_id;
	}
	public void setResp_org_id(String resp_org_id) {
		this.resp_org_id = resp_org_id;
	}
	public Timestamp getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(Timestamp visit_time) {
		this.visit_time = visit_time;
	}
	public String getCust_contact() {
		return cust_contact;
	}
	public void setCust_contact(String cust_contact) {
		this.cust_contact = cust_contact;
	}
	public String getAccount_holder() {
		return account_holder;
	}
	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}
	public String getLanding_flag() {
		return landing_flag;
	}
	public void setLanding_flag(String landing_flag) {
		this.landing_flag = landing_flag;
	}
	@Override
	public String toString() {
		return "TMS_job_cust_base [customer_id=" + customer_id
				+ ", cust_contact=" + cust_contact + ", customer_name="
				+ customer_name + ", cust_mobile=" + cust_mobile
				+ ", cust_status=" + cust_status + ", monthly_pay_flag="
				+ monthly_pay_flag + ", resp_org_id=" + resp_org_id
				+ ", op_user=" + op_user + ", op_user_id=" + op_user_id
				+ ", creator=" + creator + ", create_time=" + create_time
				+ ", stop_time=" + stop_time + ", first_delivery_time="
				+ first_delivery_time + ", cust_phone=" + cust_phone
				+ ", industry_type=" + industry_type + ", main_business="
				+ main_business + ", billing_amount=" + billing_amount
				+ ", settle_amount=" + settle_amount + ", owning_amount="
				+ owning_amount + ", prov_code=" + prov_code + ", city_code="
				+ city_code + ", area_code=" + area_code + ", cust_address="
				+ cust_address + ", customer_type=" + customer_type
				+ ", cust_source=" + cust_source + ", contacts_serial_no="
				+ contacts_serial_no + ", contacts=" + contacts + ", position="
				+ position + ", mobile=" + mobile + ", phone=" + phone
				+ ", fax=" + fax + ", email=" + email + ", contacts_address="
				+ contacts_address + ", contacts_default_flag="
				+ contacts_default_flag + ", billing_serial_no="
				+ billing_serial_no + ", company=" + company
				+ ", billing_type=" + billing_type + ", duty_num=" + duty_num
				+ ", bank=" + bank + ", account=" + account
				+ ", ticket_collector=" + ticket_collector
				+ ", billing_address=" + billing_address + ", telephone="
				+ telephone + ", billing_default_flag=" + billing_default_flag
				+ ", visit_serial_no=" + visit_serial_no + ", visiter_id="
				+ visiter_id + ", visit_type=" + visit_type + ", visit_descr="
				+ visit_descr + ", visit_contacts=" + visit_contacts
				+ ", visit_contacts_phone=" + visit_contacts_phone
				+ ", visit_remark=" + visit_remark + ", account_bank="
				+ account_bank + ", account_holder=" + account_holder
				+ ", account_num=" + account_num + ", account_serial_no="
				+ account_serial_no + ", account_default_flag="
				+ account_default_flag + ", visit_time=" + visit_time
				+ ", landing_flag=" + landing_flag + "]";
	}
	
	
	
}
