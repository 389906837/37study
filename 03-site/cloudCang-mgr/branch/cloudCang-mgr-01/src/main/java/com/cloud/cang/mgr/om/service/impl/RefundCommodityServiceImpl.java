package com.cloud.cang.mgr.om.service.impl;

import java.util.List;

import com.cloud.cang.mgr.om.domain.RefundCommodityDomain;
import com.cloud.cang.mgr.om.vo.RefundCommodityVo;
import com.cloud.cang.model.om.OrderCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.om.dao.RefundCommodityDao;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.mgr.om.service.RefundCommodityService;

@Service
public class RefundCommodityServiceImpl extends GenericServiceImpl<RefundCommodity, String> implements
		RefundCommodityService {

	@Autowired
	RefundCommodityDao refundCommodityDao;

	
	@Override
	public GenericDao<RefundCommodity, String> getDao() {
		return refundCommodityDao;
	}

	
	@Override
	/**
	 * 查看退款订单详情
	 *
	 * @param refundCommodityDomain
	 * @return
	 */
	public  Page<RefundCommodityVo> queryDetails(Page<RefundCommodityVo> page, RefundCommodityDomain refundCommodityDomain){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<RefundCommodityVo>) refundCommodityDao.queryDetailsByDomain(refundCommodityDomain);
	}

}