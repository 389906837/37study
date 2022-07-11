package com.cloud.cang.core.rmq.listener;

import cn.hutool.core.date.DateUtil;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.mq.service.MessageService;
import com.cloud.cang.core.rmq.config.RmqCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description: 如果消息没有到exchange, 则confirm回调, ack=false
 *              如果消息到达exchange,则confirm回调,ack=true
 * @author: qzg
 * @create: 2019-11-30 10:43
 **/
@Component
@Conditional(RmqCondition.class)
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback{
    private static final Logger logger = LoggerFactory.getLogger(ConfirmCallBackListener.class);
    private static final String MQ_SEND_FAIL_ID = "mq_receive_fail_id_";
    @Autowired
    MessageService messageService;
    @Autowired
    ICached iCached;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        try {
            if(ack){
                // 消息发送成功
                if(null == iCached.get(MQ_SEND_FAIL_ID + correlationData.getId())){
                    logger.info("发送MQ消息成功, 消息id:{}",correlationData.getId());
                    com.cloud.cang.model.mq.Message mqMessage = new com.cloud.cang.model.mq.Message();
                    mqMessage.setId(correlationData.getId());
                    mqMessage.setIstatus(com.cloud.cang.model.mq.Message.StatusEnum.SEND_SUC.getCode());
                    mqMessage.setTupdateTime(DateUtil.date());

                    messageService.updateMessageStatus(mqMessage);
                }else{
                    // 删除缓存
                    iCached.remove(MQ_SEND_FAIL_ID + correlationData.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
