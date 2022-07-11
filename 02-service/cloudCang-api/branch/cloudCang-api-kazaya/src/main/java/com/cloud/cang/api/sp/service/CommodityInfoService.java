package com.cloud.cang.api.sp.service;

import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {

    /***
     * 查询商品信息
     * @param svrCode 视觉识别编号
     * @param smerchantId 商户编号
     */
    CommodityInfo selectByVrCode(String svrCode, String smerchantId);

    List<CommodityInfo> selectCommodityList(String smerchantId,String scode,String sname);
}