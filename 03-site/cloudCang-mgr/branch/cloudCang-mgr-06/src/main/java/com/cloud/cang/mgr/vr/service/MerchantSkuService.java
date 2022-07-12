package com.cloud.cang.mgr.vr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.vr.MerchantSku;
import com.cloud.cang.generic.GenericService;

public interface MerchantSkuService extends GenericService<MerchantSku, String> {


    /**
     * 给商户添加视觉商品
     * @param vrCommodityIds
     * @param merchantId
     */
    ResponseVo<String> insertVrCommodityIds(String vrCommodityIds, String merchantId);


}