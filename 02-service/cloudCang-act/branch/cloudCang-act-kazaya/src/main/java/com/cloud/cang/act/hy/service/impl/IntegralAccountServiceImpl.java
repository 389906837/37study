package com.cloud.cang.act.hy.service.impl;

import com.cloud.cang.act.hy.dao.IntegralAccountDao;
import com.cloud.cang.act.hy.service.IntegralAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.model.hy.IntegralAccount;

@Service
public class IntegralAccountServiceImpl extends GenericServiceImpl<IntegralAccount, String> implements
        IntegralAccountService {

	@Autowired
    IntegralAccountDao integralAccountDao;

	
	@Override
	public GenericDao<IntegralAccount, String> getDao() {
		return integralAccountDao;
	}

	
	

}