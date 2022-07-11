package com.cloud.cang.wap.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.wap.ac.vo.DeviceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备基础信息表(SB_DEVICE_INFO)
 **/
public interface DeviceInfoDao extends GenericDao<DeviceInfo, String> {
    /**
     * 获取设备信息
     *
     * @param deviceCode 设备编号
     */
    DeviceInfo selectByCode(String deviceCode);

    /**
     * 获取设备信息
     *
     * @param queryCondition 查询条件
     */
    List<DeviceVo> selectUseDevice(@Param("queryCondition") String queryCondition);

}