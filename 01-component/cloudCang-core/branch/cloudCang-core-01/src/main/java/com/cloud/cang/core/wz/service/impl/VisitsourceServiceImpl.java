package com.cloud.cang.core.wz.service.impl;

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

import com.cloud.cang.core.wz.dao.VisitsourceDao;
import com.cloud.cang.model.wz.Visitsource;
import com.cloud.cang.core.wz.service.VisitsourceService;

@Service
public class VisitsourceServiceImpl extends GenericServiceImpl<Visitsource, String> implements
		VisitsourceService {

	@Autowired
	VisitsourceDao visitsourceDao;

	
	@Override
	public GenericDao<Visitsource, String> getDao() {
		return visitsourceDao;
	}

	
	

}