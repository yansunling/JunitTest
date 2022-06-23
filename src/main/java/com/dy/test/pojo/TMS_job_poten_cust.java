package com.dy.test.pojo;

import com.yd.utils.mq.ChannelMessage;


public class TMS_job_poten_cust extends  ChannelMessage{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 7876108529490681988L;

	/**
     * 发货客户名称
     */
    private String send_cust_name = "";
    
    /**
     * 发货手机
     */
    private String send_mobile = "";
    /**
     * 发货固定电话
     */
    private String send_contact = "";
    
    /**
     * 发货省编码
     */
    private int send_prov_code = 0;


    /**
     * 发货市编码
     */
    private int send_city_code = 0;


    /**
     * 发货区编码
     */
    private int send_area_code = 0;
    /**
     * 发货地址
     */
    private String send_address = "";
    
    
    /**
     * 收货客户名称
     */
    private String recv_cust_name = "";


    /**
     * 收货省编码
     */
    private int recv_prov_code = 0;


    /**
     * 收货市编码
     */
    private int recv_city_code = 0;


    /**
     * 收货区编码
     */
    private int recv_area_code = 0;


    /**
     * 收货地址
     */
    private String recv_address = "";


    /**
     * 收货手机
     */
    private String recv_mobile = "";


    /**
     * 收货固定电话
     */
    private String recv_contact = "";
    /**
     * 约车部门
     */
    private String book_org_id;
    
    /**
     * 操作人名
     */
    private String user_name;


	public String getSend_cust_name() {
		return send_cust_name;
	}


	public void setSend_cust_name(String send_cust_name) {
		this.send_cust_name = send_cust_name;
	}


	public String getSend_mobile() {
		return send_mobile;
	}


	public void setSend_mobile(String send_mobile) {
		this.send_mobile = send_mobile;
	}


	public String getSend_contact() {
		return send_contact;
	}


	public void setSend_contact(String send_contact) {
		this.send_contact = send_contact;
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


	public String getRecv_mobile() {
		return recv_mobile;
	}


	public void setRecv_mobile(String recv_mobile) {
		this.recv_mobile = recv_mobile;
	}


	public String getRecv_contact() {
		return recv_contact;
	}


	public void setRecv_contact(String recv_contact) {
		this.recv_contact = recv_contact;
	}


	public String getBook_org_id() {
		return book_org_id;
	}


	public void setBook_org_id(String book_org_id) {
		this.book_org_id = book_org_id;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	@Override
	public String toString() {
		return "TMS_job_poten_cust [send_cust_name=" + send_cust_name
				+ ", send_mobile=" + send_mobile + ", send_contact="
				+ send_contact + ", send_prov_code=" + send_prov_code
				+ ", send_city_code=" + send_city_code + ", send_area_code="
				+ send_area_code + ", send_address=" + send_address
				+ ", recv_cust_name=" + recv_cust_name + ", recv_prov_code="
				+ recv_prov_code + ", recv_city_code=" + recv_city_code
				+ ", recv_area_code=" + recv_area_code + ", recv_address="
				+ recv_address + ", recv_mobile=" + recv_mobile
				+ ", recv_contact=" + recv_contact + ", book_org_id="
				+ book_org_id + ", user_name=" + user_name + "]";
	}


	
	




	


	
    
    
    
    
    
}
