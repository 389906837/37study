package com.cloud.cang.wap.om.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.wap.om.vo.CommodityDomain;
import com.cloud.cang.wap.om.vo.RefundCommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.wap.om.dao.RefundCommodityDao;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.wap.om.service.RefundCommodityService;

@Service
public class RefundCommodityServiceImpl extends GenericServiceImpl<RefundCommodity, String> implements
		RefundCommodityService {

	@Autowired
	RefundCommodityDao refundCommodityDao;

	
	@Override
	public GenericDao<RefundCommodity, String> getDao() {
		return refundCommodityDao;
	}

	/***
	 * 获取审核订单商品明细
	 * @param refundCode 审核订单编号
	 * @return
	 */
	@Override
	public List<CommodityDomain> selectByOrderCode(String refundCode) {
		return refundCommodityDao.selectByOrderCode(refundCode);
	}
	/**
	 * 查询退款审核订单商品明细
	 * @param params 查询参数
	 * @return
	 */
	@Override
	public List<RefundCommodityVo> selectByMap(Map<String, Object> params) {
		return refundCommodityDao.selectByMap(params);
	}
}