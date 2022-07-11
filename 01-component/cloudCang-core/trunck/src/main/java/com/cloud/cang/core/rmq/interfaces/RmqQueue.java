package com.cloud.cang.core.rmq.interfaces;

import org.springframework.amqp.core.Queue;

import java.util.List;

public interface RmqQueue {
    List<Queue> getQueues();
}
