package com.cloud.cang.rmp.sp.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.rmp.sp.dao.CommodityInfoDao;
import com.cloud.cang.rmp.sp.service.CommodityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityInfoServiceImpl extends GenericServiceImpl<CommodityInfo, String> implements
		CommodityInfoService {

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<CommodityInfo, String> getDao() {
		return commodityInfoDao;
	}

	
	

}