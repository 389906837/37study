package com.cloud.cang.api.sp.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.CommodityRfid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 商品标签表(SP_COMMODITY_RFID) **/
public interface CommodityRfidDao extends GenericDao<CommodityRfid, String> {

	/** codegen **/
    List<CommodityRfid> commdityRfidList(@Param("smerchantId")String smerchantId,
                                         @Param("rfids")List<String> rfids);

    void batchDeleteByRfids(@Param("rfids")List<String> rfids);
}