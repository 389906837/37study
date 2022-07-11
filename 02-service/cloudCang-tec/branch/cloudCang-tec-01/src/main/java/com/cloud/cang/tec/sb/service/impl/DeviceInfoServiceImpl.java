package com.cloud.cang.tec.sb.service.impl;

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

import com.cloud.cang.tec.sb.dao.DeviceInfoDao;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.tec.sb.service.DeviceInfoService;

@Service
public class DeviceInfoServiceImpl extends GenericServiceImpl<DeviceInfo, String> implements
		DeviceInfoService {

	@Autowired
	DeviceInfoDao deviceInfoDao;

	
	@Override
	public GenericDao<DeviceInfo, String> getDao() {
		return deviceInfoDao;
	}


	/**
	 * 查看商户所有设备
	 *
	 * @param merchantId 商户ID
	 */
	@Override
	public String selectDevice(String merchantId) {
		return deviceInfoDao.selectDevice(merchantId);
	}

}