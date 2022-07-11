package com.cloud.cang.vis.mq.handler;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Cloud_Exception_ImgResul;
import com.cloud.cang.mq.message.Mq_ImgRec;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.open.sdk.model.request.ImgRecognitionDto;
import com.cloud.cang.openapi.APIConstant;
import com.cloud.cang.openapi.RecongitionVo;
import com.cloud.cang.vis.InitServer;
import com.cloud.cang.vis.detector.DetectorLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别消息
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
@DependsOn("initServer")
public class ImgRecMessageHandler implements RmqMessageHandler<Mq_ImgRec> {
    private static final Logger logger = LoggerFactory.getLogger(ImgRecMessageHandler.class);
    @Autowired
    InitServer initServer;
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    ICached iCached;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_ImgRec> rmqMessage) {
        // 若识别模型还未初始化完成，则不消费队列
        if(initServer.status != 200){
            logger.warn("模型初始化未完成！");
            return HandlerEnum.NACK;
        }

        // 调用底层识别
        Mq_ImgResult imgResult = null;
        Mq_ImgRec mq_imgRec = rmqMessage.getMessage();
        try {
            String rearms = JSONObject.toJSONString(mq_imgRec.getImgRecoItems());
            logger.info("调用recognitionImageByFileName识别参数: {}", rearms);

            String result = new DetectorLibrary().recogitionImageByFileName(rearms);
            logger.info("调用recognitionImageByFileName识别结果: {}", result);
            imgResult = JSONUtil.toBean(result, Mq_ImgResult.class);

            // 识别成功
            if(imgResult != null && imgResult.getStatus() == 200){
                this.sendImgResultToOpenApi(mq_imgRec, imgResult, rmqMessage);
            }else{
            // 识别失败
                this.sendImgErrorToApi(mq_imgRec);
                //删除缓存
                this.removeCache_recognitionvo(mq_imgRec.getBatchNo());
                // 钉钉预警
                DingTalkUtils.sendText("vis调用识别失败,消息ID:"+rmqMessage.getId());
            }
        } catch (Exception e) {
            logger.info("调用recognitionImageByFileName识别异常: {}", e.getMessage());
            //识别失败，发送消息给api
            this.sendImgErrorToApi(mq_imgRec);
            //删除缓存
            this.removeCache_recognitionvo(mq_imgRec.getBatchNo());
            // 钉钉预警
            DingTalkUtils.sendText("vis调用识别失败,消息ID:"+rmqMessage.getId());
        }
        return HandlerEnum.ACK;
    }

    // 删除缓存
    private void removeCache_recognitionvo(String batchNo){
        try {
            iCached.remove(APIConstant.RedisKey.IMG_RECOGNITIONVO +batchNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean matchHandler(String queueName) {
//        if(queueName.contains(QueueEnum.IMG_MODEL.getName())){
//            return true;
//        }
        return false;
    }

    /**
     * 从缓存中获取当前批次号的RecongitionVo<ImgRecognition></>
     * @param batchNo
     * @return
     */
    private ImgRecognitionDto getImgRecognitionDto(String batchNo) {
        try {
            Object object = iCached.get(APIConstant.RedisKey.IMG_RECOGNITIONVO + batchNo);
            if (object != null) {
                RecongitionVo recongitionVo =
                        JSONUtil.toBean(object.toString(), RecongitionVo.class);

                ImgRecognitionDto imgRecognition =
                        JSONUtil.toBean((cn.hutool.json.JSONObject) recongitionVo.getBizContent(),
                                ImgRecognitionDto.class);

                return imgRecognition;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 识别失败，发送消息给api
     * @param mq_imgRec
     *
     * // openDoor=开门
     * // openDoorCheck=开门校验
     * // openDoorInventory=实时盘货
     * // closeDoor=关门
     * // closeDoor_inventory=关门盘货
     * // localGravityLayeredOpenDoorInventory=购物实时
     * // localGravityLayeredReplenOpenDoorInventory=补货实时
     * // local_gravity_layered_close_door=称重分层 关门盘货
     */
    private void sendImgErrorToApi(Mq_ImgRec mq_imgRec){
        ImgRecognitionDto imgRecognitionDto = this.getImgRecognitionDto(mq_imgRec.getBatchNo());
        String methodStr = imgRecognitionDto.getMethod();
        if(this.isSendMq(methodStr)){
            int method = 20;
            if(StrUtil.equals("openDoor",methodStr)
                    || StrUtil.equals("openDoorCheck",methodStr)){
                method = 10;
            }
            Mq_Cloud_Exception_ImgResul errorImg = Mq_Cloud_Exception_ImgResul.builder()
                    .deviceId(mq_imgRec.getDeviceId())
                    .key(imgRecognitionDto.getKey())
                    .openDoorType(Integer.valueOf(imgRecognitionDto.getUserType()))
                    .methodType(method)
                    .userId(mq_imgRec.getUserId())
                    .build();
            // 识别结果，MQ推送给api
            RmqMessage build = RmqMessage.<Mq_Cloud_Exception_ImgResul>builder()
                    .queueName(QueueEnum.IMG_ERROR.getName())
                    .message(errorImg)
                    .build();
            // 发送异常处理
            rmqProducer.sendMessage(build);
        }
    }

    private boolean isSendMq(String methodStr){
        // 实时盘货，不发送
        String[]  METHOD_ARRAY= {
                "openDoorInventory",
                "localGravityLayeredOpenDoorInventory",
                "localGravityLayeredReplenOpenDoorInventory"};

        return !ArrayUtil.contains(METHOD_ARRAY,methodStr);
    }

    /**
     * 识别成功，发送消息给open-api
     * @param mq_imgRec
     * @param imgResult
     */
    private void sendImgResultToOpenApi(Mq_ImgRec mq_imgRec,Mq_ImgResult imgResult,
                                        RmqMessage<Mq_ImgRec> rmqMessage){
        // 填充Mq_ImgResult
        imgResult.setBatchNo(mq_imgRec.getBatchNo());
        imgResult.setDeviceId(mq_imgRec.getDeviceId());
        imgResult.setUserId(mq_imgRec.getUserId());
        // 识别结果，MQ推送给open-api
        RmqMessage build = RmqMessage.<Mq_ImgResult>builder()
                .queueName(QueueEnum.IMG_RESULT.getName())
                .message(imgResult)
                .flagDB(rmqMessage.isFlagDB())
                .build();
        rmqProducer.sendMessage(build);
    }
}
