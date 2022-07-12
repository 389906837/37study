package com.cloud.cang.mgr.sys.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.MerchantPurview;

/** 后台权限商户表(SYS_MERCHANT_PURVIEW) **/
public interface MerchantPurviewDao extends GenericDao<MerchantPurview, String> {

    /**
     * 删除商户菜单权限
     * @param merchantId 商户ID
     */
    void deleteByMerchantId(String merchantId);

}