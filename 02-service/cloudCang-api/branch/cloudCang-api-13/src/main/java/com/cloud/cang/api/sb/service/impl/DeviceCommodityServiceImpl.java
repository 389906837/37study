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

import com.cloud.cang.api.sb.dao.DeviceCommodityDao;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.api.sb.service.DeviceCommodityService;

@Service
public class DeviceCommodityServiceImpl extends GenericServiceImpl<DeviceCommodity, String> implements
		DeviceCommodityService {

	@Autowired
	DeviceCommodityDao deviceCommodityDao;

	
	@Override
	public GenericDao<DeviceCommodity, String> getDao() {
		return deviceCommodityDao;
	}

	
	

}