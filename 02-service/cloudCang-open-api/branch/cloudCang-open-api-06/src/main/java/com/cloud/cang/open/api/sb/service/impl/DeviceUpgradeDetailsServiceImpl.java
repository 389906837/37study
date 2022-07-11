package com.cloud.cang.open.api.sb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.open.api.sb.dao.DeviceUpgradeDetailsDao;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.open.api.sb.service.DeviceUpgradeDetailsService;

@Service
public class DeviceUpgradeDetailsServiceImpl extends GenericServiceImpl<DeviceUpgradeDetails, String> implements
		DeviceUpgradeDetailsService {

	@Autowired
	DeviceUpgradeDetailsDao deviceUpgradeDetailsDao;

	
	@Override
	public GenericDao<DeviceUpgradeDetails, String> getDao() {
		return deviceUpgradeDetailsDao;
	}

	/**
	 * 更新数据
	 * @param paramMap 更新参数
	 * @return
	 */
	@Override
	public Integer updateByMap(Map<String, Object> paramMap) {
		return deviceUpgradeDetailsDao.updateByMap(paramMap);
	}
}