package com.cloud.cang.mgr.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sh.MerchantAttach;

/**
 * 商户资质附件信息表(SH_MERCHANT_ATTACH)
 **/
public interface MerchantAttachDao extends GenericDao<MerchantAttach, String> {

    /**
     * 新增商户资质附件信息
     * @param merchantAttach
     * @return
     */
    void insertAll(MerchantAttach merchantAttach);

    /**
     * 删除商户时删除其商户资质附件信息表
     *
     * @param merchantAttach
     */
    void upBymerchantId(MerchantAttach merchantAttach);
}