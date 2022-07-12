package com.cloud.cang.mgr.om.service;

import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.domain.RefundCommodityDomain;
import com.cloud.cang.mgr.om.vo.RefundCommodityVo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface RefundCommodityService extends GenericService<RefundCommodity, String> {

    /**
     * 查看退款订单详情
     *
     * @param refundCommodityDomain
     * @return
     */
    Page<RefundCommodityVo> queryDetails(Page<RefundCommodityVo> page, RefundCommodityDomain refundCommodityDomain);
}