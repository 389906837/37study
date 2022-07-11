package com.cloud.cang.vis.mq.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_ImgRec;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.vis.InitServer;
import com.cloud.cang.vis.detector.DetectorLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别消息（第三方回调） 第三方异步回调方法 cloud.api.recognition.async
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
@DependsOn("initServer")
public class ImgRecCallBackMessageHandler implements RmqMessageHandler<Mq_ImgRec> {
    private static final Logger logger = LoggerFactory.getLogger(ImgRecCallBackMessageHandler.class);
    @Autowired
    InitServer initServer;
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    ICached iCached;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_ImgRec> rmqMessage) {
        // 若识别模型还未初始化完成，则不消费队列
        if(initServer.status !=200){
            logger.warn("模型初始化未完成！");
            return HandlerEnum.NACK;
        }

        // 调用底层识别
        Mq_ImgResult imgResult = null;
        Mq_ImgRec mq_imgRec = rmqMessage.getMessage();
        try {
            String rearms = JSONObject.toJSONString(mq_imgRec.getImgRecoItems());
            logger.info("第三方异步回调识别参数: {}", JSONObject.toJSONString(mq_imgRec));

            String result = "";
            if(mq_imgRec.getResponseType() == 10){
                result = new DetectorLibrary().recogitionImageByFileName(rearms);
                logger.info("recogitionImageByFileName第三方异步回调识别结果: {}", result);
            }else{
                result = new DetectorLibrary().recogitionImageByFileNamePos(rearms);
                logger.info("recogitionImageByFileNamePos第三方异步回调识别结果: {}", result);
            }
            imgResult = JSONUtil.toBean(result, Mq_ImgResult.class,true);

            // 识别成功
            if(imgResult != null && imgResult.getStatus() == 200 ){
                this.sendImgResultToOpenApi(mq_imgRec,imgResult,rmqMessage);
            }else{
            // 识别失败
                this.sendImgErrorToOpenApi(mq_imgRec,imgResult,rmqMessage);
                DingTalkUtils.sendText("第三方vis调用识别失败,消息ID:"+rmqMessage.getId());
            }
        } catch (Exception e) {
            logger.info("第三方调用recognitionImageByFileName识别异常: {}", e.getMessage());
            // 钉钉预警
            DingTalkUtils.sendText("第三方vis调用识别失败,消息ID:"+rmqMessage.getId());
            // 识别失败，发送消息给api
            this.sendImgErrorToOpenApi(mq_imgRec,imgResult,rmqMessage);
        }
        return HandlerEnum.ACK;
    }

    @Override
    public boolean matchHandler(String queueName) {
        if(queueName.contains(QueueEnum.IMG_MODEL_CALLBACK.getName())){
            return true;
        }
        return false;
    }

    /**
     * 识别失败，发送消息给open-api
     * @param mq_imgRec
     */
    private void sendImgErrorToOpenApi(Mq_ImgRec mq_imgRec,
                                       Mq_ImgResult imgResult,
                                       RmqMessage<Mq_ImgRec> rmqMessage){
        // 填充Mq_ImgResult
        imgResult.setBatchNo(mq_imgRec.getBatchNo());
        imgResult.setDeviceId(mq_imgRec.getDeviceId());
        imgResult.setStatus(-1000);
        // 识别结果，MQ推送给open-api
        RmqMessage build = RmqMessage.<Mq_ImgResult>builder()
                .queueName(QueueEnum.IMG_RESULT_CALLBACK.getName())
                .message(imgResult)
                .flagDB(rmqMessage.isFlagDB())
                .build();
        rmqProducer.sendMessage(build);
    }

    /**
     * 识别成功，发送消息给open-api
     * @param mq_imgRec
     * @param imgResult
     */
    private void sendImgResultToOpenApi(Mq_ImgRec mq_imgRec,
                                        Mq_ImgResult imgResult,
                                        RmqMessage<Mq_ImgRec> rmqMessage){
        // 填充Mq_ImgResult
        imgResult.setBatchNo(mq_imgRec.getBatchNo());
        imgResult.setDeviceId(mq_imgRec.getDeviceId());
        imgResult.setStatus(imgResult.getStatus());
        // 识别结果，MQ推送给open-api
        RmqMessage build = RmqMessage.<Mq_ImgResult>builder()
                .queueName(QueueEnum.IMG_RESULT_CALLBACK.getName())
                .message(imgResult)
                .flagDB(rmqMessage.isFlagDB())
                .build();
        rmqProducer.sendMessage(build);
    }
}
