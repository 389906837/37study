package com.cloud.cang.core.rmq;

import com.cloud.cang.core.rmq.config.RabbitmqConfig;
import com.cloud.cang.core.rmq.config.RmqCondition;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 37cang-云平台
 * @description: RabbitMq 消息管理
 * @author: qzg
 * @create: 2019-11-20 12:41
 **/
@Component
@Conditional(RmqCondition.class)
public class RmqManager{
    private SimpleMessageListenerContainer simpleMessageListenerContainer;
    @Autowired
    private RabbitAdmin rabbitAdmin;

    Map<String, Object> arguments = new HashMap<>();

    @PostConstruct
    public void init(){
        // 设置死信队列
        arguments.put("x-dead-letter-exchange", RabbitmqConfig.DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", RabbitmqConfig.DEAD_QUEUE);
    }

    /**
     * 声明一个消息队列：持久化，非独占，不自动删除
     * The queue is durable, non-exclusive and non auto-delete.
     */
    public synchronized String declareQueue(String name){
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(name);
        if(queueInfo == null){
            Queue queue = new Queue(name,true, false, false,arguments);
            return rabbitAdmin.declareQueue(queue);
        }
        return name;
    }

    /**
     * 声明一个消息队列：持久化，非独占，不自动删除
     * The queue is durable, non-exclusive and non auto-delete.
     */
    public synchronized String declareQueue(String name,Map<String, Object> arguments){
        if(arguments!= null){
            this.arguments.putAll(arguments);
        }
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(name);
        if(queueInfo == null){
            Queue queue = new Queue(name,true, false, false,this.arguments);
            return rabbitAdmin.declareQueue(queue);
        }
        return name;
    }


    /**
     * Delete a queue
     */
    public synchronized boolean deleteQueue(String name){
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(name);
        if(queueInfo != null){
            return rabbitAdmin.deleteQueue(name);
        }
        return true;
    }

    /**
     * 清空某对列的消息，返回清空的数量
     */
    public synchronized int purgeQueue(String name){
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(name);
        if(queueInfo != null){
            return rabbitAdmin.purgeQueue(name);
        }
        return 0;
    }

    /**
     * 查询消息队列信息
     */
    public Object getQueueInfo(String name){
        return null;
    }

    /**
     * 查询某队列消息数量
     */
    public int getMessageCount(String name){
        return rabbitAdmin.getQueueInfo(name).getMessageCount();
    }

    /**
     * 查询所有消息对列
     */
    public List<Queue> getQueues(){
        return null;
    }

    /**
     * 移除对某消息队列的监听
     * @param qname
     */
    public synchronized void removeListenQueue(String ...qname){
        simpleMessageListenerContainer.removeQueueNames(qname);
    }

    /**
     * 添加对某消息队列的监听
     * @param qname
     */
    public synchronized void addListenQueue(String ...qname){
        for (String name : qname) {
            this.declareQueue(name);
        }
        simpleMessageListenerContainer.addQueueNames(qname);
    }

    public void setSimpleMessageListenerContainer(SimpleMessageListenerContainer simpleMessageListenerContainer) {
        this.simpleMessageListenerContainer = simpleMessageListenerContainer;
    }
}
