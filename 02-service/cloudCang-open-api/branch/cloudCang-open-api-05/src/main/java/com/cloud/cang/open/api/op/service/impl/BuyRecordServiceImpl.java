package com.cloud.cang.open.api.op.service.impl;

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

import com.cloud.cang.open.api.op.dao.BuyRecordDao;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.open.api.op.service.BuyRecordService;

@Service
public class BuyRecordServiceImpl extends GenericServiceImpl<BuyRecord, String> implements
		BuyRecordService {

	@Autowired
	BuyRecordDao buyRecordDao;

	
	@Override
	public GenericDao<BuyRecord, String> getDao() {
		return buyRecordDao;
	}

	
	

}