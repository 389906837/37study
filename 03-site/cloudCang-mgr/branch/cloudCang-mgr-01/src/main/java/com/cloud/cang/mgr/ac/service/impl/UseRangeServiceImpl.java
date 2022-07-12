package com.cloud.cang.mgr.ac.service.impl;

import java.util.List;

import com.cloud.cang.mgr.ac.domain.UseRangeDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.ac.dao.UseRangeDao;
import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.mgr.ac.service.UseRangeService;

@Service
public class UseRangeServiceImpl extends GenericServiceImpl<UseRange, String> implements
		UseRangeService {

	@Autowired
	UseRangeDao useRangeDao;

	
	@Override
	public GenericDao<UseRange, String> getDao() {
		return useRangeDao;
	}

	/**
	 * @Author: zhouhong
	 * @Description: 获取设备的应用范围明细
	 * @param: actId 活动ID
	 * @Date: 2018/2/10 10:23
	 * @return com.cloud.cang.model.ac.UseRange
	 */
	@Override
	public UseRange selectByActId(String actId) {
		return useRangeDao.selectByActId(actId);
	}

	@Override
	public List<UseRangeDomain> selectUseRange(String actId) {
		return useRangeDao.selectUseRange(actId);
	}
}