package com.cloud.cang.mgr.ac.service.impl;

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

import com.cloud.cang.mgr.ac.dao.DiscountDetailDao;
import com.cloud.cang.model.ac.DiscountDetail;
import com.cloud.cang.mgr.ac.service.DiscountDetailService;

@Service
public class DiscountDetailServiceImpl extends GenericServiceImpl<DiscountDetail, String> implements
		DiscountDetailService {

	@Autowired
	DiscountDetailDao discountDetailDao;

	
	@Override
	public GenericDao<DiscountDetail, String> getDao() {
		return discountDetailDao;
	}

	
	

}