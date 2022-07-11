package com.cloud.cang.api.sb.service.impl;

import com.cloud.cang.api.sb.dao.CameraConfigDao;
import com.cloud.cang.api.sb.service.CameraConfigService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sb.CameraConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraConfigServiceImpl extends GenericServiceImpl<CameraConfig, String> implements
		CameraConfigService {

	@Autowired
	CameraConfigDao cameraConfigDao;

	
	@Override
	public GenericDao<CameraConfig, String> getDao() {
		return cameraConfigDao;
	}

	
	

}