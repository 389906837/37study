package com.cloud.cang.api.mq.handler;

import com.cloud.cang.api.netty.service.ImgCloudRecognition;
import com.cloud.cang.api.netty.service.NettyMsgService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_ImgResul;
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
public class ImgResultMessageHandler implements RmqMessageHandler<Mq_Cloud_ImgResul> {
    private static final Logger logger = LoggerFactory.getLogger(ImgResultMessageHandler.class);
    @Autowired
    private ImgCloudRecognition imgCloudRecognition;
    @Autowired
    private NettyMsgService nettyMsgService;
    @Autowired
    private ICached iCached;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_Cloud_ImgResul> rmqMessage) {
        logger.info("{}", rmqMessage);
        try {
            Mq_Cloud_ImgResul message = rmqMessage.getMessage();
            boolean isSuccess = message.isSuccess();
            if (isSuccess) {
                Integer type = message.getType();
                ResponseVo<String> responseVo = null;
                //10 开门 20 实时 30 关门
                if (10 == type) {
                    responseVo = imgCloudRecognition.cloudOpenDoor(message.getDeviceId(), message.getKey(), message.getImgResultDetail());
                    logger.info("云端识别开门返回：{}", responseVo);
                    if (null != responseVo && responseVo.isSuccess()) {
                        iCached.put("cloudOpenPicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                        nettyMsgService.openDoor(message.getDeviceId(), message.getUserId(), message.getOpenDoorType());
                        return HandlerEnum.ACK;
                    }
                } else if (20 == type) {
                    responseVo = imgCloudRecognition.cloudRealtime(message.getDeviceId(), message.getKey(), message.getUserId(), message.getOpenDoorType(), message.getImgResultDetail());
                    logger.info("云端识别实时订单返回：{}", responseVo);
                    if (null != responseVo && responseVo.isSuccess()) {
                        return HandlerEnum.ACK;
                    }
                } else if (30 == type) {
                    iCached.put("cloudClosePicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                    responseVo = imgCloudRecognition.cloudCloseDoor(message.getDeviceId(), message.getKey(), message.getUserId(), message.getOpenDoorType(), message.getImgResultDetail());
                    logger.info("云端识别关门返回：{}", responseVo);
                    if (null != responseVo && responseVo.isSuccess()) {
                        return HandlerEnum.ACK;
                    }
                } else {
                    logger.error("云端纯视觉,处理图像识别结果消息来源异常：{}", type);
                }
            }
        } catch (Exception e) {
            logger.error("云端消费MQ消息异常:{}", e);
        }
        //TODO 处理 图像处理结果，推送给api
        // ...
        return HandlerEnum.ACK_BIZ_FAIL;

    }

    @Override
    public boolean matchHandler(String queueName) {
        if (queueName.contains(QueueEnum.IMG_OPENAPI_RESULT.getName())) {
            return true;
        }
        return false;
    }
}
