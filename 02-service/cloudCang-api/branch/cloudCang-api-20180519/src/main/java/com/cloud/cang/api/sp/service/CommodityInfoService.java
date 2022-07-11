package com.cloud.cang.api.sp.service;

import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {


    CommodityInfo selectByVrCode(String vrCode);

}