package com.cloud.cang.api.mq.handler;

import com.cloud.cang.api.common.AndroidErrorCode;
import com.cloud.cang.api.netty.service.NettyExceptionMsgService;
import com.cloud.cang.common.TypeConstant;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_Weight_Exception_ImgResul;
import com.cloud.cang.pojo.BaseResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YLF on 2019/12/2.
 */
@Component
public class ImgWeightExceptionResultMessageHandler implements RmqMessageHandler<Mq_Cloud_Weight_Exception_ImgResul> {
    private static final Logger logger = LoggerFactory.getLogger(ImgExceptionResultMessageHandler.class);
    @Autowired
    private NettyExceptionMsgService nettyExceptionMsgService;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_Cloud_Weight_Exception_ImgResul> rmqMessage) {
        logger.info("{}", rmqMessage);
        Mq_Cloud_Weight_Exception_ImgResul message = rmqMessage.getMessage();
        Integer methodType = message.getMethodType();
        BaseResponseVo baseResponseVo = new BaseResponseVo();
        baseResponseVo.setUserId(message.getUserId());
        baseResponseVo.setUserId(message.getDeviceId());
        baseResponseVo.setSuccess(true);
        baseResponseVo.setExceptionGrade(TypeConstant.EXCEPTION);
        if (10 == methodType) {
            baseResponseVo.setCode(AndroidErrorCode.ERROR_OPENDOOR_FAILED.getCode());
        } else if (20 == methodType) {
            baseResponseVo.setCode(AndroidErrorCode.ERROR_CLOSEDOOR_FAILED.getCode());
        }
        nettyExceptionMsgService.handlerExceptionMessage(baseResponseVo);
        //TODO 处理 图像处理结果，推送给api
        return HandlerEnum.ACK;
    }

    @Override
    public boolean matchHandler(String queueName) {
        if (queueName.contains(QueueEnum.IMG_WEIGHT_ERROR.getName())) {
            return true;
        }
        return false;
    }
}
