package com.cloud.cang.api.mq.handler;

import com.cloud.cang.api.netty.service.NettyMsgService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_Weight_ImgResul;
import com.cloud.cang.pojo.BaseResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别结果消息 云端视觉 + 称重
 * @author: ylf
 * @create: 2019-12-02 10:52
 **/
@Component
public class ImgWeightResultMessageHandler implements RmqMessageHandler<Mq_Cloud_Weight_ImgResul> {

    private static final Logger logger = LoggerFactory.getLogger(ImgWeightResultMessageHandler.class);
    @Autowired
    NettyMsgService nettyMsgService;
    @Autowired
    private ICached iCached;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_Cloud_Weight_ImgResul> rmqMessage) {
        logger.info("{}", rmqMessage);
        try {
            Mq_Cloud_Weight_ImgResul message = rmqMessage.getMessage();
            boolean isSuccess = message.isSuccess();
            if (isSuccess) {
                Integer openDoorType = message.getOpenDoorType();//开门类型10购物 20补货
                Integer type = message.getType();//10 开门 20 实时 30 关门
                BaseResponseVo baseResponseVo = new BaseResponseVo();
                baseResponseVo.setUserId(message.getUserId());
                if (10 == openDoorType) {
                    baseResponseVo.setData(message.getShopLayeredGoods());
                    if (10 == type) {
                        iCached.put("cloudOpenPicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                        baseResponseVo = new BaseResponseVo();
                        baseResponseVo.setDeviceId(message.getDeviceId());
                        nettyMsgService.gravityLayeredOpenDoor(baseResponseVo);
                    } else if (20 == type) {
                        nettyMsgService.localGravityLayeredOpenDoorInventory(message.getDeviceId(), message.getDeviceCode(), baseResponseVo);
                    } else if (30 == type) {
                        iCached.put("cloudClosePicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                        nettyMsgService.localGravityLayeredCloseDoor(message.getDeviceCode(), message.getDeviceId(), baseResponseVo);
                    }
                } else if (20 == openDoorType) {
                    baseResponseVo.setDeviceId(message.getDeviceId());
                    if (10 == type) {
                        iCached.put("cloudOpenPicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                        baseResponseVo.setData(message.getShopLayeredGoods());
                        nettyMsgService.gravityLayeredReplenOpenDoor(baseResponseVo);
                    } else if (20 == type) {
                        baseResponseVo.setData(message.getShopLayeredGoods());
                        nettyMsgService.localGravityLayeredReplenOpenDoorInventory(message.getDeviceId(), message.getDeviceCode(), baseResponseVo);
                    } else if (30 == type) {
                        iCached.put("cloudClosePicUrl_" + message.getDeviceId() + "_" + message.getUserId(), message.getPicUrlList());
                        baseResponseVo.setData(message.getGoods());
                        baseResponseVo.setOpenSource(message.getOpenSource());
                        nettyMsgService.closeDoorConfirmInventorySuccess(baseResponseVo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("云端称重消费MQ消息异常：{}", e);
            return HandlerEnum.ACK_BIZ_FAIL;
        }
        return HandlerEnum.ACK;
    }

    @Override
    public boolean matchHandler(String queueName) {
        if (queueName.contains(QueueEnum.IMG_OPENAPI_WEIGHT_RESULT.getName())) {
            return true;
        }
        return false;
    }
}
