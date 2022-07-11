package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.message.xx.dao.MsgTemplateDao;
import com.cloud.cang.message.xx.service.MsgTemplateService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgTemplateServiceImpl extends GenericServiceImpl<MsgTemplate, String> implements
        MsgTemplateService {

	@Autowired
    MsgTemplateDao msgTemplateDao;

	
	@Override
	public GenericDao<MsgTemplate, String> getDao() {
		return msgTemplateDao;
	}

	
	

}