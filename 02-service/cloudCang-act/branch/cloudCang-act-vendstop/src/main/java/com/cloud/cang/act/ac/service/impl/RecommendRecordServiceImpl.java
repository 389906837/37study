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

import com.cloud.cang.act.ac.dao.RecommendRecordDao;
import com.cloud.cang.model.ac.RecommendRecord;
import com.cloud.cang.act.ac.service.RecommendRecordService;

@Service
public class RecommendRecordServiceImpl extends GenericServiceImpl<RecommendRecord, String> implements
		RecommendRecordService {

	@Autowired
	RecommendRecordDao recommendRecordDao;

	
	@Override
	public GenericDao<RecommendRecord, String> getDao() {
		return recommendRecordDao;
	}

	@Override
	public Integer countRecommendRecordBySpresenteeId(String userId) {
		return recommendRecordDao.countRecommendRecordBySpresenteeId(userId);
	}

	@Override
	public RecommendRecord selectRecommendRecordBySpresenteeId(String userId) {
		return recommendRecordDao.selectRecommendRecordBySpresenteeId(userId);
	}
}