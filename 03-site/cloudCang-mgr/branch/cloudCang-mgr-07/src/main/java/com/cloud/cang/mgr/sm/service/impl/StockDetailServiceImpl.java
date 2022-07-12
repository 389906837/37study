package com.cloud.cang.mgr.sm.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.sm.vo.StockDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sm.dao.StockDetailDao;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.mgr.sm.service.StockDetailService;

@Service
public class StockDetailServiceImpl extends GenericServiceImpl<StockDetail, String> implements
		StockDetailService {

	@Autowired
	StockDetailDao stockDetailDao;

	
	@Override
	public GenericDao<StockDetail, String> getDao() {
		return stockDetailDao;
	}


	@Override
	public Page<StockDetail> selectStockDetail(Page<StockDetail> page, StockDetailVo stockDetailVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return stockDetailDao.selectStockDetail(stockDetailVo);
	}

	@Override
	public List<StockDetail> selectInfoId(String sid) {
		return stockDetailDao.selectInfoId(sid);
	}
}