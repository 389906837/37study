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
import com.cloud.cang.mq.message.Mq_Zlqf_ImgRec;
import com.cloud.cang.mq.message.Mq_Zlqf_ImgResult;
import com.cloud.cang.vis.InitServer;
import com.cloud.cang.vis.common.VisContant;
import com.cloud.cang.vis.detector.DetectorLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别消息（第三方回调）
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
@DependsOn("initServer")
public class ImgRecZlqfMessageHandler implements RmqMessageHandler<Mq_Zlqf_ImgRec> {
    private static final Logger logger = LoggerFactory.getLogger(ImgRecZlqfMessageHandler.class);
    @Autowired
    InitServer initServer;
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    ICached iCached;

    @Override
    public HandlerEnum handler(RmqMessage<Mq_Zlqf_ImgRec> rmqMessage) {
        // 若识别模型还未初始化完成，则不消费队列
        if(initServer.status !=200){
            logger.warn("模型初始化未完成！");
            return HandlerEnum.NACK;
        }

        // 调用底层识别
        Mq_Zlqf_ImgResult imgResult = null;
        Mq_Zlqf_ImgRec mq_imgRec = rmqMessage.getMessage();
        try {
            //保存图片
            //String imageUrl = this.saveImage(mq_imgRec);
            String imageUrl = mq_imgRec.getImgPath();
            if (imageUrl != null){
                Map<String, String> map = new HashMap<>();
                map.put("imageUrl", imageUrl);
                String rearms = JSONObject.toJSONString(map);

                String result = "";
                if(mq_imgRec.getResponseType() == 10){
                    logger.info("第三方调用recognitionImageByFileName识别参数: {}", rearms);
                    result = new DetectorLibrary().recogitionImageByFileName(rearms);
                    logger.info("第三方调用recogitionImageByFileName识别结果: {}", result);
                }else{
                    logger.info("第三方调用recogitionImageByFileNamePos识别参数: {}", rearms);
                    result = new DetectorLibrary().recogitionImageByFileNamePos(rearms);
                    logger.info("第三方调用recogitionImageByFileNamePos识别结果: {}", result);
                }

                imgResult = JSONUtil.toBean(result, Mq_Zlqf_ImgResult.class,true);

                // 识别成功
                if(imgResult != null && imgResult.getStatus() == 200 ){
                    this.sendImgResultToOpenApi(mq_imgRec,imgResult,rmqMessage);
                }else{
                    // 识别失败
                    this.sendImgErrorToOpenApi(mq_imgRec,imgResult,rmqMessage);
                    DingTalkUtils.sendText("第三方vis调用识别失败,消息ID:"+rmqMessage.getId());
                }
            }else {
                this.sendImgErrorToOpenApi(mq_imgRec,imgResult,rmqMessage);
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
        if(queueName.contains(QueueEnum.IMG_MODEL_DARKNET.getName())){
            return true;
        }
        return false;
    }


    /**
     * base64编码字符串转换为图片
     * @param imgStr base64编码字符串
     * @param path 图片路径
     * @return
     */
    public static boolean base64StrToImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 保存待识别图片
     * @param mq_imgRec
     */
    public String saveImage(Mq_Zlqf_ImgRec mq_imgRec){
        String imgPath = VisContant.IMAGE_SAVE_PATH + mq_imgRec.getImgCode() + "." + mq_imgRec.getImgFormat();
        if (base64StrToImage(mq_imgRec.getImgBase64(),imgPath)){
            return imgPath;
        }else {
            logger.error("第三方(中林清风)图片保存异常！");
            return null;
        }
    }


    /**
     * 识别失败，发送消息给open-api
     * @param mq_imgRec
     */
    private void sendImgErrorToOpenApi(Mq_Zlqf_ImgRec mq_imgRec,
                                       Mq_Zlqf_ImgResult imgResult,
                                       RmqMessage<Mq_Zlqf_ImgRec> rmqMessage){
        // 填充Mq_ImgResult
        imgResult.setImgCode(mq_imgRec.getImgCode());
        imgResult.setSuccess(false);
        imgResult.setStatus(-1000);
        // 识别结果，MQ推送给open-api
        RmqMessage build = RmqMessage.<Mq_Zlqf_ImgResult>builder()
                .queueName(QueueEnum.IMG_RESULT_DARKNET.getName())
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
    private void sendImgResultToOpenApi(Mq_Zlqf_ImgRec mq_imgRec,
                                        Mq_Zlqf_ImgResult imgResult,
                                        RmqMessage<Mq_Zlqf_ImgRec> rmqMessage){
        // 填充Mq_ImgResult
        imgResult.setImgCode(mq_imgRec.getImgCode());
        imgResult.setSuccess(true);
        // 识别结果，MQ推送给open-api
        RmqMessage build = RmqMessage.<Mq_Zlqf_ImgResult>builder()
                .queueName(QueueEnum.IMG_RESULT_DARKNET.getName())
                .message(imgResult)
                .flagDB(rmqMessage.isFlagDB())
                .build();
        rmqProducer.sendMessage(build);
    }
}
