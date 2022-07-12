package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.model.sh.MerchantAttach;
import com.cloud.cang.generic.GenericService;

public interface MerchantAttachService extends GenericService<MerchantAttach, String> {
    /**
     * 新增商户商户资质附件信息表
     * @param
     * @return
     */
    void insertAll(MerchantAttach merchantAttach);

    /**
     * 删除商户时删除其商户资质附件信息表
     * @param merchantAttach
     */
    void upBymerchantId(MerchantAttach merchantAttach);
}