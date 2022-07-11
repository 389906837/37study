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

import com.cloud.cang.open.api.cr.dao.ServerModelDao;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.open.api.cr.service.ServerModelService;

@Service
public class ServerModelServiceImpl extends GenericServiceImpl<ServerModel, String> implements
		ServerModelService {

	@Autowired
	ServerModelDao serverModelDao;

	
	@Override
	public GenericDao<ServerModel, String> getDao() {
		return serverModelDao;
	}

	
	

}