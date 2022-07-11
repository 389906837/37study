package com.cloud.cang.wap.om.service;

import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OrderRecordService extends GenericService<OrderRecord, String> {


    /**
     * 统计各类型订单数量
     * @param memberId 会员ID
     * @return
     */
    Map<String, Object> statisticalOrderNumByMemberId(String memberId);

    /***
     * 分页查询我的订单数据
     * @param page 分页参数
     * @param map 查询参数
     */
    Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map);

    /**
     * 获取订单数据
     * @param orderCode 订单编号
     * @return
     */
    OrderRecord selectByCode(String orderCode);

    /**
     * 查询用户的异常订单
     * @param memberId 会员ID
     * @return
     */
    List<OrderRecord> selectExceptionOrder(String memberId);

    /**
     * 更新订单状态为支付处理中
     * @param orderId 订单ID
     */
    void updateStatus(String orderId);
}