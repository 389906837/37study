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

import com.cloud.cang.api.sb.dao.DeviceUpgradeDao;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.api.sb.service.DeviceUpgradeService;

@Service
public class DeviceUpgradeServiceImpl extends GenericServiceImpl<DeviceUpgrade, String> implements
		DeviceUpgradeService {

	@Autowired
	DeviceUpgradeDao deviceUpgradeDao;

	
	@Override
	public GenericDao<DeviceUpgrade, String> getDao() {
		return deviceUpgradeDao;
	}

	
	

}