package com.cloud.cang.rmp.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.rmp.sm.vo.StandardStockVo;

import java.util.List;

/** 设备标准库存配置明细表(SM_STANDARD_DETAIL) **/
public interface StandardDetailDao extends GenericDao<StandardDetail, String> {

    /**
     * 根据设备查询 设备标准库存明细
     * @param sdeviceId 设备编号
     * @return
     */
    List<StandardDetail> selectByStandardId(String sdeviceId);
    /**
     *  查询设备标准库存商品明细
     * @param sdeviceId 设备编号
     * @return
     */
    List<StandardStockVo> selectStandardStockByDeviceId(String sdeviceId);
}