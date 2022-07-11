package com.cloud.cang.tec.quartz.utils;

import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @Description: 计划任务执行处 无状态
 * @date 2014年4月24日 下午5:05:47
 */
public class QuartzJobFactory implements Job {
	private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJobBean QuartzJobBean = (QuartzJobBean) context.getMergedJobDataMap().get("QuartzJobBean");
		TaskUtils.invokMethod(QuartzJobBean);
	}
	
	
}