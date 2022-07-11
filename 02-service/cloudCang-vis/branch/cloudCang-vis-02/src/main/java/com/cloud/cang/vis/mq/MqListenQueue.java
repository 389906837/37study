package com.cloud.cang.vis.mq;

import cn.hutool.core.collection.CollUtil;
import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.interfaces.RmqQueue;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.utils.DispatcherUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.vis.cr.service.RecognitionServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    RecognitionServerService recognitionServerService;

    @Override
    public List<Queue> getQueues() {
        List queues = new ArrayList<Queue>();
        // 监听图片识别的消息队列
        queues.addAll(this.getImgRecQueue());
        // 模型更新
        queues.add(this.getModelUpdateQueue());
        return  queues;
    }


    public List<Queue> getImgRecQueue(){
        String host = null;
        try {
            host = DispatcherUtils.getLocalAddress();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new ServiceException("获取本地ip地址失败!");
        }
        String port = CoreUtils.getPortByMBean();
        RecognitionServer entity = new RecognitionServer();
        entity.setIdelFlag(0);
        entity.setIauditStatus(20);
        entity.setSip(host);
        entity.setSport(CoreUtils.getPortByMBean());
        List<RecognitionServer> servers = recognitionServerService.selectByEntityWhere(entity);
        if(CollUtil.isNotEmpty(servers)){
            List list = CollUtil.newArrayList();
            String modelCode = servers.get(0).getSmodelCode();

            String queueName = QueueEnum.IMG_MODEL.getName() + modelCode;
            rmqManager.declareQueue(queueName);
            list.add(new Queue(queueName));
            logger.info("监听MQ消息队列-图片识别, queueName:{}", queueName);

            String queueName_callback = QueueEnum.IMG_MODEL_CALLBACK.getName() + modelCode;
            rmqManager.declareQueue(queueName_callback);
            list.add(new Queue(queueName_callback));
            logger.info("监听MQ消息队列-图片识别第三方异步接口, queueName:{}", queueName_callback);


            String queuename_synchronize = QueueEnum.IMG_MODEL_SYNCHRONIZE.getName() + modelCode;
            rmqManager.declareQueue(queuename_synchronize);
            list.add(new Queue(queuename_synchronize));
            logger.info("监听MQ消息队列-图片识别第三方同步接口, queueName:{}", queuename_synchronize);

            return list;
        }
        logger.error("监听MQ消息队列失败 , 表cr_recognition_server中不存在ip:{} , port:{}",host,port);
        return null;
    }

    private Queue getModelUpdateQueue(){
        String queueName = QueueEnum.MODEL_UPDATE.getName();
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 60 * 1000);//消息存活时间:60秒
        rmqManager.declareQueue(queueName, arguments);
        logger.info("监听MQ消息队列-模型更新, queueName:{}", queueName);
        return new Queue(queueName);
    }
}
