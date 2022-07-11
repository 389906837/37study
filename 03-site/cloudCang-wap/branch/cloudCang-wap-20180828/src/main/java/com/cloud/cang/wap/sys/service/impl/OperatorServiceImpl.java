package com.cloud.cang.wap.sys.service.impl;

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

import com.cloud.cang.wap.sys.dao.OperatorDao;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.wap.sys.service.OperatorService;

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
	 * 获取系统用户
	 * @param userName 会员名
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public Operator selectByMemberName(String userName, String merchantCode) {
		return operatorDao.selectByMemberName(userName, merchantCode);
	}
}