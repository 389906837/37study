package com.cloud.cang.api.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityInfo;

/**
 * 商品信息表(SP_COMMODITY_INFO)
 **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {

    /**
     * 根据视觉商品识别码查询商品信息
     *
     * @param vrCode
     * @return
     */
    CommodityInfo selectByVrCode(String vrCode);


    /** codegen **/
}