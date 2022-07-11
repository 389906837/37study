package com.cloud.cang.wap.ac.service.impl;

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

import com.cloud.cang.wap.ac.dao.ActivityConfDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.wap.ac.service.ActivityConfService;

@Service
public class ActivityConfServiceImpl extends GenericServiceImpl<ActivityConf, String> implements
		ActivityConfService {

	@Autowired
	ActivityConfDao activityConfDao;

	
	@Override
	public GenericDao<ActivityConf, String> getDao() {
		return activityConfDao;
	}

	/**
	 * 查询活动信息
	 * @param map 查询参数
	 * @return
	 */
	@Override
	public ActivityConf selectByMap(Map<String, Object> map) {
		return activityConfDao.selectByMap(map);
	}
}