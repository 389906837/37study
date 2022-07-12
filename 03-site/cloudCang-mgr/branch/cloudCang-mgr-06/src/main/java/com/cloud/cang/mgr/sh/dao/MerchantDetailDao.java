package com.cloud.cang.mgr.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sh.MerchantDetail;
import com.cloud.cang.model.sh.MerchantInfo;

/** 商户基础详细信息表(SH_MERCHANT_DETAIL) **/
public interface MerchantDetailDao extends GenericDao<MerchantDetail, String> {


    /**
     * 新增商户商户商户基础详细信息表
     * @param
     * @return
     */
    void insertAll(MerchantDetail merchantDetail );
}