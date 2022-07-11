package com.cloud.cang.pay.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderRecord;

/** 商品订单记录信息表(OM_ORDER_RECORD) **/
public interface OrderRecordDao extends GenericDao<OrderRecord, String> {
    /**
     * 获取订单信息
     * @param orderCode 订单编号
     * @return
     */
    OrderRecord selectByOrderCode(String orderCode);

    /**
     * 更新订单状态
     * @param pmap 参数集合
     * @return
     */
    Integer updateStatusByOrderId(Map<String, Object> pmap);
}