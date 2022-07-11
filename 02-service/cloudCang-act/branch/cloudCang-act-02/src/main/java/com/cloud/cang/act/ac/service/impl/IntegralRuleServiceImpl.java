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

import com.cloud.cang.act.ac.dao.IntegralRuleDao;
import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.act.ac.service.IntegralRuleService;

@Service
public class IntegralRuleServiceImpl extends GenericServiceImpl<IntegralRule, String> implements
		IntegralRuleService {

	@Autowired
	IntegralRuleDao integralRuleDao;

	
	@Override
	public GenericDao<IntegralRule, String> getDao() {
		return integralRuleDao;
	}

	@Override
	public IntegralRule selectIntegralRuleByActivityId(String activityId) {
		return integralRuleDao.selectIntegralRuleByActivityId(activityId);
	}
	

}