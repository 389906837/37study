package com.cloud.cang.wap.om.service;

import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.om.vo.CommodityDomain;
import com.cloud.cang.wap.om.vo.RefundCommodityVo;

import java.util.List;
import java.util.Map;

public interface RefundCommodityService extends GenericService<RefundCommodity, String> {

    /***
     * 获取审核订单商品明细
     * @param refundCode 审核订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String refundCode);

    /**
     * 查询退款审核订单商品明细
     * @param params 查询参数
     * @return
     */
    List<RefundCommodityVo> selectByMap(Map<String, Object> params);
}