package com.cloud.cang.tec.tec.service.impl;

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

import com.cloud.cang.tec.tec.dao.ScheduleLogDao;
import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.tec.tec.service.ScheduleLogService;

@Service
public class ScheduleLogServiceImpl extends GenericServiceImpl<ScheduleLog, String> implements
		ScheduleLogService {

	@Autowired
	ScheduleLogDao scheduleLogDao;

	
	@Override
	public GenericDao<ScheduleLog, String> getDao() {
		return scheduleLogDao;
	}


	@Override
	public int getScheduleCountToday() {
		return scheduleLogDao.getScheduleCountToday();
	}


	

}