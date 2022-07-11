package com.cloud.cang.tec.quartz.service.impl;

import java.util.List;

import com.cloud.cang.tec.quartz.utils.TaskService;
import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadTask {     
     
	private final Logger logger = LoggerFactory.getLogger(this.getClass());     
	      
	@Autowired     
	private TaskService taskService;
	     
	public void initTask() throws SchedulerException {		     
		// 可执行的任务列表        
		List<QuartzJobBean> taskList = taskService.getTaskList();
		logger.info("初始化加载定时任务......");     
		for (QuartzJobBean job : taskList) {     
			taskService.addJob(job);     
		}     
	}	     
}