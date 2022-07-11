package com.cloud.cang.bzc.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sh.MerchantInfo;

/** 商户基础信息表(SH_MERCHANT_INFO) **/
public interface MerchantInfoDao extends GenericDao<MerchantInfo, String> {

    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
}