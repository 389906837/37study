package com.cloud.cang.api.sb.service.impl;

import com.cloud.cang.api.sb.dao.AiInfoDao;
import com.cloud.cang.api.sb.service.AiInfoService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sb.AiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiInfoServiceImpl extends GenericServiceImpl<AiInfo, String> implements
		AiInfoService {

	@Autowired
	AiInfoDao aiInfoDao;

	
	@Override
	public GenericDao<AiInfo, String> getDao() {
		return aiInfoDao;
	}

	
	

}