package com.cloud.cang.rmp.rm.service.impl;

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

import com.cloud.cang.rmp.rm.dao.ReplenishmentPlanDetailDao;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;
import com.cloud.cang.rmp.rm.service.ReplenishmentPlanDetailService;

@Service
public class ReplenishmentPlanDetailServiceImpl extends GenericServiceImpl<ReplenishmentPlanDetail, String> implements
		ReplenishmentPlanDetailService {

	@Autowired
	ReplenishmentPlanDetailDao replenishmentPlanDetailDao;

	
	@Override
	public GenericDao<ReplenishmentPlanDetail, String> getDao() {
		return replenishmentPlanDetailDao;
	}

	
	

}