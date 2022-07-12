package com.cloud.cang.mgr.rm.service.impl;

import java.util.List;
import java.util.Map;


import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.rm.dao.ReplenishmentRecordDao;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.mgr.rm.service.ReplenishmentRecordService;

@Service
public class ReplenishmentRecordServiceImpl extends GenericServiceImpl<ReplenishmentRecord, String> implements
		ReplenishmentRecordService {

	@Autowired
	ReplenishmentRecordDao replenishmentRecordDao;

	
	@Override
	public GenericDao<ReplenishmentRecord, String> getDao() {
		return replenishmentRecordDao;
	}


	@Override
	public Page<ReplenishMentDomain> selectReplenishMent(Page<ReplenishMentDomain> page, ReplenishMentVo replenishMentVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return  replenishmentRecordDao.selectReplenishMent(replenishMentVo);
	}

	@Override
	public List<Map<String, Object>> selectReplenishMentByExport(ReplenishMentVo replenishMentVo) {
		return replenishmentRecordDao.selectReplenishMentByExport(replenishMentVo);
	}

}