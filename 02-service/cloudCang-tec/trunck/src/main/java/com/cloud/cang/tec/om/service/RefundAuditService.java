package com.cloud.cang.tec.om.service;

import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.generic.GenericService;

import java.math.BigDecimal;

public interface RefundAuditService extends GenericService<RefundAudit, String> {

    /**
     * 查询昨日退款金额
     *
     * @param merchantId
     * @param itype      支付方式
     */
    BigDecimal selectYesTodayRefundMoney(String merchantId, Integer itype);

}