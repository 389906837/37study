package com.cloud.cang.bzc.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityInfo;
import org.apache.ibatis.annotations.Param;

/** 商品信息表(SP_COMMODITY_INFO) **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {

    /***
     * 查询商品信息
     * @param svrCode 视觉识别编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByVrCode(@Param("svrCode") String svrCode, @Param("smerchantId") String smerchantId);

    /***
     * 查询商品信息
     * @param scode 商品编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByScode(@Param("scode") String scode, @Param("smerchantId") String smerchantId);


}