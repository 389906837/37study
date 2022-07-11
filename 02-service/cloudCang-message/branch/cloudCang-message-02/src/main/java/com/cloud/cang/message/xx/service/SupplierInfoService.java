package com.cloud.cang.message.xx.service;

import com.cloud.cang.model.xx.SupplierInfo;

public interface SupplierInfoService {
 
	 /**
     * 根据Code获取供应商
     * @param code
     * @return
     */
    SupplierInfo getSupplierInfoByCode(String code);
}