package com.cloud.cang.rmp.om.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rmp.om.dao.OrderCommodityDao;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.rmp.om.service.OrderCommodityService;

@Service
public class OrderCommodityServiceImpl extends GenericServiceImpl<OrderCommodity, String> implements
		OrderCommodityService {

	@Autowired
	OrderCommodityDao orderCommodityDao;

	
	@Override
	public GenericDao<OrderCommodity, String> getDao() {
		return orderCommodityDao;
	}

	/**
	 * 更新订单商品信息
	 * @param updateMap
	 */
	@Override
	public void updateByOrderId(Map<String, Object> updateMap) {
		orderCommodityDao.updateByOrderId(updateMap);
	}
}