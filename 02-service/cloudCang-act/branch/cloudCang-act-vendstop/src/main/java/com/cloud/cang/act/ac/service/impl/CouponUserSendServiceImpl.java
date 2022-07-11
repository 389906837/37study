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

import com.cloud.cang.act.ac.dao.CouponUserSendDao;
import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.act.ac.service.CouponUserSendService;

@Service
public class CouponUserSendServiceImpl extends GenericServiceImpl<CouponUserSend, String> implements
		CouponUserSendService {

	@Autowired
	CouponUserSendDao couponUserSendDao;

	
	@Override
	public GenericDao<CouponUserSend, String> getDao() {
		return couponUserSendDao;
	}



	@Override
	public List<CouponUserSend> selectCouponUserSendByBatchId(String sbatchId) {
		return couponUserSendDao.selectCouponUserSendByBatchId(sbatchId);
	}

}