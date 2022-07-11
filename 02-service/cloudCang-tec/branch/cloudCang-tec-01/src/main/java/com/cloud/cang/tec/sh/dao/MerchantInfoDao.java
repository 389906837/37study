package com.cloud.cang.tec.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sh.MerchantInfo;

/**
 * 商户基础信息表(SH_MERCHANT_INFO)
 **/
public interface MerchantInfoDao extends GenericDao<MerchantInfo, String> {


    /**
     * 商户签约到期状态变更定时任务
     *
     * @param merchantId 商户ID
     */
    List<MerchantInfo> selectExpired(String merchantId);
}