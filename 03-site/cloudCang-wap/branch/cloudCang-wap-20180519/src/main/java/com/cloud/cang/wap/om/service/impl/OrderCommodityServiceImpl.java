package com.cloud.cang.wap.om.service.impl;

import java.util.List;

import com.cloud.cang.wap.om.vo.CommodityDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.wap.om.dao.OrderCommodityDao;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.wap.om.service.OrderCommodityService;

@Service
public class OrderCommodityServiceImpl extends GenericServiceImpl<OrderCommodity, String> implements
		OrderCommodityService {

	@Autowired
	OrderCommodityDao orderCommodityDao;

	
	@Override
	public GenericDao<OrderCommodity, String> getDao() {
		return orderCommodityDao;
	}

	/***
	 * 获取订单商品明细
	 * @param sorderCode 订单编号
	 * @return
	 */
	@Override
	public List<CommodityDomain> selectByOrderCode(String sorderCode) {
		return orderCommodityDao.selectByOrderCode(sorderCode);
	}
}