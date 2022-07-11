package com.cloud.cang.core.rmq.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.json.JSONUtil;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.mq.service.MessageService;
import com.cloud.cang.core.rmq.config.RmqCondition;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.DingTalkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description: exchange到queue成功, 则不回调return
 *               exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
 * @author: qzg
 * @create: 2019-11-30 10:44
 **/
@Component
@Conditional(RmqCondition.class)
public class ReturnCallBackListener implements RabbitTemplate.ReturnCallback{
    private static final Logger logger = LoggerFactory.getLogger(ReturnCallBackListener.class);
    public static final String CHART_SET = "UTF-8";
    private static final String MQ_SEND_FAIL_ID = "mq_receive_fail_id_";
    @Autowired
    MessageService messageService;
    @Autowired
    ICached iCached;

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {

        try {
            String body = new String(message.getBody(), CHART_SET);
            RmqMessage rmqMessage = JSONUtil.toBean(body, RmqMessage.class);
            String errorMsg = StrFormatter.format("发送MQ消息失败, id:{} , routingKey:{} , replyCode:{} , replyText:{}",
                    rmqMessage.getId(),routingKey,replyCode,replyText);
            logger.error(errorMsg);
            // 发送失败钉钉预警
            DingTalkUtils.sendText(errorMsg);

            // 发送失败, 放入缓存
            iCached.put(MQ_SEND_FAIL_ID + rmqMessage.getId(),rmqMessage.getId());

            // 发送失败,更新MQ_MESSAGE
            com.cloud.cang.model.mq.Message mqMessage = new com.cloud.cang.model.mq.Message();
            mqMessage.setId(rmqMessage.getId());
            mqMessage.setIstatus(com.cloud.cang.model.mq.Message.StatusEnum.SEND_FAIL.getCode());
            mqMessage.setTupdateTime(DateUtil.date());
            mqMessage.setSremark(replyText);
            messageService.updateBySelective(mqMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
