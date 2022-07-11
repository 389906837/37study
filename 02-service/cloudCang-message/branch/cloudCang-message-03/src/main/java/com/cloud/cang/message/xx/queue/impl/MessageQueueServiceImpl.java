package com.cloud.cang.message.xx.queue.impl;

import com.cloud.cang.message.xx.queue.MessageQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlocking Message Queue
 * @author zhouhong
 * @version 1.0
 * @param <T>
 */
@Service
public class MessageQueueServiceImpl<T> implements MessageQueueService<T> {
    
    private static Logger LOGGER = LoggerFactory
		.getLogger(MessageQueueServiceImpl.class);
    
   //queue 缓存
   private final BlockingQueue<T> queueCache = new LinkedBlockingQueue<T>();

    @Override
    public T take() {
	try {
	    return queueCache.take();
	} catch (InterruptedException e) {
	    LOGGER.error("message queue take error:",e);
	}
	return null;
    }

    @Override
    public void put(T t) {
	try {
	    queueCache.put(t);
	} catch (InterruptedException e) {
	    LOGGER.error("message queue put error:",e);
	}
	
    }

}
