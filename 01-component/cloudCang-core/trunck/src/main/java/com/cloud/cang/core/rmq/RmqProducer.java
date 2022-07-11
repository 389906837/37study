package com.cloud.cang.core.rmq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.cang.core.mq.service.MessageService;
import com.cloud.cang.core.rmq.config.RmqCondition;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.mq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-14 13:30
 **/
@Component
@Conditional(RmqCondition.class)
public class RmqProducer {
    private static final Logger logger = LoggerFactory.getLogger(RmqProducer.class);
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MessageService messageService;

    public void sendMessage(RmqMessage rmqMessage) {
        try {
            // 保存MQ_MESSAGE
            this.saveMqMessage(rmqMessage);

            // 发送MQ消息
            rabbitTemplate.convertAndSend(
                    rmqMessage.getQueueName(),
                    rmqMessage,
                    new CorrelationData(rmqMessage.getId()));
            logger.info("==> 发送MQ消息:{}",rmqMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Message saveMqMessage(RmqMessage rmqMessage){
        Date addTime = DateUtil.date();
        String uuid = IdUtil.simpleUUID();
        // RmqMessage赋值
        rmqMessage.setId(uuid);
        rmqMessage.setTime(addTime.getTime());

        // 不需要保存数据库
        if(!rmqMessage.isFlagDB()){
            return null;
        }
        // 保存MQ_MESSAGE
        Message entity = new Message();
        entity.setId(uuid);
        entity.setIstatus(Message.StatusEnum.CREATE.getCode());
        entity.setSqueueName(rmqMessage.getQueueName());
        entity.setScontent(JSONUtil.toJsonStr(rmqMessage));
        entity.setSfromIp(NetUtil.getLocalhost().getHostAddress() + ":" + CoreUtils.getPortByMBean());
        entity.setTaddTime(addTime);
        if(messageService.insert(entity) > 0) {
            return entity;
        }
        return null;
    }
}
