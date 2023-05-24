package com.dy.directMq;

import com.yd.utils.mq.MqHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskExecInfoHandler extends MqHandler<TASK_JOB_INFO> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void handler(TASK_JOB_INFO info) {
		System.out.println("---------收到消息开始-----------");
		System.out.println(info);
		System.out.println("---------收到消息结束-----------");
	}



}
