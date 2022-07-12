package com.cloud.cang.mgr.om.service.impl;

import java.util.List;

import com.cloud.cang.mgr.om.domain.OrderAuditCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderAuditCommodityVo;
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

import com.cloud.cang.mgr.om.dao.OrderAuditCommodityDao;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.mgr.om.service.OrderAuditCommodityService;

@Service
public class OrderAuditCommodityServiceImpl extends GenericServiceImpl<OrderAuditCommodity, String> implements
        OrderAuditCommodityService {

    @Autowired
    OrderAuditCommodityDao orderAuditCommodityDao;


    @Override
    public GenericDao<OrderAuditCommodity, String> getDao() {
        return orderAuditCommodityDao;
    }


    @Override
    /**
     * 查看异常订单详情
     *
     * @param orderAuditCommodityDomain
     * @return
     */
    public Page<OrderAuditCommodityVo> queryDetails(Page<OrderAuditCommodityVo> page, OrderAuditCommodityDomain orderAuditCommodityDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<OrderAuditCommodityVo>) orderAuditCommodityDao.queryDetailsByDomain(orderAuditCommodityDomain);
    }

    /**
     * 编辑异常订单详情
     *
     * @param orderAuditCommodity 异常订单
     * @return OrderAuditCommodityVo
     */
    @Override
    public List<OrderAuditCommodityVo> selectOrderAuditDetail(OrderAuditCommodity orderAuditCommodity) {
        return orderAuditCommodityDao.selectOrderAuditDetail(orderAuditCommodity);
    }

    /**
     * 根据异常订单Id删除从表
     *
     * @param orderId
     */
    @Override
    public void delectByOrderId(String orderId){
         orderAuditCommodityDao.delectByOrderId(orderId);
    }
}