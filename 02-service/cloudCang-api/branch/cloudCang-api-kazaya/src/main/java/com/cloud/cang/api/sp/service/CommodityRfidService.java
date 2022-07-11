package com.cloud.cang.api.sp.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sp.CommodityRfid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityRfidService extends GenericService<CommodityRfid, String> {

    /** codegen **/
    List<CommodityRfid> commdityRfidList(@Param("smerchantId")String smerchantId,
                                         @Param("rfids")List<String> rfids);

    void batchDeleteByRfids(@Param("rfids")List<String> rfids);
}