package com.cloud.cang.api.sp.dao;



import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品信息表(SP_COMMODITY_INFO)
 **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {


    /***
     * 查询商品信息
     * @param svrCode 视觉识别编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByVrCode(@Param("svrCode") String svrCode, @Param("smerchantId") String smerchantId);

    List<CommodityInfo> selectCommodityList(@Param("smerchantId") String smerchantId,
                                            @Param("scode") String scode,
                                            @Param("sname") String sname);
}