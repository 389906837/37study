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

import com.cloud.cang.mgr.vr.dao.CommodityDeclareDao;
import com.cloud.cang.model.vr.CommodityDeclare;
import com.cloud.cang.mgr.vr.service.CommodityDeclareService;

@Service
public class CommodityDeclareServiceImpl extends GenericServiceImpl<CommodityDeclare, String> implements
		CommodityDeclareService {

	@Autowired
	CommodityDeclareDao commodityDeclareDao;

	
	@Override
	public GenericDao<CommodityDeclare, String> getDao() {
		return commodityDeclareDao;
	}

	
	

}