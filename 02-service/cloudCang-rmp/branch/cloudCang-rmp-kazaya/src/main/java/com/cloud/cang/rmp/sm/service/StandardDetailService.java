package com.cloud.cang.rmp.sm.service;

import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rmp.sm.vo.StandardStockVo;

import java.util.List;

public interface StandardDetailService extends GenericService<StandardDetail, String> {

    /**
     *  查询设备标准库存商品明细
     * @param sdeviceId 设备编号
     * @return
     */
    List<StandardStockVo> selectStandardStockByDeviceId(String sdeviceId);
}