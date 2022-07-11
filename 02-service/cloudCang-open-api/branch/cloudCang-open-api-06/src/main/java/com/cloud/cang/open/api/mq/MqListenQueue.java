package com.cloud.cang.open.api.mq;

import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.interfaces.RmqQueue;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.open.api.cr.service.ServerModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 37cang-云平台
 * @description: 监听消息队列
 * @author: qzg
 * @create: 2019-11-20 10:27
 **/
@Component
public class MqListenQueue implements RmqQueue {
    private static final Logger logger = LoggerFactory.getLogger(MqListenQueue.class);
    @Autowired
    RmqManager rmqManager;
    @Autowired
    ServerModelService serverModelService;


    @Override
    public List<Queue> getQueues() {
        List queues = new ArrayList<Queue>();
        // MQ识别结果队列消息
        queues.add(this.getImgResultQueue());
        // MQ识别结果队列消息 第三方异步方法
        queues.add(this.getImgResultQueue_callback());
        // YOLOv4 MQ识别结果队列消息 第三方异步方法
        queues.add(this.getImgYoloV4ResultQueue_callback());
        // MQ识别结果队列消息 第三方同步方法
        queues.add(this.getImgResultQueue_synchronize());
        // MQ识别结果队列消息 中林清风接口
        queues.add(this.getImgResultQueue_zlqf());
        return  queues;
    }

    public Queue getImgResultQueue(){
        String queueName = QueueEnum.IMG_RESULT.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-图片识别结果, queueName:{}", queueName);
        return new Queue(queueName);
    }

    public Queue getImgResultQueue_callback(){
        String queueName = QueueEnum.IMG_RESULT_CALLBACK.getName();
        rmqManager.declareQueue(queueName);
        logger.info("异步方法监听MQ消息队列-图片识别结果, queueName:{}", queueName);
        return new Queue(queueName);
    }

    public Queue getImgYoloV4ResultQueue_callback(){
        String queueName = QueueEnum.IMG_RESULT_YOLOV4_CALLBACK.getName();
        rmqManager.declareQueue(queueName);
        logger.info("异步方法监听MQ消息队列-YOLOV4图片识别结果, queueName:{}", queueName);
        return new Queue(queueName);
    }

    public Queue getImgResultQueue_synchronize(){
        String queueName = QueueEnum.IMG_RESULT_SYNCHRONIZE.getName();
        rmqManager.declareQueue(queueName);
        logger.info("同步方法监听MQ消息队列-图片识别结果, queueName:{}", queueName);
        return new Queue(queueName);
    }

    public Queue getImgResultQueue_zlqf(){
        String queueName = QueueEnum.IMG_RESULT_DARKNET.getName();
        rmqManager.declareQueue(queueName);
        logger.info("监听MQ消息队列-第三方识别结果, queueName:{}", queueName);
        return new Queue(queueName);
    }
}
