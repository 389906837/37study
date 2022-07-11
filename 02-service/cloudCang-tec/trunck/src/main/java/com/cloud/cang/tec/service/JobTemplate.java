/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月12日
 * Description:JobTemplate.java 
 */
package com.cloud.cang.tec.service;

import java.util.Map;

import com.cloud.cang.tec.common.TasksSwitchProcessor;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.tec.tec.service.ScheduleLogService;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;



/**
 * 任务模板
 * @author zhouhong
 * @version 1.0
 */
@Service
public class JobTemplate {
	
	private static final Logger logger = LoggerFactory.getLogger(JobTemplate.class);
	
	@Autowired
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	private TasksSwitchProcessor tasksSwitchProcessor;
	
    /**
     * 
     * @param jobCallBack 回调函数
     * @param title job名称
     * @param isScheduleLog 是否加入任务调度日志
     */
    public void excuteJob(JobCallBack jobCallBack, String title, boolean isScheduleLog){

    	if (!tasksSwitchProcessor.isTaskEnable()) return;
        boolean isSucc = true;
        String msg = "";
        try {
            msg = jobCallBack.doInJob();
        } catch (Exception e) {
            isSucc = false;
            msg = e.getMessage();
            logger.error(title+"job出错：",e);
        }
        
        if(isScheduleLog){
            logger.info(title+"记录任务调度日志入库");
            ScheduleLog scheduleLog = new ScheduleLog(); //任务调度日志
            scheduleLog.setTexecuteTime(DateUtils.getCurrentDateTime());//任务执行时间
            scheduleLog.setStaskName(title);
            scheduleLog.setIresult(isSucc ? 1 : 0);//执行结果 1=成功  0=失败
            scheduleLog.setSremark(msg);
            scheduleLogService.insert(scheduleLog);
        }
        
    }
    
    /**
     * 发送告警信息
     * @param templateMainCode 主模板编号
     * @param purviewCode 权限码
     * @param contentParam 参数
     */
    public void sendWarnMsg(String templateMainCode,String purviewCode, Map<String,Object> contentParam) {

        InnerMessageDto innerMessageDto = new InnerMessageDto();
        // 模板编号
        innerMessageDto.setTemplateCode(templateMainCode);
        // 内容
        innerMessageDto.setContentParam(contentParam);
        // 权限编码
        innerMessageDto.setPurviewCode(purviewCode);
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
            invoke.setRequestObj(innerMessageDto); // post 参数
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
            ResponseVo<String> responseVo1 = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error("警告短信发送失败", e);
        }
    }

}
