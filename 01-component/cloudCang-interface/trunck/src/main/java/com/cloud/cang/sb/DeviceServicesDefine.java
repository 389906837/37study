package com.cloud.cang.sb;

import com.cloud.cang.common.ResponseVo;

/**
 * 发送消息给客户端接口名称
 *
 * @version 1.0
 * @Author: zengzexiong
 * @Date: 2018年1月30日15:05:36
 */
public class DeviceServicesDefine {
    /**
     * 发送消息给客户端
     * 请求参数：{@link DeviceDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String OPERATE_DEVICE = "com.cloud.cang.api.ws.DeviceService.operateDevice";


    /**
     * 将连接的设备设置为离线状态
     * 断开设备与netty服务器的连接
     * 从ctxMap中将该设备移除
     * 请求参数：{@link SetDeviceOfflineDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String SET_DEVICE_OFFLINE = "com.cloud.cang.api.ws.DeviceService.setDeviceOffline";


    /**
     * 获取离线设备
     * 请求参数：{@link OfflineDeviceDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo}
     */
    public static final String GET_OFFLINE_DEVICE = "com.cloud.cang.api.ws.DeviceService.getOfflineDevice";

    /**
     * 获取盘货结果
     * 请求参数：{@link InventoryDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String GET_INVENTORY_RESULT = "com.cloud.cang.api.ws.DeviceService.getInventoryResult";

    /**
     * 发送主动盘货消息
     * 请求参数：{@link InventoryDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ACTIVE_INVENTORY = "com.cloud.cang.api.ws.DeviceService.activeInventory";

    /**
     * 发送广告服务
     * 请求参数：{@link SendOperatingAdvertisingDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String SEND_OPERATING_ADVERTISING = "com.cloud.cang.api.ws.DeviceService.sendOperatingAdvertising";

    /***
     * 更新广告
     * 请求参数：{@link UpdateDeviceAdvertisDto}
     * 返回参数：{@link ResponseVo < String >}
     */
    public static final String UPDATE_DEVICE_ADVERTIS = "com.cloud.cang.api.ws.DeviceService.updateDeviceAdvertis";

    /**
     * 获取连接到netty服务器但是没有注册的结果
     * 请求参数：{@link TcpDto}
     * 返回参数：{@link ResponseVo<TcpResult> }
     */
    public static final String GET_CONNECTED_TCP = "com.cloud.cang.api.ws.DeviceService.getConnectedTcp";


    /**
     * 获取注册到netty服务器的TCP结果
     * 请求参数：{@link TcpDto}
     * 返回参数：{@link ResponseVo<TcpResult> }
     */
    public static final String GET_REGISTER_TCP = "com.cloud.cang.api.ws.DeviceService.getRegisterTcp";

    /**
     * 断开连接但是未注册的TCP
     * 请求参数：{@link UnRegisterTcpDto}
     * 返回参数：{@link ResponseVo<String> }
     */
    public static final String DISCONNECT_UNREGISTER_TCP = "com.cloud.cang.api.ws.DeviceService.disconnectUnregisterTcp";


    /**
     * 断开已经连接并且注册的TCP
     * 请求参数：{@link RegisterTcpDto}
     * 返回参数：{@link ResponseVo<String> }
     */
    public static final String DISCONNECT_REGISTER_TCP = "com.cloud.cang.api.ws.DeviceService.disconnectRegisterTcp";


    /**
     * 断开已经连接并且注册的TCP
     * 请求参数：{@link AuthorizeAiFaceDto}
     * 返回参数：{@link ResponseVo<String> }
     */
    public static final String SCAN_CODE_AUTHORIZE_AI_FACE = "com.cloud.cang.api.ws.AiFaceService.scanCodeAuthorizeAiFace";

    /**
     * 云端设备 配置参数调整
     * 请求参数：{@link CloudParamConfigDto}
     * 返回参数：{@link ResponseVo<String> }
     */
    public static final String CLOUD_DEVICE_PARAM_CONFIG = "com.cloud.cang.api.ws.DeviceService.cloudDeviceParamConfig";

    /**
     * 发送消息给客户端
     * 请求参数：{@link DeviceOperatingDto<Object>}
     * 返回参数：ResponseVo<String>
     */
    public static final String DEVICE_OPERATING = "com.cloud.cang.api.ws.DeviceService.deviceOperating";

}
