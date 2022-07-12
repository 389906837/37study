package com.cloud.cang.mgr.vr.service.impl;

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

import com.cloud.cang.mgr.vr.dao.CommodityFeatureDao;
import com.cloud.cang.model.vr.CommodityFeature;
import com.cloud.cang.mgr.vr.service.CommodityFeatureService;

@Service
public class CommodityFeatureServiceImpl extends GenericServiceImpl<CommodityFeature, String> implements
		CommodityFeatureService {

	@Autowired
	CommodityFeatureDao commodityFeatureDao;

	
	@Override
	public GenericDao<CommodityFeature, String> getDao() {
		return commodityFeatureDao;
	}

	
	

}