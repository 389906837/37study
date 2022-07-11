package com.cloud.cang.api.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.sb.DeviceDto;

import java.util.List;

public interface DeviceInfoService extends GenericService<DeviceInfo, String> {


    /**
     * 给设备发送消息
     * @param deviceDto
     * @return
     */
    ResponseVo<String> operateDevice(DeviceDto deviceDto) throws Exception;



    /**
     * 根据商户ID查询商户下的设备ID
     * @param merchantId 商户ID
     * @return
     */
    List<String> selectDeviceIdByMerchantId(String merchantId);


    /**
     * 查询商户下的设备运营位1的广告信息
     * @param deviceId
     * @return
     */
    ResponseVo<AdListModel> selectOperatingAdOne(String deviceId) throws Exception;


    /**
     * 查询商户下的设备运营位2的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    ResponseVo<AdListModel> selectOperatingAdTwo(String deviceId) throws Exception;


    /**
     * 查询商户下的设备运营位3的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    ResponseVo<AdListModel> selectOperatingAdThree(String deviceId) throws Exception;




}