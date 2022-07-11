package com.cloud.cang.core.rmq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-20 17:11
 **/
@Data
@Builder
public class RmqMessage<T> implements Serializable {
    // 消息ID
    private String id;
    // 消息发送时间
    private long time;
    // 消息队列的名称
    private String queueName;
    // 是否保存、更新MQ_MESSAGE
    @Builder.Default
    private boolean flagDB = true;
    // 消息内容
    private T message;

}
