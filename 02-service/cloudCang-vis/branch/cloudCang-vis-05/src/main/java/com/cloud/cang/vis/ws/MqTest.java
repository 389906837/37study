package com.cloud.cang.vis.ws;

import cn.hutool.http.HttpRequest;
import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.openapi.ImageParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-20 12:51
 **/
@RestController
@RequestMapping("/mq")
@RegisterRestResource
public class MqTest {
    @Autowired
    RmqManager rmqManager;
    @Autowired
    RmqProducer rmqProducer;

    @RequestMapping(value = "/declare/queue")
    public void declareQueue(String name){
        System.out.println(CoreUtils.getPortByMBean());
        rmqManager.declareQueue(name);
    }

    @RequestMapping(value = "/send")
    public void send(){

        ImageParamsDto imageParams = ImageParamsDto.builder()
                .imgCode("afasda")
                .imgBase64(ImageToBase64ByLocal("/data/zlqf/test.jpg"))
                .imgFormat("jpg")
                .build();
        RmqMessage message = RmqMessage.<ImageParamsDto>builder()
                            .queueName(QueueEnum.IMG_MODEL_DARKNET.getName())
                            .message(imageParams)
                            .flagDB(false)
                            .build();
        rmqProducer.sendMessage(message);
    }

    @RequestMapping(value = "/queues")
    public void queues(){
        String result = HttpRequest.get("http://10.0.101.111:15672/api/queues")
                .basicAuth("root","p2p58066815")
                .contentType("application/json")
                .execute().body();
        System.out.println(result);
    }



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


    public static void main(String[] args) {
        MqTest mqTest = new MqTest();
        mqTest.sendTest();
    }

}
