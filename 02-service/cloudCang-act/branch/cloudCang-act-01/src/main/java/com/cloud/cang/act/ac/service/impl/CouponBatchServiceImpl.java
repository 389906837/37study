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

import com.cloud.cang.act.ac.dao.CouponBatchDao;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.act.ac.service.CouponBatchService;

@Service
public class CouponBatchServiceImpl extends GenericServiceImpl<CouponBatch, String> implements
		CouponBatchService {

	@Autowired
	CouponBatchDao couponBatchDao;

	
	@Override
	public GenericDao<CouponBatch, String> getDao() {
		return couponBatchDao;
	}

	
	

}