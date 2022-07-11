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

import com.cloud.cang.api.sb.dao.DeviceModelDao;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.api.sb.service.DeviceModelService;

@Service
public class DeviceModelServiceImpl extends GenericServiceImpl<DeviceModel, String> implements
		DeviceModelService {

	@Autowired
	DeviceModelDao deviceModelDao;

	
	@Override
	public GenericDao<DeviceModel, String> getDao() {
		return deviceModelDao;
	}

	
	

}