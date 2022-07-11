package com.cloud.cang.bzc.sp.service;

import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;
import org.apache.ibatis.annotations.Param;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {

    /***
     * 查询商品信息
     * @param svrCode 视觉识别编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByVrCode(String svrCode, String smerchantId);

    /***
     * 查询商品信息
     * @param scode 商品编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByScode(@Param("scode") String scode, @Param("smerchantId") String smerchantId);
}