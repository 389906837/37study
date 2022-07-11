package com.cloud.cang.core.common;

import com.cloud.cang.core.utils.GrpParaUtil;

/**
 * Netty常量
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年3月15日11:15:33
 */
public class NettyConst {

    /**
     * 后台向设备发送消息时的--方法名
     */
    public static final String VOLUME = "volume";                         //音量
    public static final String INVENTORY = "inventory";                   //盘货
    public static final String UPGRADEVOICE = "upgradeVoice";             //升级语音
    public static final String SYSTEMUPGRADE = "upgradeSystem";           //系统升级
    public static final String VR_SERVER_UPGRADE = "vrServerUpgrade";     // 视觉服务更新
    public static final String UPDATE_FEATURE_LIBRARY = "updateFeatureLib";   //视觉库更新
    public static final String REBOOT = "timingBoot";                         //重启 ，吕威威特别要求使用timingBoot作为重启方法名
    public static final String SHUTDOWN = "shutdown";//关机
    public static final String TIMINGBOOT = "timingBoot1";//定时开机 （废弃）
    public static final String TIMINGSHUTDOWN = "timingShutdown";//定时关机
    public static final String DISABLED = "disabled";//停用
    public static final String OPENDOOR = "openDoor";//开门
    public static final String CLOSEDOOR = "closeDoor";//关门
    public static final String OFFLINE = "offline";//离线
    public static final String QR_CODE = "qrCode";//发送二维码
    public static final String OPERATING_AD_ONE = "operatingAdOne";//运营位1广告
    public static final String OPERATING_AD_TWO = "operatingAdTwo";//运营位2广告
    public static final String OPERATING_AD_THREE = "operatingAdThree";//运营位3广告

    /**
     * 后台向设备发送消息时的--附属参数
     */
    public static final String VOLUME_VALUE = "volumeValue";//音量大小
    public static final String TIMINGBOOT_VALUE = "timingBootValue";//定时关机时间

    /**
     * 后台向设备发送消息时的--方法名对应的参数
     */
    public static final int ONDOOR_TYPE = 1;//开门
    public static final int OFFDOOR_TYPE = 2;//关门
    public static final int STOCKTAKING_TYPE = 3;//盘货
    public static final int ONDEVICE_TYPE = 4;//开机
    public static final int OFFDEVICE_TYPE = 5;//关机
    public static final int TIMINGDEVICE_TYPE = 6;//定时开关机
    public static final int VOLUME_TYPE = 7;//调节音量
    public static final int UPGRADEVOICE_TYPE = 8;//升级语音
    public static final int SYSTEMUPGRADE_TYPE = 9;//系统升级
    public static final int REBOOT_TYPE = 10;//重启


    /**
     * 设备心跳
     */
    public static final String HEARTBEAT = "heartbeat";//心跳

    public static final String DEVICE_ID = "deviceId";                //设备id
    public static final String KEY = "key";                             //安全密钥
    public static final String CODE = "code";                           //返回码
    public static final String SUCCESS = "success";                     //成功返回"0"，失败返回原因
    public static final String FAIL = "fail";                           //失败
    public static final String TYPE = "type";                     //成功返回"0"，失败返回原因

    /**
     * 广告位置
     */
    public static final String AD_ONE = "adOne";                     // 广告位1
    public static final String AD_TWO = "adTwo";                     // 广告位2
    public static final String AD_THREE = "adThree";                 // 广告位3
    public static final String AD_ONE_NUMBER = GrpParaUtil.getValue(GrpParaUtil.OPERATE_ADVERTISING_CONFIG, "adOne");    // 广告位1数据编号
    public static final String AD_TWO_NUMBER = GrpParaUtil.getValue(GrpParaUtil.OPERATE_ADVERTISING_CONFIG, "adTwo");    // 广告位2数据编号
    public static final String AD_THREE_NUMBER = GrpParaUtil.getValue(GrpParaUtil.OPERATE_ADVERTISING_CONFIG, "adThree");  // 广告位3数据编号


    /**
     * 设备状态
     */
    public static final String OPEN = "open";   //开
    public static final String CLOSE = "close";//关
    public static final String FTP_IMAGE_PATH = "ftpImagePath";


    /**
     * Redis --> 长连接主键
     */
    public static final String OPENDOOR_USER_ID = "openDoorUserId";             // 开门人ID
    public static final String SYN_CLIENT_MAP = "synClientMap";                 // 自定义设备信息
    public static final String CLIENT_HEART = "clientHeart";                    // 售货机心跳检测主键
    public static final String FACE_HEART = "faceHeart";                        // 人脸识别设备心跳检测主键
    public static final String FACE_REGISTER_QR_CODE = "faceRegisterQrCode";    // 人脸识别设备注册二维码
    public static final String FACE_REGISTER_TOKEN = "faceRegisterToken";       // 人脸识别设备注册二维码token值范围(有效="valid"，取消="cancel"，失效="invalid"||"")
    public static final String FACE_REGISTER_QRCODE_ISSCAN = "faceRegisterQrCodeIsScan";       // 人脸识别设备注册二维码是否被扫
    public static final String FACE_DEVICE_VO = "faceDeviceVo";                 // 人脸识别设备基础信息
    public static final String DEVICE_COMMODITYS = "deviceCommoditys";          // 设备商品信息
    public static final String DEVICE_INFO = "deviceInfo";                      // 设备基础信息
    public static final String DEVICE_REGISTER = "deviceRegister";              // 设备注册信息
    public static final String DEVICE_MODEL = "deviceModel";                    // 设备详细信息
    public static final String DEVICE_MONITOR_DATACONF = "MonitorDataConf";     // 设备监控信息
    public static final String THIRD_USER_ID = "thirdUserId";                   // 第三方设备开门用户ID
    public static final String CLOUD_DEVICE_OPEN_DOOR_COMMODITYS = "cloudDeviceOpenDoorCommoditys";                   // 云识别设备商品开门记录商品主键ID
    public static final String LAYERED_CLOUD_DEVICE_OPEN_DOOR_COMMODITYS = "LayeredcloudDeviceOpenDoorCommoditys";    // 设备分层商品开门记录商品
    public static final String LAYERED_CLOUD_DEVICE_REPLEN_OPEN_DOOR_COMMODITYS = "LayeredcloudDeviceReplenOpenDoorCommoditys";    // 设备分层商品补货开门记录商品
    public static final String DEVICE_ACTIVE_INVENTORY_COMMODITY = "deviceActiveInventoryCommodity";                  // 设备主动盘点返回商品集合结果主键ID


    /**
     * SocketIO--消息事件
     */
    public static final String SOCKETIO_EVENT = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "socketIoEvent");    //数据字典配置--socketIo事件

    // 库存商品为0
    public static final String DEVICE_STOCK_IS_ZERO = "deviceStockIsZero";                  // 设备库存为零


    /**
     * vendstop
     */
    //事件名称(用户监听端事件,必须与客户端事件名称保持一致)
    public static final String EVENT_CONNECTION = "event_connection";
    public static final String EVENT_OPEN_DOOR = "event_open_door";
    public static final String EVENT_CLOSE_DOOR = "event_close_door";
    //缓存key
    public static final String CACHE_USER_KEY = "soketIo_user_session_";
}
