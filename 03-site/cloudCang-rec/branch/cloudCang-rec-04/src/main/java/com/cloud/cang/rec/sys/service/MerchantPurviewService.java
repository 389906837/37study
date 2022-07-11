package com.cloud.cang.rec.sys.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sys.MerchantPurview;

public interface MerchantPurviewService extends GenericService<MerchantPurview, String> {


    /**
     * 删除商户菜单权限
     * @param merchantId 商户ID
     */
    void deleteByMerchantId(String merchantId);
}