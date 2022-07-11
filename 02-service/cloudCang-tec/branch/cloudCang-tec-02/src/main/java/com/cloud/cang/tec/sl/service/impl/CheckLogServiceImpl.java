package com.cloud.cang.tec.sl.service.impl;

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

import com.cloud.cang.tec.sl.dao.CheckLogDao;
import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.tec.sl.service.CheckLogService;

@Service
public class CheckLogServiceImpl extends GenericServiceImpl<CheckLog, String> implements
		CheckLogService {

	@Autowired
	CheckLogDao checkLogDao;

	
	@Override
	public GenericDao<CheckLog, String> getDao() {
		return checkLogDao;
	}

	
	

}