package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.DeviceInventoryStockDomain;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.cloud.cang.sb.TcpVo;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public interface DeviceInfoService extends GenericService<DeviceInfo, String> {

    /**
     * 根据ID集合删除设备信息
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 分页查询
     *
     * @param page
     * @param deviceInfoVo
     * @return
     */
    Page<DeviceInfoDomain> selectPageByDomainWhere(Page<DeviceInfoDomain> page, DeviceInfoVo deviceInfoVo);

    /**
     * 长连接批量操作设备
     *
     * @param checkboxId 设备ID集合
     * @param methodType 操作类型
     * @return
     */
    ResponseVo<String> operate(String[] checkboxId, String methodType);


    /**
     * @return java.util.List<com.cloud.cang.model.sb.DeviceInfo>
     * @Author: zhouhong
     * @Description: 获取商户所有有效设备
     * @param: smerchantId 商户Id
     * @Date: 2018/2/10 19:28
     */
    List<DeviceInfo> selectAllValidDeviceByMerchantId(String smerchantId);
    /**
     * @return java.util.List<com.cloud.cang.model.sb.DeviceInfo>
     * @Author: zhouhong
     * @Description: 获取商户有效设备
     * @param: smerchantId 商户Id
     * @Date: 2018/2/10 19:28
     */
    List<DeviceInfo> selectAvailable(String smerchantId,String queryCondition);

    /**
     * 设备编号获取设备信息
     *
     * @param scode 设备编号
     * @return
     */
    DeviceInfo selectByCode(String scode);

    /**
     * 根据批量ID查询设备信息
     *
     * @param deviceArray
     * @return
     */
    List<DeviceInfoDomain> selectBykeys(String[] deviceArray);

    /**
     * 长连接批量操作设备
     *
     * @param checkboxId    设备ID集合
     * @param method        操作类型
     * @param operateParams 操作参数
     * @return
     */
    ResponseVo<String> operate(String[] checkboxId, String method, String operateParams);

    /**
     * 添加设备信息
     *
     * @param deviceInfo      设备基础信息表
     * @param deviceManage    设备管理信息表
     * @param deviceModel     设备详细信息表
     * @param monitorDataConf 设备配置信息表
     * @param request         其他参数
     * @param readerID        读写器
     * @param merchantId      商户ID
     */
    ResponseVo<DeviceInfo> insertDeviceInfo(DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request, String readerID, String merchantId);


    /**

     */
    /**
     * 修改设备信息
     *
     * @param oldDeviceData   修改前设备基础信息
     * @param deviceInfo      设备基础信息表
     * @param deviceManage    设备管理信息表
     * @param deviceModel     设备详细信息表
     * @param monitorDataConf 设备配置信息表
     * @param request         其他参数
     * @return
     */
    ResponseVo<DeviceInfo> updateInfo(DeviceInfo oldDeviceData, DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request) throws Exception;


    /**
     * 查询设备信息
     *
     * @param sid
     * @return
     */
    DeviceInfoDomain selectBydeviceId(String sid);

    /**
     * 查询设备信息及分组信息
     *
     * @param deviceId 设备Id
     * @return
     */
    DeviceInfoDomain selectDeviceMessageById(String deviceId);


    /**
     * @param checkboxId
     */
    ResponseVo updateDeviceStatus(String[] checkboxId);


    /**
     * 向设备发送二维码
     *
     * @param id
     * @param sqrUrl
     */
    void sendQrCode(String id, String sqrUrl);

    /**
     * 运营广告位1
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdOne(String deviceIds, String userId, String merchantCode);

    /**
     * 运营广告位2
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdTwo(String deviceIds, String userId, String merchantCode);

    /**
     * 运营广告位3
     *
     * @param deviceIds
     * @param userId
     * @param merchantCode
     * @return
     */
    ResponseVo<String> sendAdThree(String deviceIds, String userId, String merchantCode);


    /**
     * 启用/禁用设备运营状态
     *
     * @param checkboxId 设备ID
     * @param status     执行操作----启用/禁用
     * @return
     */
    ResponseVo<String> updateDeviceOperatingStatus(String[] checkboxId, String status);


    /**
     * 子集商户修改设备信息
     * 名称，分组，地址
     *
     * @param deviceInfo
     * @param deviceManage
     * @param request      @return
     */
    ResponseVo<DeviceInfo> updateInfo(DeviceInfo deviceInfo, DeviceManage deviceManage, HttpServletRequest request);

    /**
     * @param merchantId
     * @return
     */
    List<DeviceInfo> selectAllDeviceInfoByMerchantId(String merchantId);

    /**
     * 检查选中设备是否归属于该商户
     *
     * @param array
     * @param merchantId
     * @return
     */
    ResponseVo<String> checkDeviceAttribution(String[] array, String merchantId);

    /**
     * 查询所有未注册的TCP连接
     *
     * @return
     */
    List<TcpVo> selectUnRegistered();


    /**
     * 查询所有已经注册的TCP连接
     *
     * @return
     */
    List<TcpVo> selectRegistered();


    /**
     * 根据通道channelId断开TCP连接
     *
     * @param channelId
     * @return
     */
    ResponseVo<String> disconnetTcp(String channelId);

    /**
     * 根据设备ID断开已经注册的TCP连接
     *
     * @param deviceId
     * @return
     */
    ResponseVo<String> disconnectRegisterTcp(String deviceId);

    /**
     * 根据设备故障的设备ID查询设备信息表的设备名称
     *
     * @param sid
     * @return
     */
    DeviceInfo selectBySid(String sid);

    /**
     * 查询所有设备(故障:选择设备)
     *
     * @param page
     * @param deviceInfoVo
     * @return
     */
    Page<DeviceInfoDomain> selectAllDevice(Page<DeviceInfoDomain> page, DeviceInfoVo deviceInfoVo);

    /**
     * 记录升级日志
     * 发送升级消息
     *
     * @param ids
     * @param timeType
     * @param version
     * @param dproduceDate
     * @param user
     * @param irangeDevice
     * @param apkFile
     * @return
     */
    ResponseVo recordAndSendApkUpgradeMsg(String[] ids, String timeType, String version, String dproduceDate, String user, String irangeDevice, File  apkFile, Integer upFileType, String localAddress);


    /**
     * 服务更新
     *
     * @param ids
     * @param timeType
     * @param url
     * @param version
     * @param dproduceDate
     * @param user
     * @param irangeDevice
     * @return
     */
    ResponseVo recordAndSendOsServiceUpgradeMsg(String[] ids, String timeType, String url, String version, String dproduceDate, String user, String irangeDevice);


    /**
     * 视觉库更新
     *
     * @param ids
     * @param timeType
     * @param url
     * @param version
     * @param dproduceDate
     * @param user
     * @param irangeDevice
     * @return
     */
    ResponseVo recordAndSendVrOsFeatureLibUpgradeMsg(String[] ids, String timeType, String url, String version, String dproduceDate, String user, String irangeDevice, Integer upFileType, File vrFile);


    /**
     * 盘货
     *
     * @param deviceId 设备ID
     * @return
     */
    ResponseVo<String> inventory(String deviceId);

    /**
     * 语音升级
     *
     * @param file         音频文件
     * @param voiceType    语音类型： 10 开门 20 购物 30 关门
     * @param deviceIds    设备IDs
     * @param irangeDevice 设备：10全部 20部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes
     * @param merchantCode
     * @return
     */
    ResponseVo upgradeVoice(MultipartFile file, String voiceType, String deviceIds, String irangeDevice, String merchantId, String user, String deviceCodes, String merchantCode);

    /**
     * 调节音量值大小
     *
     * @param volumeValue  音量值
     * @param deviceIds    设备ID
     * @param irangeDevice 设备：10全部 20部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes
     * @param merchantCode
     * @return
     */
    ResponseVo<String> adjustVolume(String volumeValue, String deviceIds, String irangeDevice, String merchantId, String user, String deviceCodes, String merchantCode);

    /**
     * 重启设备
     *
     * @param deviceIds    设备ID
     * @param irangeDevice 设备范围：10 全部 20 部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes  设备编号
     * @param merchantCode 商户编号
     */
    ResponseVo<String> reboot(String deviceIds, String irangeDevice, String merchantId, String user, String deviceCodes, String merchantCode);


    /**
     * 从Redis中获取设备库存信息
     *
     * @param deviceId 设备ID
     * @return
     */
    ResponseVo<List<DeviceInventoryStockDomain>> getDeviceStock(String deviceId) throws Exception;


    /**
     * 保存修改后的库存信息
     *
     * @param deviceId     设备ID
     * @param commodityIds 商品IDs
     * @param request      请求
     * @return
     */
    ResponseVo<String> saveActiveInventoryStockInfo(String deviceId, String commodityIds, HttpServletRequest request);


    /**
     * 关机
     *
     * @param deviceIds    设备ID
     * @param deviceCodes  设备编号
     * @param irangeDevice 设备范围：10 全部 20 部分
     * @param merchantId   商户ID
     * @param merchantCode 商户编号
     * @param user         操作员
     * @return
     */
    ResponseVo<String> shutdown(String deviceIds, String deviceCodes, String irangeDevice, String merchantId, String merchantCode, String user);

    /**
     * 批量修改云端柜子实时盘货购物车开关
     *
     * @param merchantId             商户ID
     * @param merchantCode           商户编号
     * @param shoppingInventory      购物开关
     * @param replenishmentInventory 补货开关
     * @param deviceIds              设备ID
     * @param deviceCodes            设备编号
     * @param deviceType             操作设备范围：10=全部，20=部分
     * @return
     */
    ResponseVo<String> batchRealTimeShoppingCartSwitch(String merchantId, String merchantCode, String shoppingInventory, String replenishmentInventory, String deviceIds, String deviceCodes, String deviceType);

}