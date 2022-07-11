package com.cloud.cang.tec.xx.service;

import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface SupplierInfoService extends GenericService<SupplierInfo, String> {
 
    List<SupplierInfo> selectByMerchantId(String merchantId);
}