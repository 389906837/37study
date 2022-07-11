package com.cloud.cang.bzc.sl.service.impl;

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

import com.cloud.cang.bzc.sl.dao.SystemAlarmDao;
import com.cloud.cang.model.sl.SystemAlarm;
import com.cloud.cang.bzc.sl.service.SystemAlarmService;

@Service
public class SystemAlarmServiceImpl extends GenericServiceImpl<SystemAlarm, String> implements
		SystemAlarmService {

	@Autowired
	SystemAlarmDao systemAlarmDao;

	
	@Override
	public GenericDao<SystemAlarm, String> getDao() {
		return systemAlarmDao;
	}

	
	

}