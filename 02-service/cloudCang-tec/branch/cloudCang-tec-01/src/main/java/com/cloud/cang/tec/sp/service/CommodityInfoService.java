package com.cloud.cang.tec.sp.service;

import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {

    /**
     *  更新商品数据 销售总数 平均销售价 平均成本价
     * @param merchantId 商户ID
     * @return
     */
    int updateDataByMerchantId(String merchantId);
}