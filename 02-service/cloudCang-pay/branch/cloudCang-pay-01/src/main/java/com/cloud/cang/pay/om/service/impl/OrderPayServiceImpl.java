package com.cloud.cang.pay.om.service.impl;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sys.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.om.dao.OrderPayDao;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.pay.om.service.OrderPayService;

import java.util.Date;
import java.util.Map;

@Service
public class OrderPayServiceImpl extends GenericServiceImpl<OrderPay, String> implements
		OrderPayService {

	@Autowired
	OrderPayDao orderPayDao;

	
	@Override
	public GenericDao<OrderPay, String> getDao() {
		return orderPayDao;
	}


	@Override
	public OrderPay insertOrderPay(OrderRecord orderRecord, Integer payType) throws Exception {
		OrderPay orderPay = new OrderPay();
		orderPay.setIpayType(payType);
		orderPay.setScode(CoreUtils.newCode("om_order_pay"));
		orderPay.setSorderCode(orderRecord.getSorderCode());
		orderPay.setSorderId(orderRecord.getId());
		orderPay.setIstatus(10);
		orderPay.setTaddTime(new Date());
		orderPayDao.insert(orderPay);
		return orderPay;
	}
	/**
	 * 查询订单号
	 * @param outTradePayNo 商户订单支付编号
	 * @return
	 */
	@Override
	public String selectOrderCodeByPayNo(String outTradePayNo) {
		return orderPayDao.selectOrderCodeByPayNo(outTradePayNo);
	}
	/**
	 * 更新数据
	 * @param map
	 */
	@Override
	public void updateDataByMap(Map<String, Object> map) {
		orderPayDao.updateDataByMap(map);
	}
	/**
	 * 查询商户订单支付编号
	 * @param outTradeNo 商户订单编号
	 * @return
	 */
	@Override
	public String selectOutTradeNoByOrderCode(String outTradeNo) {
		return orderPayDao.selectOutTradeNoByOrderCode(outTradeNo);
	}
}