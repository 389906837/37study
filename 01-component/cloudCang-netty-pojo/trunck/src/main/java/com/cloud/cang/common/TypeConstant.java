package com.cloud.cang.common;

/**
 * @version 1.0
 * @Description: 方法名常量
 * @Author: zengzexiong
 * @Date: 2018年4月2日15:36:50
 */
public class TypeConstant extends SuperDto {

    /**
     * 后台向设备发送消息时的--方法名
     */
    public static final String VOLUME = "volume";                                 //音量
    public static final String INVENTORY = "inventory";                           //盘货
    public static final String UPGRADE_VOICE = "upgradeVoice";                    //升级语音
    public static final String UPGRADE_SYSTEM = "upgradeSystem";                  //系统升级
    public static final String REBOOT = "reboot";                                 //重启
    public static final String BOOT = "boot";                                     //开机
    public static final String SHUTDOWN = "shutdown";                             //关机
    public static final String TIMINGBOOT = "timingBoot";                         //定时开机
    public static final String TIMINGSHUTDOWN = "timingShutdown";                 //定时关机
    public static final String DISABLED = "disabled";                             //停用
    public static final String OPEN_DOOR = "openDoor";                            //开门
    public static final String CLOSE_DOOR = "closeDoor";                        //关门
    public static final String OPEN_DOOR_INVENTORY = "openDoorInventory";       //开门状态盘货
    public static final String REPLEN_OPEN_DOOR_INVENTORY = "ReplenOpenDoorInventory"; //补货开门状态盘货
    public static final String REPLENISH_OPEN_DOOR_INVENTORY = "replenishOpenDoorInventory";       //开门状态盘货
    public static final String PAY_SUCCESS = "paySuccess";                      //支付成功
    public static final String PAY_FAIL = "payFail";                            //支付失败
    public static final String QR_CODE = "qrCode";                                //发送二维码
    public static final String OPERATING_AD_ONE = "operatingAdOne";               //运营位1广告
    public static final String OPERATING_AD_TWO = "operatingAdTwo";               //运营位2广告
    public static final String OPERATING_AD_THREE = "operatingAdThree";           //运营位3广告
    // 设备广告更新
    public static final String UPDATE_ADVERTIS = "updateAdvertis";
    public static final String FREE_PAYMENT_ORDER = "freePaymentOrder";           //免密支付订单
    public static final String MANUAL_PAYMENT_ORDER = "manualPaymentOrder";       //手动支付订单
    public static final String IS_ALIVE = "isAlive";                              //连接是否断开
    public static final String CLOSE_DOOR_ORDER = "closeDoorOrder";               //关门盘货订单
    public static final String CLOSE_DOOR_REPLENISH_ORDER_NULL = "closeDoorReplenishOrderNull";       //关门补货订单--没有变化
    public static final String CLOSE_DOOR_REPLENISH_ORDER = "closeDoorReplenishOrder";               //关门补货订单--变化
    public static final String TIMING_INVENTOTY = "timingInventoty";               //关门定时盘货
    public static final String EXCEPTION = "exception";                         // 设备异常
    public static final String WEIGHT_EXCEPTION = "weightException";                         // 称重异常
    public static final String FAULT = "fault";                                 // 设备故障
    public static final String CHECK_TCP = "checkTcp";                          // 校验长连接是否成功
    public static final String SHOPPING_EXCEPTION_ORDER = "shoppingExceptionOrder";  // 购物异常订单
    public static final String LOCAL_ACTIVE_INVENTORY = "localActiveInventory";      // 本地识别主动盘货方法名
    public static final String CLOUD_ACTIVE_INVENTORY = "cloudActiveInventory";      // 云端识别主动盘货方法名
    public static final String CLOUD_PARAM_CONFIG = "cloudParamConfig";      // 云端识别主动盘货方法名
    public static final String CLOUD_REALTIME_JUMP = "cloudRealtimeJump";      // 云端识别 --实时盘货跳转页面


    public static final String OPEN_DISCERN_CHANNEL = "openDiscernChannel";     //开启识别通道
    public static final String CLOSE_DISCERN_CHANNEL = "closeDiscernChannel";   //关闭识别通道
    public static final String UPDATE_FEATURE_LIBRARY = "updateFeatureLib";     //更新特征库
    public static final String VR_SERVER_UPGRADE = "vrServerUpgrade";           //视觉服务更新
    public static final String GET_CHANNEL_STATUS = "getChannelStatus";         //获取某个通道的运行状态
    public static final String GET_VISION_VERSION = "getVisionVersion";         //获取视觉系统版本
    public static final String CLOSE_VISION = "closeVision";                    //关闭视觉功能
    public static final String INIT_VISION_SYSTEM = "initVisionSystem";         //初始化视觉识别系统
    public static final String REINIT_VISION_SYSTEM = "reInitVisionSystem";     //重新初始化视觉识别系统
    public static final String INIT_WEIGHT_SYSTEM = "initWeightSystem";         //初始化称重
    public static final String OPER_WEIGHT_SYSTEM = "operWeightSystem";         //操作称重


