package com.cloud.cang.api.sb.service.impl;

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

import com.cloud.cang.api.sb.dao.MonitorDataConfDao;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.cloud.cang.api.sb.service.MonitorDataConfService;

@Service
public class MonitorDataConfServiceImpl extends GenericServiceImpl<MonitorDataConf, String> implements
		MonitorDataConfService {

	@Autowired
	MonitorDataConfDao monitorDataConfDao;

	
	@Override
	public GenericDao<MonitorDataConf, String> getDao() {
		return monitorDataConfDao;
	}

	
	

}