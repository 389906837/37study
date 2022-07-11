package com.cloud.cang.bzc.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sh.MerchantConf;

/** 商户支付、公众号配置信息(SH_MERCHANT_CONF) **/
public interface MerchantConfDao extends GenericDao<MerchantConf, String> {

    /**
     * 根据商户Id,配置信息类型查询
     * @param
     * @return
     */
    MerchantConf selectByIdType(MerchantConf merchantConf);
}