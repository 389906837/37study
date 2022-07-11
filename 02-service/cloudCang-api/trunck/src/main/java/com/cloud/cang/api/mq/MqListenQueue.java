package com.cloud.cang.api.mq;

import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.interfaces.RmqQueue;
import com.cloud.cang.mq.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: 37cang-云平台
 * @description: 监听消息队列
 * @author: qzg
 * @create: 2019-11-20 10:27
 **/
@Component
public class MqListenQueue implements RmqQueue,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(MqListenQueue.class);
    ApplicationContext applicationContext;
    @Autowired
    RmqManager rmqManager;

    @PostConstruct
    private void init(){
        System.out.println(applicationContext);
    }

    @Override
    public List<Queue> getQueues() {
        List queues = new ArrayList<Queue>();
        // 监听识别结果的消息, 纯视觉
        queues.add(this.getOpenApiImgResultQu());
        // 监听识别结果的消息, 视觉+称重
        queues.add(this.getOpenApiImgResultQu_weight());
        // 识别异常消息,纯视觉
        queues.add(this.getImgError_weight());
        // 识别异常消息,视觉+称重
        queues.add(this.getImgWeightError_weight());
        // 其他队列消息
        // ...
        return  queues;
    }

    private Queue getOpenApiImgResultQu(){
        String queueName = QueueEnum.IMG_OPENAPI_RESULT.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-接收open-api识别结果,纯视觉, queueName:{}", queueName);
        return new Queue(queueName);
    }

    private Queue getOpenApiImgResultQu_weight(){
        String queueName = QueueEnum.IMG_OPENAPI_WEIGHT_RESULT.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-接收open-api识别结果,视觉+称重, queueName:{}", queueName);
        return new Queue(queueName);
    }

    private Queue getImgError_weight(){
        String queueName = QueueEnum.IMG_ERROR.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-接收open-api识别异常消息,纯视觉, queueName:{}", queueName);
        return new Queue(queueName);
    }

    private Queue getImgWeightError_weight(){
        String queueName = QueueEnum.IMG_WEIGHT_ERROR.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-接收open-api识别异常消息,视觉+称重, queueName:{}", queueName);
        return new Queue(queueName);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
    }
}
