package com.cloud.cang.vis.mq.handler;

import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_ModelUpdate;
import com.cloud.cang.vis.InitServer;
import com.cloud.cang.vis.cr.service.RecognitionServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 *
 * *****注意： 此消息队列有多台vis服务监听，若有一台消费时校验 validateBiz() 失败，不可删除此消息；
 *             此消息声明时，设置x-message-ttl=60秒，超过时间则删除此消息
 *
 * @description: 消息处理类：更新模型
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
@DependsOn("initServer")
public class ModelUpdateMessageHandler implements RmqMessageHandler<Mq_ModelUpdate> {
    private static final Logger logger = LoggerFactory.getLogger(ModelUpdateMessageHandler.class);
    @Autowired
    InitServer initServer;
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    RmqManager rmqManager;
    @Autowired
    RecognitionServerService recognitionServerService;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_ModelUpdate> rmqMessage) {
        /*if(initServer.status !=200){
            logger.warn("模型初始化未完成！");
            return HandlerEnum.ACK;
        }*/
        try {
            Mq_ModelUpdate modelUpdate = rmqMessage.getMessage();
            String oldModeCode = initServer.modelCode;
            // 校验
            if(!validateBiz(rmqMessage)){
                return HandlerEnum.NACK;
            }
            // 调用初始化模型
            initServer.loadVisMoel(true);
            // 移除旧队列的监听
            rmqManager.removeListenQueue(QueueEnum.IMG_MODEL.getName() + oldModeCode);
            // 添加新队列的监听
            rmqManager.addListenQueue(QueueEnum.IMG_MODEL.getName() + modelUpdate.getModelCode());
        } catch (Exception e) {
            logger.info("更新模型异常: {}", e);
        }
        return HandlerEnum.ACK;
    }

    @Override
    public boolean matchHandler(String queueName) {
        if (queueName.equals(QueueEnum.MODEL_UPDATE.getName())){
            return true;
        }
        return false;
    }

    private boolean validateBiz(RmqMessage<Mq_ModelUpdate> rmqMessage){
        Mq_ModelUpdate modelUpdate = rmqMessage.getMessage();
        if (!modelUpdate.getHost().equals(initServer.host) ||
                !modelUpdate.getPort().equals(initServer.port)){
            return false;
        }
        // 若只有一台vis服务消费QueueEnum.IMG_MODEL, 则需要等待此队列完全消费完，才能更新模型
        int serverNum = recognitionServerService.getRunningServerNum(initServer.modelCode);
        if(serverNum == 1 ){
            String imgModelQueue = QueueEnum.IMG_MODEL.getName() + initServer.modelCode;
            int count = rmqManager.getMessageCount(imgModelQueue);
            if(count > 0){
                logger.info("等待更新模型 , 队列 [{}] 中有 {} 条消息未被消费！", imgModelQueue, count);
                return false;
            }
        }
        return true;
    }
}
