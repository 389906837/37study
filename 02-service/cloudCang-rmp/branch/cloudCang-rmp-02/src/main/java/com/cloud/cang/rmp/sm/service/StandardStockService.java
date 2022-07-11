package com.cloud.cang.rmp.sm.service;

import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.generic.GenericService;

public interface StandardStockService extends GenericService<StandardStock, String> {


    /**
     * 获取设备标准库存配置
     * @param sdeviceId 设备ID
     * @return
     */
    StandardStock selectBySdeviceId(String sdeviceId);
}