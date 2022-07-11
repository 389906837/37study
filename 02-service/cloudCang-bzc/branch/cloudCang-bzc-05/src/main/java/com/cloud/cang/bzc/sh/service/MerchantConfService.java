package com.cloud.cang.bzc.sh.service;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.generic.GenericService;

public interface MerchantConfService extends GenericService<MerchantConf, String> {

    /**
     * 根据商户Id,配置信息类型查询
     * @param
     * @return
     */
    MerchantConf selectByIdType(MerchantConf merchantConf);
}