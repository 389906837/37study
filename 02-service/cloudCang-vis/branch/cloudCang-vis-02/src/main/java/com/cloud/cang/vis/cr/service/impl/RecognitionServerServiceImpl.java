package com.cloud.cang.vis.cr.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.vis.cr.dao.RecognitionServerDao;
import com.cloud.cang.vis.cr.service.RecognitionServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecognitionServerServiceImpl extends GenericServiceImpl<RecognitionServer, String> implements
		RecognitionServerService {

	@Autowired
	RecognitionServerDao recognitionServerDao;

	
	@Override
	public GenericDao<RecognitionServer, String> getDao() {
		return recognitionServerDao;
	}


	@Override
	public int getRunningServerNum(String modelCode) {
		RecognitionServer server = new RecognitionServer();
		server.setIauditStatus(10);
		server.setIdelFlag(0);
		server.setSmodelCode(modelCode);
		List list = recognitionServerDao.selectByEntityWhere(server);
		return list ==null? 0:list.size();
	}
}