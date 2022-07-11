package com.cloud.cang.tec.sp.service.impl;

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

import com.cloud.cang.tec.sp.dao.CommodityInfoDao;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.tec.sp.service.CommodityInfoService;

@Service
public class CommodityInfoServiceImpl extends GenericServiceImpl<CommodityInfo, String> implements
		CommodityInfoService {

	@Autowired
	CommodityInfoDao commodityInfoDao;

	
	@Override
	public GenericDao<CommodityInfo, String> getDao() {
		return commodityInfoDao;
	}

	/**
	 *  更新商品数据 销售总数 平均销售价 平均成本价
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public int updateDataByMerchantId(String merchantId) {
		Integer updateNum = commodityInfoDao.updateDataByMerchantId(merchantId);
		if (null != updateNum) {
			return updateNum;
		}
		return 0;
	}
}