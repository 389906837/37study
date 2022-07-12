package com.cloud.cang.mgr.vr.service.impl;

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

import com.cloud.cang.mgr.vr.dao.MerchantDeviceSkuDao;
import com.cloud.cang.model.vr.MerchantDeviceSku;
import com.cloud.cang.mgr.vr.service.MerchantDeviceSkuService;

@Service
public class MerchantDeviceSkuServiceImpl extends GenericServiceImpl<MerchantDeviceSku, String> implements
		MerchantDeviceSkuService {

	@Autowired
	MerchantDeviceSkuDao merchantDeviceSkuDao;

	
	@Override
	public GenericDao<MerchantDeviceSku, String> getDao() {
		return merchantDeviceSkuDao;
	}

	
	

}