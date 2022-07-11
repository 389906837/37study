package com.cloud.cang.act.ac.service.impl;

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

import com.cloud.cang.act.ac.dao.FirstRewardIntegralDao;
import com.cloud.cang.model.ac.FirstRewardIntegral;
import com.cloud.cang.act.ac.service.FirstRewardIntegralService;

@Service
public class FirstRewardIntegralServiceImpl extends GenericServiceImpl<FirstRewardIntegral, String> implements
		FirstRewardIntegralService {

	@Autowired
	FirstRewardIntegralDao firstRewardIntegralDao;

	
	@Override
	public GenericDao<FirstRewardIntegral, String> getDao() {
		return firstRewardIntegralDao;
	}

	
	

}