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

import com.cloud.cang.open.api.cr.dao.OpenGpuServerDao;
import com.cloud.cang.model.cr.OpenGpuServer;
import com.cloud.cang.open.api.cr.service.OpenGpuServerService;

@Service
public class OpenGpuServerServiceImpl extends GenericServiceImpl<OpenGpuServer, String> implements
		OpenGpuServerService {

	@Autowired
	OpenGpuServerDao openGpuServerDao;

	
	@Override
	public GenericDao<OpenGpuServer, String> getDao() {
		return openGpuServerDao;
	}

	
	

}