    public static final String SUNING_SHED = "shed";                                //苏宁棚格图信息
    public static final String SUNING_OPENDOOR = "suningOpenDoor";                  //苏宁开门
    public static final String SUNING_CLOSEDOOR = "suningCloseDoor";                //苏宁关门
    public static final String SUNING_INVENTORY = "suningInventory";                //苏宁盘货
    public static final String SUNING_REPLENISHMENT = "suningReplenishment";        //苏宁上货


    public static final String TB_INTERFACE_TRANSFER_LOG = "tbInterfaceTransferLog"; //第三方接口调用记录

    //重力开门
    public static final String OPEN_DOOR_CHECK = "openDoorCheck";                              //开门前获得数据
    public static final String WEIGHT_OPEN_DOOR = "weightOpenDoor";                            //开门校验返回结果
    public static final String WEIGHT_REPLEN_OPEN_DOOR = "weightReplenOpenDoor";                            //补货开门校验返回结果
    public static final String WEIGHT_LAYERED_OPEN_DOOR = "weightLayeredOpenDoor";                            //开门分层校验返回结果
    public static final String WEIGHT_LAYERED_REPLEN_OPEN_DOOR = "weightLayeredReplenOpenDoor";                            //补货开门分层校验返回结果
    public static final String LOCAL_GRAVITY_OPEN_DOOR_INVENTORY = "localGravityOpenDoorInventory";      // 本地识别重力视觉柜子开门实时盘货
    public static final String LOCAL_GRAVITY_LAYERED_OPEN_DOOR_INVENTORY = "localGravityLayeredOpenDoorInventory";      // 本地识别重力视觉分层柜子开门实时盘货
    public static final String LOCAL_GRAVITY_LAYERED_REPLEN_OPEN_DOOR_INVENTORY = "localGravityLayeredReplenOpenDoorInventory";      // 本地识别重力视觉分层柜子补货开门实时盘货
    public static final String LOCAL_GRAVITY_CLOSE_DOOR = "local_gravity_close_door";      // 本地识别重力视觉柜子关门
    public static final String LOCAL_GRAVITY_LAYERED_CLOSE_DOOR = "local_gravity_layered_close_door";      // 本地识别重力视觉分层柜子关门


    /**
     * 本地重力视觉柜子方法类型
     */
    public static final String LOCAL_GRAVITY_VISION_BEFORE_OPEN_DOOR_INVENTORY = "localGravityVisionBeforeOpenDoorInventory";           // 本地识别重力视觉柜子开门前盘货
    public static final String LOCAL_GRAVITY_VISION_CLOSE_DOOR_INVENTORY = "localGravityVisionCloseDoorInventory";                      // 本地识别重力视觉柜子关门盘货(异常情况返回也是该方法)
    public static final String LOCAL_GRAVITY_VISION_OPEN_DOOR_INVENTORY = "localGravityVisionOpenDoorInventory";                        // 本地识别重力视觉柜子开门实时盘货
    public static final String LOCAL_GRAVITY_VISION_REPLENISH_OPEN_DOOR_INVENTORY = "localGravityVisionReplenishOpenDoorInventory";     // 本地识别重力视觉柜子补货开门实时盘货

    public static final String LOCAL_GRAVITY_VISION_CLOSE_DOOR_REPLENISH_ORDER = "localGravityVisionCloseDoorReplenishOrder";             //本地重力视觉柜子关门补货订单--变化
    public static final String LOCAL_GRAVITY_VISION_CLOSE_DOOR_REPLENISH_ORDER_NULL = "localGravityVisionCloseDoorReplenishOrderNull";    //本地重力视觉柜子关门补货订单--没有变化
    public static final String LOCAL_GRAVITY_VISION_CLOSE_DOOR_ORDER = "localGravityVisionCloseDoorOrder";                                //本地重力视觉柜子关门盘货订单


    /**
     * 云端重力视觉柜子方法类型
     */
    public static final String CLOUD_GRAVITY_VISION_CLOSE_DOOR_INVENTORY = "cloudGravityVisionCloseDoorInventory";   // 云端识别重力视觉柜子关门盘货
    /**
     * 后台发送测试消息时的--方法名
     */
    public static final String TEST_CONNECTION = "test_connection";

    public TypeConstant() {

    }
}
