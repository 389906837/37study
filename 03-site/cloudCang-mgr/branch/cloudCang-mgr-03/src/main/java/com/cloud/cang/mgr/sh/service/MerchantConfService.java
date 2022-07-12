package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.mgr.sh.domain.MerchantConfDomain;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.generic.GenericService;

public interface MerchantConfService extends GenericService<MerchantConf, String> {
    /**
     * 根据商户Id,配置信息类型查询
     *
     * @param
     * @return
     */
    MerchantConf selectByIdType(MerchantConf merchantConf);

    /**
     * 商户 添加 支付宝/微信 配置
     *
     * @param
     * @return
     */
    void insertMerchantDomain(MerchantConfDomain merchantConfDomain);

    /**
     * 商户 修改 支付宝/微信 配置
     *
     * @param
     * @return
     */
    void updateByDomain(MerchantConfDomain merchantConfDomain);

    /**
     * 根据商户Id删除商户配置表
     *
     * @param
     * @return
     */
    void upBySmerchantId(MerchantConf merchantConf);

}