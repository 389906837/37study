package com.cloud.cang.core.mq.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.mq.Message;

/** (MQ_MESSAGE) **/
public interface MessageDao extends GenericDao<Message, String> {


	/** codegen **/
    void updateMessageStatus(Message message);
}