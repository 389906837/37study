package com.cloud.cang.tec.om.service.impl;

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

import com.cloud.cang.tec.om.dao.OrderPayDao;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.tec.om.service.OrderPayService;

@Service
public class OrderPayServiceImpl extends GenericServiceImpl<OrderPay, String> implements
        OrderPayService {

    @Autowired
    OrderPayDao orderPayDao;


    @Override
    public GenericDao<OrderPay, String> getDao() {
        return orderPayDao;
    }

    /**
     * 根据订单编号查询付款编号
     *
     * @param orderCode
     * @return
     */
    @Override
    public String selectOutTradeNoByOrderCode(String orderCode) {
        return orderPayDao.selectOutTradeNoByOrderCode(orderCode);
    }
    /**
     * 更新数据
     *
     * @param map
     */
    @Override
    public void updateDataByMap(Map<String, Object> map) {
        orderPayDao.updateDataByMap(map);
    }

}