package com.cloud.cang.api.common;

import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 请严格准命名规范
 * ERROR_错误类型 <br />
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年5月7日10:36:41
 */
public enum AndroidErrorCode {

    /* -----------------------1000—9999开始----------------------- */
    ERROR_INVALID_PARAMETER(1001, "调用SDK接口时，参数格式或取值不正确"),
    ERROR_SDK_NOT_INITIALIZED(1002, "调用SDK其他接口之前未调用init初始化"),
    ERROR_SDK_RE_INITIALIZED(1003, "重复调用SDK的init接口"),
    ERROR_TOO_MUCH_RECOGNITION_MODULES(1004, "SDK初始化时传入太多的识别模块或通道参数"),
    ERROR_SDK_INIT_FAILED(1005, "SDK初始化失败"),
    ERROR_CANOT_CONNECT_TO_INVENTORY_MODULE(1006, "SDK初始化失败，无法连接到盘点模块"),
    ERROR_CAMERA_NOT_WORKING_PROPERLY(2001, "识别模块连接的摄像头被遮挡或不能正常工作"),
    ERROR_CAMERA_NOT_FOUND(2002, "识别模块未连接摄像头"),
    ERROR_NOT_ENOUGH_DISC_SPACE_IN_MODULE(2003, "商品识别模块已无磁盘空间"),
    ERROR_MODULE_CANNOT_BE_CONNECTED(2004, "商品识别模块无法连接"),
    ERROR_START_UPDATING_ERROR(2011, "启动更新失败"),
    ERROR_BUSY_FOR_UPDATING(2012, "更新中"),
    ERROR_CHANNEL_NOT_EXIST(2021, "通道不存在"),
    ERROR_CHANNEL_ALREADY_OPEN(2022, "通道已经打开"),
    ERROR_CHANNEL_NOT_OPEN(2023, "通道未打开"),
    ERROR_CELL_ID_ALREADY_BE_USED(2024, "单元ID已被使用"),
    ERROR_RECOGNITION_TIMEOUT(3001, "商品识别超时"),
    ERROR_ABNORMAL_PLACEMENT_OF_GOODS(3002, "商品放置异常"),
    ERROR_UNKNOWN_EXCEPTION(9000, "未知异常/错误"),

//    ERROR_INVALID_PARAMETER(1001, "invalid parameter"),  // 调用SDK接口时，参数格式或取值不正确
//    ERROR_SDK_NOT_INITIALIZED(1002, "SDK not initialized"),    // 调用SDK其他接口之前未调用init初始化
//    ERROR_SDK_RE_INITIALIZED(1003, "SDK can’t be re-initialized"),    // 重复调用SDK的init接口
//    ERROR_TOO_MUCH_RECOGNITION_MODULES(1004, "Too much recognition modules"),   // SDK初始化时传入太多的识别模块或通道参数
//    ERROR_SDK_INIT_FAILED(1005, "SDK init error"),     // SDK初始化失败
//    ERROR_CANOT_CONNECT_TO_INVENTORY_MODULE(1006, "Can’t connect to inventory module"),  // SDK初始化失败，无法连接到盘点模块
//    ERROR_CAMERA_NOT_WORKING_PROPERLY(2001, "Camera not working properly"),    // 识别模块连接的摄像头被遮挡或不能正常工作
//    ERROR_CAMERA_NOT_FOUND(2002, "Camera not found"),   // 识别模块未连接摄像头
//    ERROR_NOT_ENOUGH_DISC_SPACE_IN_MODULE(2003, "Not enough disc space in module"),    // 商品识别模块已无磁盘空间
//    ERROR_MODULE_CANNOT_BE_CONNECTED(2004, "Module cannot be connected"),     // 商品识别模块无法连接
//    ERROR_START_UPDATING_ERROR(2011, "Start updating error"),   // 启动更新失败
//    ERROR_BUSY_FOR_UPDATING(2012, "Busy for updating"),  // 更新中
//    ERROR_CHANNEL_NOT_EXIST(2021, "Channel not exist"),  // 通道不存在
//    ERROR_CHANNEL_ALREADY_OPEN(2022, "Channel already open"),   // 通道已经打开
//    ERROR_CHANNEL_NOT_OPEN(2023, "Channel not open"),   // 通道未打开
//    ERROR_CELL_ID_ALREADY_BE_USED(2024, "Cell ID already be used"),    // 单元ID已被使用
//    ERROR_RECOGNITION_TIMEOUT(3001, "Recognition timeout"),    // 商品识别超时
//    ERROR_ABNORMAL_PLACEMENT_OF_GOODS(3002, "Abnormal placement of goods"),    // 商品放置异常
//    ERROR_UNKNOWN(9000, "Unknown exception/error"),     // 未知异常/错误

