package com.cloud.cang.tec.sm.service;

import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.sm.vo.ErrorCommodityVo;
import com.cloud.cang.tec.sm.vo.StockDetailVo;

import java.util.List;

public interface StockDetailService extends GenericService<StockDetail, String> {
    /**
     * 商品过期预警 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<StockDetailVo> selectExpired(String merchantId);

    /**
     * 今日过期商品通知 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<StockDetailVo> selectComExpiredPrompt(String merchantId);

    /**
     * 异常商品预警 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<ErrorCommodityVo> selectErrorCommodity(String merchantId);

    /**
     * 商品过期状态变更 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<StockDetail> selectExpiredCommodity(String merchantId);


}