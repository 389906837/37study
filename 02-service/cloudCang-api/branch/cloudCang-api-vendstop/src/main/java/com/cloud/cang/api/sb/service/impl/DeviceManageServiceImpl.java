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

import com.cloud.cang.api.sb.dao.DeviceManageDao;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.api.sb.service.DeviceManageService;

@Service
public class DeviceManageServiceImpl extends GenericServiceImpl<DeviceManage, String> implements
		DeviceManageService {

	@Autowired
	DeviceManageDao deviceManageDao;

	
	@Override
	public GenericDao<DeviceManage, String> getDao() {
		return deviceManageDao;
	}

	
	

}