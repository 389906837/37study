package com.cloud.cang.tec.sm.service;

import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface StockOperRecordService extends GenericService<StockOperRecord, String> {
    /**
     * 查询昨日库存变化数(销售数，补货数)
     *
     * @param merchantId 商户ID
     * @return Map
     */
    Map selectYesterdayStockChangeAndReplenishmentNum(String merchantId);
}