package com.cloud.cang.mgr.ac.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.RecommendRecordDao;
import com.cloud.cang.mgr.ac.domain.RecommendRecordDomain;
import com.cloud.cang.mgr.ac.service.RecommendRecordService;
import com.cloud.cang.mgr.ac.vo.RecommendRecordVo;
import com.cloud.cang.model.ac.RecommendRecord;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Page<RecommendRecordDomain> selectQueryData(Page<RecommendRecordDomain> page, RecommendRecordVo recommendRecordVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return recommendRecordDao.selectQueryData(recommendRecordVo);
	}
}