package com.cloud.cang.api.mq.handler;

import com.cloud.cang.api.netty.service.ImgCloudRecognition;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_Exception_ImgResul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别结果消息 云端纯视觉
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
public class ImgExceptionResultMessageHandler implements RmqMessageHandler<Mq_Cloud_Exception_ImgResul> {
    private static final Logger logger = LoggerFactory.getLogger(ImgExceptionResultMessageHandler.class);
   @Autowired
    private ImgCloudRecognition imgCloudRecognition;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_Cloud_Exception_ImgResul> rmqMessage) {
        logger.info("{}", rmqMessage);
        Mq_Cloud_Exception_ImgResul message = rmqMessage.getMessage();
          ResponseVo<String> responseVo=  imgCloudRecognition.cloudException(message.getDeviceId(),message.getKey(),message.getUserId(),message.getOpenDoorType(),message.getOpenDoorType(),message.getErrorCode());
        //TODO 处理 图像处理结果，推送给api
        // ...
        return HandlerEnum.ACK;
    }

    @Override
    public boolean matchHandler(String queueName) {
        if (queueName.contains(QueueEnum.IMG_ERROR.getName())) {
            return true;
        }
        return false;
    }
}
