package com.cloud.cang.core.mq.service;

import com.cloud.cang.model.mq.Message;
import com.cloud.cang.generic.GenericService;

public interface MessageService extends GenericService<Message, String> {

    void updateMessageStatus(Message message);
    
}