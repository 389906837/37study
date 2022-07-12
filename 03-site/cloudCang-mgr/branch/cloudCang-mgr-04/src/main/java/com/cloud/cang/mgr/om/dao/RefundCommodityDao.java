package com.cloud.cang.mgr.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.domain.RefundCommodityDomain;
import com.cloud.cang.mgr.om.vo.RefundCommodityVo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.RefundCommodity;
import com.github.pagehelper.Page;

/**
 * 退款订单审核商品明细表(OM_REFUND_COMMODITY)
 **/
public interface RefundCommodityDao extends GenericDao<RefundCommodity, String> {


    Page<RefundCommodityVo> queryDetailsByDomain(RefundCommodityDomain refundCommodityDomain);
}