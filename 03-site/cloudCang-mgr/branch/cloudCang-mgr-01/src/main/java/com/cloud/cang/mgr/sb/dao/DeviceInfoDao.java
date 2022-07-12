package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.SelectDeviceDomain;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 设备基础信息表(SB_DEVICE_INFO)
 **/
public interface DeviceInfoDao extends GenericDao<DeviceInfo, String> {

    /**
     * 分页查询
     * @param deviceInfoVo
     */
    Page<DeviceInfoDomain> selectByDomainWhere(DeviceInfoVo deviceInfoVo);
    /**
     * @Author: zhouhong
     * @Description:  获取商户所有有效设备
     * @param: smerchantId 商户Id
     * @Date: 2018/2/10 19:28
     * @return java.util.List<com.cloud.cang.model.sb.DeviceInfo>
     */
    List<DeviceInfo> selectAllValidDeviceByMerchantId(String smerchantId);
    /**
     * 设备编号获取设备信息
     * @param scode 设备编号
     * @return
     */
    DeviceInfo selectByCode(String scode);


    /**
     * 根据设备ID查询设备信息
     * @param id
     * @return
     */
    DeviceInfoDomain selectDomainByPrimaryKey(String id);

    /**
     * 查询设备信息及分组信息
     *
     * @param deviceId 设备Id
     * @return
     */
    DeviceInfoDomain selectDeviceMessageById(String deviceId);

    /**
     * 自定义修改model
     * @param deviceInfo
     * @return
     */
    int updateByIdSelectiveVo(DeviceInfo deviceInfo);

    /**
     * 子集商户修改设备信息
     * @param deviceInfo
     * @return
     */
    int updateByIdSelectiveVo1(DeviceInfo deviceInfo);

    /**
     * 查询该商户下的所有未删除的设备
     * @param merchantId
     * @return
     */
    List<DeviceInfo> selectAllDeviceInfoByMerchantId(String merchantId);

    /**
     * 根据sdeviceId查询设备名称
     * @param sid
     * @return
     */
    DeviceInfo selectBySid(String sid);

    /**
     * 查询所有设备
     * @param deviceInfoVo
     * @return
     */
    Page<DeviceInfoDomain> selectAllDevice(DeviceInfoVo deviceInfoVo);
}