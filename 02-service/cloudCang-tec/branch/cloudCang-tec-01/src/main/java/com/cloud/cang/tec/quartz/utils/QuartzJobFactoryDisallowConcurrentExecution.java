package com.cloud.cang.tec.quartz.utils;

import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactoryDisallowConcurrentExecution.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("非并行任务{}执行开始",new String[]{context.getFireInstanceId()});
		QuartzJobBean QuartzJobBean = (QuartzJobBean) context.getMergedJobDataMap().get("QuartzJobBean");
		TaskUtils.invokMethod(QuartzJobBean);

	}
}