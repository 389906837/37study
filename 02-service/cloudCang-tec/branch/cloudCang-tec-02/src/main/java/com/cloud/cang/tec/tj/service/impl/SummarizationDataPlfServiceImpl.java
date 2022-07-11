package com.cloud.cang.tec.tj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.tj.StatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.tj.dao.SummarizationDataPlfDao;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.tec.tj.service.SummarizationDataPlfService;

@Service
public class SummarizationDataPlfServiceImpl extends GenericServiceImpl<SummarizationDataPlf, String> implements
		SummarizationDataPlfService {

	@Autowired
	SummarizationDataPlfDao summarizationDataPlfDao;

	
	@Override
	public GenericDao<SummarizationDataPlf, String> getDao() {
		return summarizationDataPlfDao;
	}

	/**
	 * 查询最近一次统计结果
	 * @param merchantId 商户Id
	 * @return
	 */
	@Override
	public SummarizationDataPlf selectLatelySummarizationDataPlf(String merchantId) {
		return summarizationDataPlfDao.selectLatelySummarizationDataPlf(merchantId);
	}

	/**
	 * 查询当天
	 * @param queryDate 查询时间
	 * @param merchantId 商户Id
	 * @return
	 */
	@Override
	public StatisticsVo selectTodayStatisticsByDate(Date queryDate, String merchantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryDate", queryDate);
		map.put("merchantId", merchantId);
		return summarizationDataPlfDao.selectTodayStatisticsByDate(map);
	}
	/**
	 * 查询平台统计数据
	 * @param bqueryDate 查询统计开始日期
	 * @param equeryDate 查询统计截止日期
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public StatisticsVo selectStatisticsByDate(Date bqueryDate, Date equeryDate, String merchantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bqueryDate", bqueryDate);
		map.put("equeryDate", equeryDate);
		map.put("merchantId", merchantId);
		return summarizationDataPlfDao.selectStatisticsByDate(map);
	}
}