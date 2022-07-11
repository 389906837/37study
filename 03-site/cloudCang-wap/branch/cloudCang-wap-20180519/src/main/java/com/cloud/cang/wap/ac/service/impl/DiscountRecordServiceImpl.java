package com.cloud.cang.wap.ac.service.impl;

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

import com.cloud.cang.wap.ac.dao.DiscountRecordDao;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.wap.ac.service.DiscountRecordService;

@Service
public class DiscountRecordServiceImpl extends GenericServiceImpl<DiscountRecord, String> implements
		DiscountRecordService {

	@Autowired
	DiscountRecordDao discountRecordDao;

	
	@Override
	public GenericDao<DiscountRecord, String> getDao() {
		return discountRecordDao;
	}

	/**
	 * 获取订单优惠信息
	 * @param params 查询参数
	 */
	@Override
	public DiscountRecord selectDiscountRecordByMap(Map<String, Object> params) {
		return discountRecordDao.selectDiscountRecordByMap(params);
	}
}