package com.module.mq.fanout;


import org.springframework.stereotype.Component;

@Component
public class DruidCustomer extends FanoutCustomerUtil{

	@Override
	public void proccess(String message) {
		System.out.println(message);
	}

}
