package com.cloud.cang.mgr.sm.service;

import com.cloud.cang.mgr.sb.domain.DetailStockDomain;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface StandardDetailService extends GenericService<StandardDetail, String> {


    /**
     * 查询设备标准库存异常
     * @param deviceId 设备编号
     * @param layerNum 设备层数
     * @return
     */
    List<List<DetailStockDomain>> selectDetailStandard(String deviceId, Integer layerNum);

    /**
     * 查询设备标准库存商品信息
     * @param deviceId 设备Id
     * @return
     */
    List<DetailStockDomain> selectByDeviceId(String deviceId);

    /**
     * 删除设备标准商品明细
     * @param sdeviceId 设备Id
     */
    void deleteByDeviceId(String sdeviceId);
}