package com.cloud.cang.core.rmq.interfaces;

import com.cloud.cang.core.rmq.message.RmqMessage;

public interface RmqMessageHandler<T> {

    /**
     * 业务处理, 当matchHandler()返回true，才调用handler()
     * @param rmqMessage
     * @return HandlerEnum
     *     ACK(10," 正常消费,MQ移除队列"),
     *     NACK(20,"重回队列"),
     *     REJECT(30," 消息放入死信队列");
     */
    HandlerEnum handler(RmqMessage<T> rmqMessage);

    /**
     * 返回true表示：匹配到消息队列名称为‘queueName’的队列，由此handle()处理,
     * 返回false表示：未匹配到消息队列名称为‘queueName’的队列，handle()不处理
     * @param queueName
     * @return
     */
    boolean matchHandler(String queueName);
}
