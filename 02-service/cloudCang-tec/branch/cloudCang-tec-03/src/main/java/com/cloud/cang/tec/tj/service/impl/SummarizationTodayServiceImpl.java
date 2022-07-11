package com.cloud.cang.tec.tj.service.impl;

import java.util.Date;
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

import com.cloud.cang.tec.tj.dao.SummarizationTodayDao;
import com.cloud.cang.model.tj.SummarizationToday;
import com.cloud.cang.tec.tj.service.SummarizationTodayService;

@Service
public class SummarizationTodayServiceImpl extends GenericServiceImpl<SummarizationToday, String> implements
		SummarizationTodayService {

	@Autowired
	SummarizationTodayDao summarizationTodayDao;

	
	@Override
	public GenericDao<SummarizationToday, String> getDao() {
		return summarizationTodayDao;
	}


	/**
	 * 查询商户每日数据统计
	 * @param queryDate 查询日期
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public SummarizationToday selectSummarizationTodayByDate(Date queryDate, String merchantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryDate", queryDate);
		map.put("merchantId", merchantId);
		return summarizationTodayDao.selectSummarizationTodayByDate(map);
	}
	/**
	 * 查询当前日期的平台数据 当天
	 * @param queryDate 查询日期
	 * @param merchantId 商户Id
	 * @return
	 */
	@Override
	public SummarizationToday selectTodayStatisticsByDate(Date queryDate, String merchantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryDate", queryDate);
		map.put("merchantId", merchantId);
		return summarizationTodayDao.selectTodayStatisticsByDate(map);
	}
}