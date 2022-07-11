package com.cloud.cang.tec.hy.service.impl;

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

import com.cloud.cang.tec.hy.dao.FreeOperLogDao;
import com.cloud.cang.model.hy.FreeOperLog;
import com.cloud.cang.tec.hy.service.FreeOperLogService;

@Service
public class FreeOperLogServiceImpl extends GenericServiceImpl<FreeOperLog, String> implements
		FreeOperLogService {

	@Autowired
	FreeOperLogDao freeOperLogDao;

	
	@Override
	public GenericDao<FreeOperLog, String> getDao() {
		return freeOperLogDao;
	}

	
	

}