package com.cloud.cang.open.api.cr.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.open.api.cr.dao.RecognitionServerDao;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.open.api.cr.service.RecognitionServerService;

@Service
public class RecognitionServerServiceImpl extends GenericServiceImpl<RecognitionServer, String> implements
		RecognitionServerService {

	@Autowired
	RecognitionServerDao recognitionServerDao;

	
	@Override
	public GenericDao<RecognitionServer, String> getDao() {
		return recognitionServerDao;
	}

	
	

}