    /* -----------------------10001—19999 开始----------------------- */
    ERROR_OPENDOOR_FAILED(10000, "开门失败"),
    ERROR_LOCK_CORE_FALL_EXCEPTION(10001, "锁芯落下异常"),
    ERROR_CLOSEDOOR_FAILED(10002, "关门失败"),
    ERROR_RECLOSEDOOR(10003, "设备门未关好，请重新关门"),
    ERROR_CLOSE_WEIGH(10004, "称重异常,关门失败"),

    /* -----------------------20001—29999 开始----------------------- */

    ERROR_VROS_INIT_FAILED(20001, "视觉系统初始化失败"),
    ERROR_VROS_SHUTDOWN_FAILED(20002, "视觉系统关闭失败"),
    ERROR_CHANNEL_OPEN_FAILED(20003, "通道打开失败"),
    ERROR_OPEN_VR_DISTINGUISH_COMMAND_EXCEPTION(20004, "打开视觉识别命令异常"),
    ERROR_SHUTDOWN_SOME_CHANNEL_FAILED(20005, "关闭某通道失败"),
    ERROR_SHUTDOWN_CHANNEL_COMMAND_FAILED(20006, "关闭通道命令执行失败"),
    ERROR_INVENTORY_FAILED_ONCE(20007, "盘货失败（一次）"),
    ERROR_CHECK_DISTINGUISH_UNIT_RUN_STATUS_FAILED(20008, "查询识别单元的运行状态失败"),
    ERROR_QUERY_VERSION_NO_FAILED(20009, "查询版本号失败"),
    ERROR_INVENTORY_FAILED_FIVE_TIMES(20010, "盘货失败（五次）"),

    /* -----------------------30001—39999 开始----------------------- */

    ERROR_SHUTDOWN_FAILED(30000, "关机失败"),
    ERROR_SHORT_CONNECTION_UNKNOWN(30001, "短连接出现未知异常"),
    ERROR_UPDATE_OPENDOOR_SPEECH_FAILED(30002, "更新开门语音失败"),
    ERROR_UPDATE_OPENDOOR_SHOPPING_VOICE_FAILED(30003, "更新开门购物语音失败"),
    ERROR_UPDATE_CLOSEDOOR_VOICE_FAILED(30004, "更新关门语音失败"),
    ERROR_CURRENT_OS_SERVICE_IS_LATEST_VERSION(30005, "当前系统服务是最新版本"),
    ERROR_SYSTEM_UPDATE_ADDRESS_WRONG(30006, "系统更新地址有误"),
    ERROR_TCP_CONNECTION_UNKNOWN1(30007, "长连接出现未知异常"),
    ERROR_HOME_AD_MODEL(30008, "首页广告model错误"),
    ERROR_HOEM_AD_URL_IS_NULL(30009, "首页广告资源为空"),
    ERROR_SHOPPING_MODEL_URL_IS_NULL(30010, "购物车广告资源地址返回错误"),
    ERROR_APK_INSTALL_FAILED(30011, "apk更新包下载完成安装失败"),
    ERROR_SHOPPING_MODEL(30012, "七奇视觉识别库下载失败"),

  /* -----------------------40000—49999 开始----------------------- */
    ERROR_SHUTDOWN_FAILED_(40000, "关机失败"),
    ERROR_SHORT_CONNECTION_UNKNOWN_(40001, "短连接出现未知异常"),


    ERROR_UNKNOWN_CODE(1000010, "未知错误码"),
    ERROR_NULL_CODE(1000011, "错误码为空"),
    ERROR_AT_LAST(1008611, "占位符");


    private int code;
    String returnMsg;

    AndroidErrorCode(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }

    public static String getNameByCode(int code) {
        return ((AndroidErrorCode) param.get(Integer.valueOf(code))).returnMsg;
    }

    public <T> ResponseVo<T> getResponseVo(T t) {
        return new ResponseVo(false, this.code, this.returnMsg, t);
    }

    public ResponseVo getResponseVo() {
        return new ResponseVo(false, this.code, this.returnMsg);
    }

    public ResponseVo getResponseVo(String msg) {
        return new ResponseVo(false, this.code, msg);
    }

    private static Map<Integer, AndroidErrorCode> param;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (param == null) {
                param = new HashMap<Integer, AndroidErrorCode>();
                for (AndroidErrorCode ec : AndroidErrorCode.values()) {
                    param.put(ec.code, ec);
                }
            }
        }
    }
}
