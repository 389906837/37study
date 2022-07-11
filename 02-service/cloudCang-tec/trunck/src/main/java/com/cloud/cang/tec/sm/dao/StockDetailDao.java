package com.cloud.cang.tec.sm.dao;

import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.tec.sm.vo.ErrorCommodityVo;
import com.cloud.cang.tec.sm.vo.StockDetailVo;
import org.springframework.stereotype.Component;

/**
 * 设备实时库存明细(SM_STOCK_DETAIL)
 **/
public interface StockDetailDao extends GenericDao<StockDetail, String> {

    /**
     * 商品过期预警 根据商户ID
     *
     * @param
     */
    List<StockDetailVo> selectExpired(Map<String, Object> map);

    /**
     * 今日过期商品通知 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<StockDetailVo> selectComExpiredPrompt(String merchantId);

    List<ErrorCommodityVo> selectErrorCommodity(String merchantId);

    /**
     * 商品过期状态变更 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<StockDetail> selectExpiredCommodity(String merchantId);
}