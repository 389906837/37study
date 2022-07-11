package com.cloud.cang.core.mq.service.impl;

import com.cloud.cang.core.mq.dao.MessageDao;
import com.cloud.cang.core.mq.service.MessageService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.mq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends GenericServiceImpl<Message, String> implements
		MessageService {

	@Autowired
	MessageDao messageDao;

	
	@Override
	public GenericDao<Message, String> getDao() {
		return messageDao;
	}


	@Override
	public void updateMessageStatus(Message message) {
		messageDao.updateMessageStatus(message);
	}
}