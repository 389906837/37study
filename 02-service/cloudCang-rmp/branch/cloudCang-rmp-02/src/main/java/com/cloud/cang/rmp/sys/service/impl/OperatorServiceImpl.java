package com.cloud.cang.rmp.sys.service.impl;

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

import com.cloud.cang.rmp.sys.dao.OperatorDao;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rmp.sys.service.OperatorService;

@Service
public class OperatorServiceImpl extends GenericServiceImpl<Operator, String> implements
		OperatorService {

	@Autowired
	OperatorDao operatorDao;

	
	@Override
	public GenericDao<Operator, String> getDao() {
		return operatorDao;
	}

	/**
	 * 根据手机号 获取系统用户
	 * @param smemberName 会员手机号
	 * @return
	 */
	@Override
	public Operator selectByMoblie(String smemberName, String smerchantId) {
		return operatorDao.selectByMoblie(smemberName, smerchantId);
	}
}