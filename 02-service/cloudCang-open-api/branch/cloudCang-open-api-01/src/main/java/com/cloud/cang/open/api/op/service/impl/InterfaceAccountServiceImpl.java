package com.cloud.cang.open.api.op.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.open.api.op.dao.InterfaceAccountDao;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.open.api.op.service.InterfaceAccountService;

@Service
public class InterfaceAccountServiceImpl extends GenericServiceImpl<InterfaceAccount, String> implements
		InterfaceAccountService {

	@Autowired
	InterfaceAccountDao interfaceAccountDao;

	
	@Override
	public GenericDao<InterfaceAccount, String> getDao() {
		return interfaceAccountDao;
	}

	/**
	 * 根据查询参数获取接口账户
	 * @param paramMap 查询参数
	 * @return
	 */
	@Override
	public InterfaceAccount selectByMap(Map<String, Object> paramMap) {
		return interfaceAccountDao.selectByMap(paramMap);
	}

	/**
	 * 锁住接口账户信息
	 * @param id 账户ID
	 * @return
	 */
	@Override
	public InterfaceAccount selectByPrimaryKeySync(String id) {
		return interfaceAccountDao.selectByPrimaryKeySync(id);
	}

	/**
	 * 根据查询参数获取接口账户数据
	 * @param paramMap 查询参数
	 * @return
	 */
	@Override
	public InterfaceAccount selectByParamsMap(Map<String, Object> paramMap) {
		return interfaceAccountDao.selectByParamsMap(paramMap);
	}
}