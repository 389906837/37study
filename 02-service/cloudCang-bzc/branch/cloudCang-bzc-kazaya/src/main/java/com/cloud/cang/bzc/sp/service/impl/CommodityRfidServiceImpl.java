package com.cloud.cang.bzc.sp.service.impl;

import java.util.List;
import java.util.Set;

import com.cloud.cang.inventory.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.bzc.sp.dao.CommodityRfidDao;
import com.cloud.cang.model.sp.CommodityRfid;
import com.cloud.cang.bzc.sp.service.CommodityRfidService;

@Service
public class CommodityRfidServiceImpl extends GenericServiceImpl<CommodityRfid, String> implements
		CommodityRfidService {

	@Autowired
	CommodityRfidDao commodityRfidDao;

	
	@Override
	public GenericDao<CommodityRfid, String> getDao() {
		return commodityRfidDao;
	}


	@Override
	public List<CommodityVo> selectCommodityVoGruopByCommodityCode(Set<String> lables) {
		return commodityRfidDao.selectCommodityVoGruopByCommodityCode(lables);
	}
}