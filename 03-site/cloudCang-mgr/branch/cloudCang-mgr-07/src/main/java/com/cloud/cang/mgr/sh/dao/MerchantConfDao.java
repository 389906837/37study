package com.cloud.cang.mgr.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sh.domain.MerchantConfDomain;
import com.cloud.cang.model.sh.MerchantConf;

/**
 * 商户支付、公众号配置信息(SH_MERCHANT_CONF)
 **/
public interface MerchantConfDao extends GenericDao<MerchantConf, String> {


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
     * @param
     * @return
     */
    void upBySmerchantId(MerchantConf merchantConf);
}