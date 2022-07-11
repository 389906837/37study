package com.cloud.cang.api.tb.service.impl;

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

import com.cloud.cang.api.tb.dao.ThirdDeviceSkuDao;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.api.tb.service.ThirdDeviceSkuService;

@Service
public class ThirdDeviceSkuServiceImpl extends GenericServiceImpl<ThirdDeviceSku, String> implements
		ThirdDeviceSkuService {

	@Autowired
	ThirdDeviceSkuDao thirdDeviceSkuDao;

	
	@Override
	public GenericDao<ThirdDeviceSku, String> getDao() {
		return thirdDeviceSkuDao;
	}

	
	

}