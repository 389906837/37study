package com.cloud.cang.api.om.service;

import com.cloud.cang.api.om.vo.OrderDomian;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.Map;

public interface OrderRecordService extends GenericService<OrderRecord, String> {

    /***
     * 分页查询我的订单数据
     * @param page 分页参数
     * @param map 查询参数
     */
    Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map);
}