package com.cloud.cang.tec.quartz.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import com.cloud.cang.tec.quartz.vo.QuartzJobBean;

import com.cloud.cang.utils.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TaskUtils {
	private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用QuartzJobBean中定义的方法
	 * 
	 * @param quartzBean
	 */
	public static void invokMethod(QuartzJobBean quartzBean) {
		Object object = null;
		Class<?> clazz = null;
		logger.info("Quartz调用任务方法：Springid={},Method={}",new String[]{quartzBean.getSpringId(),quartzBean.getMethodName()});
		if (StringUtils.isNotBlank(quartzBean.getSpringId())) {
			object = SpringContext.getBean(quartzBean.getSpringId());
		} else if (StringUtils.isNotBlank(quartzBean.getJobClass())) {
			try {
				clazz = Class.forName(quartzBean.getJobClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (object == null) {
			logger.error("任务名称 = [" + quartzBean.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {

			if (!StringUtils.isBlank(quartzBean.getJobGroup()) && "3".equals(quartzBean.getJobGroup())) {
				method = clazz.getDeclaredMethod(quartzBean.getMethodName(),String.class);
			}else{
				method = clazz.getDeclaredMethod(quartzBean.getMethodName());
			}
		} catch (NoSuchMethodException |SecurityException e) {
			logger.error("任务名称 = [" + quartzBean.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
		}
		if (method != null) {
			try {
				if (!StringUtils.isBlank(quartzBean.getJobGroup()) && "3".equals(quartzBean.getJobGroup())) {

					method.invoke(object, quartzBean.getJobId().split("#")[1]);
				} else {
					method.invoke(object);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.error("Quartz执行任务异常{}",e);
			}
		}
		
	}


	/**     
	 * 判断cron时间表达式正确性     
	 * @param cronExpression     
	 * @return      
	 */     
	public static boolean isValidExpression(final String cronExpression){     
		CronTriggerImpl trigger = new CronTriggerImpl();        
		try {
			trigger.setCronExpression(cronExpression);
			Date date = trigger.computeFirstFireTime(null);
	        return date != null && date.after(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}     
		return false;
	}

}