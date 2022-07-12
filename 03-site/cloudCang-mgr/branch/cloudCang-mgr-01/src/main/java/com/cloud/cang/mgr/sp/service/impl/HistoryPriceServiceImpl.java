package com.cloud.cang.mgr.sp.service.impl;

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

import com.cloud.cang.mgr.sp.dao.HistoryPriceDao;
import com.cloud.cang.model.sp.HistoryPrice;
import com.cloud.cang.mgr.sp.service.HistoryPriceService;

@Service
public class HistoryPriceServiceImpl extends GenericServiceImpl<HistoryPrice, String> implements
		HistoryPriceService {

	@Autowired
	HistoryPriceDao historyPriceDao;

	
	@Override
	public GenericDao<HistoryPrice, String> getDao() {
		return historyPriceDao;
	}

	
	

}