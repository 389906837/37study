package com.cloud.cang.mgr.vr.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.vr.MerchantSku;

/**
 * 商户SKU库(VR_MERCHANT_SKU)
 **/
public interface MerchantSkuDao extends GenericDao<MerchantSku, String> {

    /**
     * 根据视觉商品ID删除对应记录
     * @param id
     */
    void deleteByVrCommodityId(String id);
    /**
     * 删除商户视觉SKU
     * @param merchantId 商户ID
     */
    void deleteByVrMerchantId(String merchantId);

    /** codegen **/
}