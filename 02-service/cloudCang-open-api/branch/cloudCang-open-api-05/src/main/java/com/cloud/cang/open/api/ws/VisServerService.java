package com.cloud.cang.open.api.ws;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_ModelUpdate;
import com.cloud.cang.openapi.ImageParamsDto;
import com.cloud.cang.openapi.ModelUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 视觉服务cloudCang-vis操作
 */
@RestController
@RequestMapping("/vis")
@RegisterRestResource
public class VisServerService {
    private final static Logger logger = LoggerFactory.getLogger(VisServerService.class);
    @Autowired
    RmqProducer rmqProducer;

    /**
     * 更新模型
     * @param updateDto
     * @return
     */
    @RequestMapping(value = "/modelUpdate", method = RequestMethod.POST)
    public ResponseVo<String> modelUpdate(@RequestBody ModelUpdateDto updateDto) {
        logger.info("更新模型：{}",updateDto);
        Assert.notNull(updateDto);

        if(StrUtil.hasEmpty(updateDto.getHost(),
                updateDto.getPort(),
                updateDto.getNewModelCode())){
            return ResponseVo.getErrorResponse("缺少请求参数");
        }

        /*if(StrUtil.isNotBlank(updateDto.getOldModelCode()) &&
                StrUtil.equals(updateDto.getNewModelCode(),updateDto.getOldModelCode())){
            return ResponseVo.getSuccessResponse();
        }*/

        // 发送MQ消息给vis
        this.sendMQMessageToVis(updateDto);

        return ResponseVo.getSuccessResponse();
    }

    private void sendMQMessageToVis(ModelUpdateDto updateDto){
        Mq_ModelUpdate modelUpdate = Mq_ModelUpdate.builder()
                .host(StrUtil.cleanBlank(updateDto.getHost()))
                .port(StrUtil.cleanBlank(updateDto.getPort()))
                .modelCode(StrUtil.cleanBlank(updateDto.getNewModelCode()))
                .build();

        RmqMessage<Mq_ModelUpdate> message = RmqMessage.<Mq_ModelUpdate>builder()
                .queueName(QueueEnum.MODEL_UPDATE.getName())
                .message(modelUpdate)
                .build();
        rmqProducer.sendMessage(message);
    }


    @RequestMapping(value = "/sendTest")
    public  void sendTest() {

        ImageParamsDto imageParams = ImageParamsDto.builder()
                .imgCode("afasda")
                .imgBase64(ImageToBase64ByLocal("G:\\img\\20200329-D-0987-002.jpg"))
                .imgFormat("jpg")
                .build();
        RmqMessage<ImageParamsDto> message = RmqMessage.<ImageParamsDto>builder()
                .queueName(QueueEnum.IMG_MODEL_DARKNET.getName())
                .message(imageParams)
                .build();
        rmqProducer.sendMessage(message);

    }



    /**
     * 本地图片转换成base64字符串
     *
     * @param imgFile 图片本地路径
     * @return
     * @dateTime 2018-02-23 14:40:46
     */
    public static String ImageToBase64ByLocal(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        try {// 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

}
