package com.cloud.cang.pay.rm.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.pay.rm.dao.ReplenishmentCommodityDao;
import com.cloud.cang.pay.rm.service.ReplenishmentCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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