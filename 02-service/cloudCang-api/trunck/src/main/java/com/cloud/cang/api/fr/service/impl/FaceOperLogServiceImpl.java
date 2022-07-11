package com.cloud.cang.api.fr.service.impl;

import com.cloud.cang.api.fr.dao.FaceOperLogDao;
import com.cloud.cang.api.fr.service.FaceOperLogService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.fr.FaceOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceOperLogServiceImpl extends GenericServiceImpl<FaceOperLog, String> implements
		FaceOperLogService {

	@Autowired
	FaceOperLogDao faceOperLogDao;

	
	@Override
	public GenericDao<FaceOperLog, String> getDao() {
		return faceOperLogDao;
	}

	
	

}