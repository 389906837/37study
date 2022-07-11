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

import com.cloud.cang.rmp.om.dao.OrderAuditCommodityDao;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.rmp.om.service.OrderAuditCommodityService;

@Service
public class OrderAuditCommodityServiceImpl extends GenericServiceImpl<OrderAuditCommodity, String> implements
		OrderAuditCommodityService {

	@Autowired
	OrderAuditCommodityDao orderAuditCommodityDao;

	
	@Override
	public GenericDao<OrderAuditCommodity, String> getDao() {
		return orderAuditCommodityDao;
	}

	/**
	 * 更新审核订单商品信息
	 * @param updateMap
	 */
	@Override
	public void updateByOrderId(Map<String, Object> updateMap) {
		orderAuditCommodityDao.updateByOrderId(updateMap);
	}
}