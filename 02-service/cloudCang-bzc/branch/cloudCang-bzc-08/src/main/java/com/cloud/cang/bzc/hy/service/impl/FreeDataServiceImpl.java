package com.cloud.cang.bzc.hy.service.impl;

import java.util.HashMap;
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

import com.cloud.cang.bzc.hy.dao.FreeDataDao;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.bzc.hy.service.FreeDataService;

@Service
public class FreeDataServiceImpl extends GenericServiceImpl<FreeData, String> implements
		FreeDataService {

	@Autowired
	FreeDataDao freeDataDao;

	
	@Override
	public GenericDao<FreeData, String> getDao() {
		return freeDataDao;
	}

	/**
	 * 获取支付宝免密数据
	 * @param memberId 会员Id
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public FreeData selectByMemberId(String memberId, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("merchantCode", merchantCode);
		return freeDataDao.selectByMemberId(map);
	}
	

}