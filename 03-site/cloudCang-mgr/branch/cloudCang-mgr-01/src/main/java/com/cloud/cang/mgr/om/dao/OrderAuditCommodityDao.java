package com.cloud.cang.mgr.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderAuditCommodityDomain;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderAuditCommodityVo;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.model.om.OrderCommodity;
import com.github.pagehelper.Page;

/**
 * 审核订单商品明细表(OM_ORDER_AUDIT_COMMODITY)
 **/
public interface OrderAuditCommodityDao extends GenericDao<OrderAuditCommodity, String> {
    Page<OrderAuditCommodityVo> queryDetailsByDomain(OrderAuditCommodityDomain orderAuditCommodityDomain);

    List<OrderAuditCommodityVo> selectOrderAuditDetail(OrderAuditCommodity orderAuditCommodity);

    void delectByOrderId(String orderId);

}