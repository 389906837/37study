package com.cloud.cang.rmp.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderAuditCommodity;

/** 审核订单商品明细表(OM_ORDER_AUDIT_COMMODITY) **/
public interface OrderAuditCommodityDao extends GenericDao<OrderAuditCommodity, String> {
    /**
     * 更新审核订单商品信息
     * @param updateMap
     */
    void updateByOrderId(Map<String, Object> updateMap);


    /** codegen **/
}