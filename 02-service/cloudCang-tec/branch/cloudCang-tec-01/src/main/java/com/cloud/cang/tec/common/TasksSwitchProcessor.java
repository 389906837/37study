/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年5月25日
 * Description:TasksSwitch.java 
 */
package com.cloud.cang.tec.common;


import com.cloud.cang.dispatcher.server.Server;
import com.cloud.cang.dispatcher.support.ServerRegisterSupportor;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * 定时任务开关
 * @author zhouhong
 * @version 1.0
 */
@Component
public class TasksSwitchProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(TasksSwitchProcessor.class);
	
	private static ServerRegisterSupportor serverRegisterSupportor = null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//dispacher 是子容器启动完成执行
		if(event.getApplicationContext().getParent() != null) {//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			logger.info("容器初始化完成。。。。。。。。。。。。。");
			serverRegisterSupportor = SpringContext.getBean(ServerRegisterSupportor.class);
		}
	}
	
	/**
	 * 任务是否可用
	 * @return
	 */
	public boolean isTaskEnable(){
		if(true)
			return true;
		if (serverRegisterSupportor == null){
			logger.warn("spring contain is not start task is stop!");
			return false;
		} 
		String allowIp = EvnUtils.getValue("allow.ip");
    	if (StringUtils.isBlank(allowIp)) {
    		logger.warn("定时任务zookeeper allow.ip is null task is stop!");
    		return false;
    	}
    	Server server = serverRegisterSupportor.getServer();
    	String address = server.getIpAddress()+":"+server.getPort();
    	if (!allowIp.equals(address)) {
    		logger.warn("定时任务zookeeper allow.ip is not the server ip task is stop!");
    		return false;
    	}
		return true;
	}

}
