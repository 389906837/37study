package com.cloud.cang.tec.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.RefundAudit;
import org.apache.ibatis.annotations.Param;

/**
 * 商品订单退款审核记录信息表(OM_REFUND_AUDIT)
 **/
public interface RefundAuditDao extends GenericDao<RefundAudit, String> {


    /**
     * 查询昨日退款金额
     *
     * @param merchantId
     * @param itype      支付方式
     */
    BigDecimal selectYesTodayRefundMoney(@Param("merchantId") String merchantId, @Param("itype") Integer itype);
}