package com.cloud.cang.wap.sb.service.impl;

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

import com.cloud.cang.wap.sb.dao.DeviceInfoDao;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.wap.sb.service.DeviceInfoService;

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
	 * 获取设备信息
	 * @param deviceCode 设备编号
	 */
	@Override
	public DeviceInfo selectByCode(String deviceCode) {
		return deviceInfoDao.selectByCode(deviceCode);
	}
}