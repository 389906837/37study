package com.cloud.cang.vis.ws;

import cn.hutool.http.HttpRequest;
import com.cloud.cang.core.rmq.RmqManager;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.mq.QueueEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-20 12:51
 **/
@RestController
@RequestMapping("/mq")
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
    public void send(String qname){
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setSmemberName("乔志刚");
        memberInfo.setSmobile("15295527477");
        memberInfo.setImemberType(10);
        RmqMessage.builder().build();
        RmqMessage message = RmqMessage.<MemberInfo>builder()
                            .queueName(QueueEnum.IMG_MODEL.getName() + qname)
                            .message(memberInfo)
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
}
