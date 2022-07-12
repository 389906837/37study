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

import com.cloud.cang.mgr.ac.dao.DiscountRecordDao;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.mgr.ac.service.DiscountRecordService;

@Service
public class DiscountRecordServiceImpl extends GenericServiceImpl<DiscountRecord, String> implements
		DiscountRecordService {

	@Autowired
	DiscountRecordDao discountRecordDao;

	
	@Override
	public GenericDao<DiscountRecord, String> getDao() {
		return discountRecordDao;
	}

	
	

}