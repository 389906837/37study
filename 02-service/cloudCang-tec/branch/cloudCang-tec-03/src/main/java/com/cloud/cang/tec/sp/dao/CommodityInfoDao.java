package com.cloud.cang.tec.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityInfo;

/** 商品信息表(SP_COMMODITY_INFO) **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {

    /**
     *  更新商品数据 销售总数 平均销售价 平均成本价
     * @param merchantId 商户ID
     * @return
     */
    Integer updateDataByMerchantId(String merchantId);

}