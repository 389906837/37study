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

import com.cloud.cang.act.ac.dao.CouponCodeExDetailsDao;
import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.cloud.cang.act.ac.service.CouponCodeExDetailsService;

@Service
public class CouponCodeExDetailsServiceImpl extends GenericServiceImpl<CouponCodeExDetails, String> implements
		CouponCodeExDetailsService {

	@Autowired
	CouponCodeExDetailsDao couponCodeExDetailsDao;

	
	@Override
	public GenericDao<CouponCodeExDetails, String> getDao() {
		return couponCodeExDetailsDao;
	}

	@Override
	public CouponCodeExDetails selectCouponCodeExDetailsByCouponCode(
			String couponCode) {
		return couponCodeExDetailsDao.selectCouponCodeExDetailsByCouponCode(couponCode);
	}
	

}