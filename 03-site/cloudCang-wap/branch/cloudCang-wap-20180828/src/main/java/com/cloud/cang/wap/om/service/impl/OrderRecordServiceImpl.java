package com.cloud.cang.wap.om.service.impl;

import java.util.List;
import java.util.Map;
import com.cloud.cang.wap.om.vo.OrderDomian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.wap.om.dao.OrderRecordDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.wap.om.service.OrderRecordService;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
		OrderRecordService {

	@Autowired
	OrderRecordDao orderRecordDao;

	
	@Override
	public GenericDao<OrderRecord, String> getDao() {
		return orderRecordDao;
	}

	/**
	 * 统计各类型订单数量
	 * @param memberId 会员ID
	 * @return
	 */
	@Override
	public Map<String, Object> statisticalOrderNumByMemberId(String memberId) {
		return orderRecordDao.statisticalOrderNumByMemberId(memberId);
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
	/**
	 * 获取订单数据
	 * @param orderCode 订单编号
	 * @return
	 */
	@Override
	public OrderRecord selectByCode(String orderCode) {
		return orderRecordDao.selectByCode(orderCode);
	}

	/**
	 * 查询用户的异常订单
	 * @param memberId 会员ID
	 * @return
	 */
	@Override
	public List<OrderRecord> selectExceptionOrder(String memberId) {
		return orderRecordDao.selectExceptionOrder(memberId);
	}
	/**
	 * 更新订单状态为支付处理中
	 * @param orderId 订单ID
	 */
	@Override
	public void updateStatus(String orderId) {
		orderRecordDao.updateStatus(orderId);
	}
}