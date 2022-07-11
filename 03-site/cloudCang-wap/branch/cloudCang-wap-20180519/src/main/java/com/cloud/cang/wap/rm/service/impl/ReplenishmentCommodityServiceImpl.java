package com.cloud.cang.wap.rm.service.impl;

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

import com.cloud.cang.wap.rm.dao.ReplenishmentCommodityDao;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.wap.rm.service.ReplenishmentCommodityService;

@Service
public class ReplenishmentCommodityServiceImpl extends GenericServiceImpl<ReplenishmentCommodity, String> implements
		ReplenishmentCommodityService {

	@Autowired
	ReplenishmentCommodityDao replenishmentCommodityDao;

	
	@Override
	public GenericDao<ReplenishmentCommodity, String> getDao() {
		return replenishmentCommodityDao;
	}

	
	

}