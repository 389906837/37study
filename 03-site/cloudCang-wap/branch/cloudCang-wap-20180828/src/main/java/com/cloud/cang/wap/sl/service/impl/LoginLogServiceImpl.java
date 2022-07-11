package com.cloud.cang.wap.sl.service.impl;

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

import com.cloud.cang.wap.sl.dao.LoginLogDao;
import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.wap.sl.service.LoginLogService;

@Service
public class LoginLogServiceImpl extends GenericServiceImpl<LoginLog, String> implements
		LoginLogService {

	@Autowired
	LoginLogDao loginLogDao;

	
	@Override
	public GenericDao<LoginLog, String> getDao() {
		return loginLogDao;
	}

	
	

}