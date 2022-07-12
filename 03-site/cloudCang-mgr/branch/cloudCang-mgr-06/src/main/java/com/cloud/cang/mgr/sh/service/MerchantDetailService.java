package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.model.sh.MerchantDetail;
import com.cloud.cang.generic.GenericService;

public interface MerchantDetailService extends GenericService<MerchantDetail, String> {
    /**
     * 新增商户详情表
     * @param
     * @return
     */
    void insertAll(MerchantDetail merchantDetail);
}