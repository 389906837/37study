package com.cloud.cang.bzc.sl.service.impl;

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

import com.cloud.cang.bzc.sl.dao.DeviceOperDao;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.bzc.sl.service.DeviceOperService;

@Service
public class DeviceOperServiceImpl extends GenericServiceImpl<DeviceOper, String> implements
		DeviceOperService {

	@Autowired
	DeviceOperDao deviceOperDao;

	
	@Override
	public GenericDao<DeviceOper, String> getDao() {
		return deviceOperDao;
	}

	/**
	 * 获取最近一个会员的开门日志
	 * @param deviceCode 设备编号
	 */
	@Override
	public DeviceOper selectLastByDeviceId(String deviceCode) {
		return deviceOperDao.selectLastByDeviceId(deviceCode);
	}
}