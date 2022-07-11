package com.cloud.cang.api.sl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sl.dao.DeviceOperDao;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.api.sl.service.DeviceOperService;

@Service
public class DeviceOperServiceImpl extends GenericServiceImpl<DeviceOper, String> implements
		DeviceOperService {

	@Autowired
	DeviceOperDao deviceOperDao;

	
	@Override
	public GenericDao<DeviceOper, String> getDao() {
		return deviceOperDao;
	}


	@Override
	public String selectLastOpLog(Map<String, String> map) {
		return deviceOperDao.selectLastOpLog(map);
	}
}