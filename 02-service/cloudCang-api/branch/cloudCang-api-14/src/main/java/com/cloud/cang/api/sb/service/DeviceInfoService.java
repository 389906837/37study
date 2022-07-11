package com.cloud.cang.api.sb.service;

import com.cloud.cang.api.ws.domain.AndroidConfigInfo;
import com.cloud.cang.api.ws.domain.OpenSdkConfigInfo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.AdListModelTemp;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.sb.DeviceDto;
import com.cloud.cang.sb.UpdateDeviceAdvertisDto;

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
    ResponseVo<AdListModelTemp> selectOperatingAdOne(String deviceId) throws Exception;


    /**
     * 查询商户下的设备运营位2的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    ResponseVo<AdListModelTemp> selectOperatingAdTwo(String deviceId) throws Exception;


    /**
     * 查询商户下的设备运营位3的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    ResponseVo<AdListModelTemp> selectOperatingAdThree(String deviceId) throws Exception;


    /**
     * AI设备获取售货机开门二维码
     *
     * @param deviceId
     * @param key
     * @return
     */
    ResponseVo getOpenDoorQrCode(String deviceId, String key);

    /**
     * 获取年会人脸识别配置信息
     *
     * @return
     */
    ResponseVo<AndroidConfigInfo> getAmFaceConfig();

    /**
     * 获取云端识别柜子openSDK配置信息
     *
     * @return
     * @param deviceId 设备ID
     */
    ResponseVo<OpenSdkConfigInfo> getCloudOpenSDKConfig(String deviceId) throws Exception;


    /***
     * 更新设备广告信息
     * @param deviceDto 更新设备编号集合 运营区域数据
     * @return
     */
    ResponseVo<String> updateDeviceAdvertis(UpdateDeviceAdvertisDto deviceDto) throws ServiceException, Exception;

    /**
     * 查询设备信息
     * @param deviceCode 设备编号
     * @return
     */
    DeviceInfo selectByCode(String deviceCode);

}