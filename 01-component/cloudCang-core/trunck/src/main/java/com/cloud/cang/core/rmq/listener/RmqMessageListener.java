package com.cloud.cang.core.rmq.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.mq.service.MessageService;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.lock.JedisLock;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-13 16:09
 **/
public class RmqMessageListener implements ChannelAwareMessageListener {
    public static final String CHART_SET = "UTF-8";
    private static final String MQ_RECEIVE_FAIL_ID = "mq_receive_fail_id_";
    private static final Logger logger = LoggerFactory.getLogger(RmqMessageListener.class);

    ICached iCached;

    MessageService messageService;

    ApplicationContext applicationContext;

    Map<String,Class<?>> handlerMap = new HashMap<>();

    // mq消费失败次数锁
    JedisLock failCountLock = null;

    public RmqMessageListener(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.iCached = applicationContext.getBean(ICached.class);
        this.messageService = applicationContext.getBean(MessageService.class);
        failCountLock = new JedisLock( applicationContext.getBean(JedisCluster.class),
                MQ_RECEIVE_FAIL_ID);
    }

    @Override
    public void onMessage(Message message, Channel channel){
        String body = "";
        RmqMessage rmqMessage = null;
        try {
            body = new String(message.getBody(), CHART_SET);
            logger.info("<== 接收MQ消息:{}", body);
            rmqMessage = JSONUtil.toBean(body, RmqMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("MQ非法消息, 直接移除队列:{}",body);
            this.basicACK(message,channel);
            return;
        }

        try {
            Map<String, RmqMessageHandler> beansOfType =
                    applicationContext.getBeansOfType(RmqMessageHandler.class);

            for(RmqMessageHandler handler: beansOfType.values()){
                // 1,  判断是哪个Handler处理
                if(!handler.matchHandler(rmqMessage.getQueueName())) {
                    continue;
                }
                // 2,  RmqMessage的message确定类型
                this.convertMessage(handler,rmqMessage);

                // 3,  执行RmqMessageHandler.handler()
                HandlerEnum ack = handler.handler(rmqMessage);

                // 4, ACK
                this.confirmAck(ack,message,rmqMessage,channel);
            }
        } catch (Exception e) {
            logger.error("Handler处理消息异常,{}", e);
            this.confirmAck(HandlerEnum.ACK_BIZ_FAIL,message,rmqMessage,channel);
        }
    }

    @Override
    public void onMessage(Message message) {}

    private void convertMessage(RmqMessageHandler handler, RmqMessage rmqMessage){
        try {
            Class clazz = this.dectectedParamType(handler);
            rmqMessage.setMessage(JSONUtil.toBean(JSONUtil.toJsonStr(rmqMessage.getMessage()),clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmAck(HandlerEnum ack, Message message, RmqMessage rmqMessage, Channel channel){

        try {
            // 正常消费，MQ移除队列
            if(ack == HandlerEnum.ACK) {
                this.basicACK(message,channel);
                this.updateMessageSuccess(rmqMessage);
                return;
            }

            // 更新MQ_MESSAGE消费成功, 业务处理失败
            if(ack == HandlerEnum.ACK_BIZ_FAIL) {
                this.basicACK(message,channel);
                this.updateMessageSuccess_BizFail(rmqMessage);
                return;
            }

            // 消费三次失败后, MQ移除队列, 放入死信队列
            if(ack ==  HandlerEnum.REJECT){
                try {
                    if(failCountLock.acquire()){
                        String failKey = MQ_RECEIVE_FAIL_ID + rmqMessage.getId();
                        int count = iCached.get(failKey) == null ? 0: (int)iCached.get(failKey);
                        if(count > 3){
                            logger.error("MQ消息已达消费上限次数，放入死信队列, {}",JSONUtil.toJsonStr(rmqMessage));
                            //
                            this.basicReject(message,channel);
                            // 删除缓存
                            iCached.remove(failKey);
                            // 更新MQ_MESSAGE消费失败
                            this.updateMessageFail_dead(rmqMessage);
                            //发送钉钉预警
                            DingTalkUtils.sendText("消费队列次数已达上限:"+rmqMessage.getQueueName());
                        }else {
                            count = count + 1;
                            iCached.put(failKey, count);
                            this.basicNACK(message,channel);
                        }
                    }
                } catch (Exception e) {
                    logger.error("",e);
                } finally {
                    failCountLock.release();
                }
                return;
            }

            // MQ重回队列
            if(ack ==  HandlerEnum.NACK){
                this.basicNACK(message,channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常消费掉后通知mq服务器移除此条mq
     */
    private void basicACK(Message message,Channel channel){
        try{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch(IOException e){
            logger.error("通知服务器移除MQ时异常，异常信息："+e);
        }
    }

    /**
     * 处理异常，mq重回队列
     */
    private void basicNACK(Message message,Channel channel){
        try{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }catch(IOException e){
            logger.error("MQ重新进入服务器时出现异常，异常信息："+e);
        }
    }

    /**
     * 处理异常，放入死信队列
     */
    private void basicReject(Message message,Channel channel){
        try{
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }catch(IOException e){
            logger.error("MQ重新进入服务器时出现异常，异常信息："+e);
        }
    }


    /**
     * 确定RmqMessageHandler<?> 泛型类型
     * @param handler
     * @return
     */
    private Class<?> dectectedParamType(RmqMessageHandler handler){
        String className = handler.getClass().getName();
        if(handlerMap.containsKey(className)){
            return handlerMap.get(className);
        }
        Type[] types = handler.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Class<?> clazz = (Class<?>) parameterized.getActualTypeArguments()[0];
        handlerMap.put(className,clazz);
        return clazz;
    }


    private void updateMessageSuccess(final RmqMessage rmqMessage){
        // 不需要更新
        if(!rmqMessage.isFlagDB()){
            return;
        }
        com.cloud.cang.model.mq.Message message = new com.cloud.cang.model.mq.Message();
        message.setIstatus(com.cloud.cang.model.mq.Message.StatusEnum.CONSUMER_SUC.getCode());
        message.setStoIp(NetUtil.getLocalhost().getHostAddress() + ":" + CoreUtils.getPortByMBean());
        message.setId(rmqMessage.getId());
        message.setTupdateTime(DateUtil.date());
        messageService.updateBySelective(message);
    }

    private void updateMessageSuccess_BizFail(final RmqMessage rmqMessage){
        // 不需要更新
        if(!rmqMessage.isFlagDB()){
            return;
        }
        com.cloud.cang.model.mq.Message message = new com.cloud.cang.model.mq.Message();
        message.setIstatus(com.cloud.cang.model.mq.Message.StatusEnum.CONSUMER_SUC_BIZ_FAIL.getCode());
        message.setStoIp(NetUtil.getLocalhost().getHostAddress() + ":" + CoreUtils.getPortByMBean());
        message.setId(rmqMessage.getId());
        message.setTupdateTime(DateUtil.date());
        messageService.updateBySelective(message);
    }

    private void updateMessageFail_dead(final RmqMessage rmqMessage){
        // 不需要更新
        if(!rmqMessage.isFlagDB()){
            return;
        }
        com.cloud.cang.model.mq.Message message = new com.cloud.cang.model.mq.Message();
        message.setIstatus(com.cloud.cang.model.mq.Message.StatusEnum.CONSUMER_FAIL_DEAD.getCode());
        message.setStoIp(NetUtil.getLocalhost().getHostAddress() + ":" + CoreUtils.getPortByMBean());
        message.setId(rmqMessage.getId());
        message.setTupdateTime(DateUtil.date());
        message.setSremark(com.cloud.cang.model.mq.Message.StatusEnum.CONSUMER_FAIL_DEAD.getDesc());
        messageService.updateBySelective(message);
    }
}

