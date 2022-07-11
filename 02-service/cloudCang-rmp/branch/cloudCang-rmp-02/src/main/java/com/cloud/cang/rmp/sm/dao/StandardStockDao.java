package com.cloud.cang.rmp.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.StandardStock;

import java.util.Map;

/** 设备标准库存配置信息表(SM_STANDARD_STOCK) **/
public interface StandardStockDao extends GenericDao<StandardStock, String> {

    /**
     * 获取标准库存数据
     * @param sdeviceId
     * @return
     */
    StandardStock selectBySdeviceId(String sdeviceId);
}