package com.cloud.cang.faceCommon;

/**
 * @version 1.0
 * @Description: 方法名常量
 * @Author: zengzexiong
 * @Date: 2018年7月25日10:16:14
 */
public class FaceType extends SuperDto {

    /**
     * 后台向设备发送消息时的--方法名
     */
    public static final String OPEN_DOOR = "openDoor";                            // 开门
    public static final String CLOSE_DOOR = "closeDoor";                        // 关门
    public static final String IS_ALIVE = "isAlive";                              // 连接是否断开
    public static final String EXCEPTION = "exception";// 设备异常
    public static final String FAULT = "fault";// 设备故障
    public static final String CHECK_TCP = "checkTcp";// 校验长连接是否成功

    public static final String QR_CODE="qrCode";                                //发送大屏二维码


    public static final String REGISTER_QR_CODE = "registerQrCode";               // 发送注册二维码
    public static final String FACE_REGISTER = "faceRegister";                    // 人脸注册
    public static final String FACE_LOGIN = "faceLogin";                          // 人脸登录
    public static final String FIND_USER_BY_PHONE_NUM = "findUserByPhoneNum";     // 手机号搜寻用户人脸信息
    public static final String SCAN_CODE_SUCCESS = "scanCodeSuccess";               // 扫码成功
    public static final String SCAN_CODE_FAIL = "scanCodeFail";                     // 扫码失败
    public static final String SCAN_CODE_AUTHORIZE_SUCCESS = "scanCodeAuthorizeSuccess";          // 扫码授权成功
    public static final String SCAN_CODE_AUTHORIZE_FAIL = "scanCodeAuthorizeFail";                // 扫码授权失败
    public static final String CANCLE_REGISTER = "cancleRegister";                // AI设备取消注册授权

    public FaceType() {

    }
}
