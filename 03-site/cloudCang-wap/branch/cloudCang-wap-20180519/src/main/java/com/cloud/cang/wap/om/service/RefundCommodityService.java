package com.cloud.cang.wap.om.service;

import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.om.vo.CommodityDomain;

import java.util.List;

public interface RefundCommodityService extends GenericService<RefundCommodity, String> {

    /***
     * 获取审核订单商品明细
     * @param refundCode 审核订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String refundCode);
}