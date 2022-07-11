package com.cloud.cang.message.xx.queue;

public interface MessageQueueService<T> {
    
  
    /**
     * take the <T> from queue
     * @return
     */
    T take();
    
    
    /**
     * put the <T> to queue
     * @param t
     */
    void put(T t);

}
