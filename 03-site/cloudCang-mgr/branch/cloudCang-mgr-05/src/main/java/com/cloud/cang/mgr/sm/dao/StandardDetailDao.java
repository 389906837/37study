package com.cloud.cang.mgr.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DetailStockDomain;
import com.cloud.cang.model.sm.StandardDetail;

/**
 * 设备标准库存配置明细表(SM_STANDARD_DETAIL)
 **/
public interface StandardDetailDao extends GenericDao<StandardDetail, String> {

    /**
     * 查询设备标准库存异常
     * @param map deviceId 设备ID ; num   货道位置（1,2,3,4,5）
     * @return 设备货道对应商品
     */
    List<DetailStockDomain> selectDetailStandard(Map map);
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