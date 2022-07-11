package com.cloud.cang.api.sp.service.impl;

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

import com.cloud.cang.api.sp.dao.CommodityInfoDao;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.api.sp.service.CommodityInfoService;

@Service
public class CommodityInfoServiceImpl extends GenericServiceImpl<CommodityInfo, String> implements
		CommodityInfoService {

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<CommodityInfo, String> getDao() {
		return commodityInfoDao;
	}


	/***
	 * 查询商品信息
	 * @param svrCode 视觉识别编号
	 * @param smerchantId 商户编号
	 */
	@Override
	public CommodityInfo selectByVrCode(String svrCode, String smerchantId) {
		return commodityInfoDao.selectByVrCode(svrCode, smerchantId);
	}

}