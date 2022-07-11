package com.cloud.cang.api.om.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.api.om.vo.OrderDomian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.om.dao.OrderRecordDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.api.om.service.OrderRecordService;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
		OrderRecordService {

	@Autowired
	OrderRecordDao orderRecordDao;

	
	@Override
	public GenericDao<OrderRecord, String> getDao() {
		return orderRecordDao;
	}


	/***
	 * 分页查询我的订单数据
	 * @param page 分页参数
	 * @param map 查询参数
	 */
	@Override
	public Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return orderRecordDao.selectOrderListByPage(map);
	}

}