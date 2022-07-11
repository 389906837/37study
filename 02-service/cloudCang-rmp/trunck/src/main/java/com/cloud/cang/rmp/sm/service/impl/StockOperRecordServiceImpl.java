package com.cloud.cang.rmp.sm.service.impl;

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

import com.cloud.cang.rmp.sm.dao.StockOperRecordDao;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.rmp.sm.service.StockOperRecordService;

@Service
public class StockOperRecordServiceImpl extends GenericServiceImpl<StockOperRecord, String> implements
		StockOperRecordService {

	@Autowired
	StockOperRecordDao stockOperRecordDao;

	
	@Override
	public GenericDao<StockOperRecord, String> getDao() {
		return stockOperRecordDao;
	}

	
	

}