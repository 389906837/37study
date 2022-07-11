package com.cloud.cang.api.sm.service.impl;

import java.util.List;

import com.cloud.cang.sb.InventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sm.dao.DeviceStockDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.api.sm.service.DeviceStockService;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
		DeviceStockService {

	@Autowired
	DeviceStockDao deviceStockDao;

	
	@Override
	public GenericDao<DeviceStock, String> getDao() {
		return deviceStockDao;
	}


	@Override
	public InventoryDto calculateCommodity(String deviceId) {
		DeviceStock deviceStock = new DeviceStock();
		deviceStock.setSdeviceId(deviceId);
		List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
//		List<>
		return null;
	}
}