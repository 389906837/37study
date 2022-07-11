package com.cloud.cang.wap.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.wap.om.vo.CommodityDomain;

/** 退款订单审核商品明细表(OM_REFUND_COMMODITY) **/
public interface RefundCommodityDao extends GenericDao<RefundCommodity, String> {

    /***
     * 获取审核订单商品明细
     * @param refundCode 审核订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String refundCode);


}