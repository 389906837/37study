package com.cloud.cang.message.xx.thread;

import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.common.EnviornConfig;
import com.cloud.cang.message.xx.domain.SelectAvailSenderTaskDto;
import com.cloud.cang.message.xx.service.SmsSendProxyService;
import com.cloud.cang.message.xx.service.MsgTaskExtService;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.lock.JedisLock;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时发送任务
 * @author zhouhong
 * @version 1.0
 */
@Component
public class SmsThreadTask {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsThreadTask.class);
    
    @Autowired
    EnviornConfig enviornConfig;
    
    @Autowired
    MsgTaskExtService msgTaskExtService;
    
    @Autowired
	SmsSendProxyService smsSendProxyService;
    
    @Autowired
    JedisCluster jedisCluster;
    
    /**
     * 每次查询数据库大小
     */
    private final int SIZE = 30;
    
    
    private boolean isSmsRun  = true;
    
    private boolean isEmailRun = true;
    
    JedisLock mobileLock = null;
    JedisLock emailLock = null; 
    
    static int EMAIL_DEFAULT_EXPIRY_TIME =new Long(TimeUnit.MINUTES.toMillis(5)).intValue();
    
    @PostConstruct
    public void init() {
	LOGGER.info("任务定时发送启动......");
	if (enviornConfig.isProduct()) {
		mobileLock = new JedisLock(jedisCluster, "lock_sms_send");
		emailLock = new JedisLock(jedisCluster, "lock_email_send",JedisLock.DEFAULT_ACQUIRE_TIMEOUT_MILLIS,EMAIL_DEFAULT_EXPIRY_TIME); 
	    smsSchedule();
	    emailSchedule();
	}
	
    }
    
    /**
     * SMS 任务调度
     */
	private void smsSchedule() {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("短信发送定时任务启动......");
				SelectAvailSenderTaskDto dto = null;
				do {
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
					}
					try {
						if (mobileLock.acquire()) {
							dto = new SelectAvailSenderTaskDto();
							dto.setEnd(SIZE);
							dto.setMsgType(Constrants.MOBILE_MESSAGE);
							dto.setStartTime(DateUtils.getCurrentDateTime());
							List<MsgTask> msgTaskList = msgTaskExtService.selectAvailSenderTask(dto);
							LOGGER.debug("获取到reids lock,异步发送短信开始,size:"+ (msgTaskList == null ? 0 : msgTaskList.size()));
							if (msgTaskList == null || msgTaskList.isEmpty()) {
								continue;
							}
							for (int i = 0; i < msgTaskList.size(); i++) {
								MsgTask task = msgTaskList.get(i);
								if (task.getIsenderType() == 1) {
									if (task.getTbeginSendDatetime().getTime() < new Date().getTime()) {
										smsSendProxyService.sendMobileMessageWithSynchro(task);
									}
								} else if (task.getIsenderType() == 2) {
									String[] mobileArray = task.getSmsgRecipient().split(",");
									if (mobileArray != null && mobileArray.length > 0) {
										List<String> mobileList = Arrays.asList(mobileArray);
										if (task.getTbeginSendDatetime().getTime() < new Date().getTime()) {
											smsSendProxyService.batchSendMobileMessageWithSynchro(task, mobileList);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						LOGGER.error("", e);
					} finally {
						mobileLock.release();
					}

				} while (isSmsRun);

			}
		});

	}
    
    private void emailSchedule() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
        	LOGGER.info("邮件发送定时任务启动......");
                SelectAvailSenderTaskDto dto = null;
				do {
					try {
						TimeUnit.SECONDS.sleep(15);
					} catch (InterruptedException e) {
					}
					try {
						if (emailLock.acquire()) {
							dto = new SelectAvailSenderTaskDto();
							dto.setEnd(SIZE);
							dto.setMsgType(Constrants.EMAIL_MESSAGE);
							dto.setStartTime(DateUtils.getCurrentDateTime());
							List<MsgTask> msgTaskList = msgTaskExtService.selectAvailSenderTask(dto);
							LOGGER.info("异步发送邮件开始,size:" + (msgTaskList == null ? 0 : msgTaskList.size()));
							if (msgTaskList == null || msgTaskList.isEmpty()) {
								continue;
							}
							for (int i = 0; i < msgTaskList.size(); i++) {
								final MsgTask task = msgTaskList.get(i);
								if (task.getTbeginSendDatetime().getTime() < new Date().getTime()) {
									smsSendProxyService.sendEmailMessageWithSynchro(task);
								}
							}
						}
					} catch (Exception e) {
						LOGGER.error("{}", e);
					} finally {
						emailLock.release();
					}
				} while (isEmailRun);

            }
        });
	
    }
    
    @PreDestroy
    public void destroy() {
	isSmsRun = false;
	isEmailRun = false;
    }
    
    

}
