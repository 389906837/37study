package com.cloud.cang.api.wz.service.impl;

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

import com.cloud.cang.api.wz.dao.RegionDao;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.api.wz.service.RegionService;

@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, String> implements
		RegionService {

	@Autowired
	RegionDao regionDao;

	
	@Override
	public GenericDao<Region, String> getDao() {
		return regionDao;
	}

	
	

}