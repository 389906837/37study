package com.cloud.cang.open.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务code 枚举类
 * @author zhouhong
 * @version 2.0
 * @date 2018年1月24日11:19:34
 */
public enum SubCodeEnum {

    SUCCESS("SUCCESS", "成功"),
    SYSTEM_ERROR("SYSTEM-ERROR", "系统异常，请稍后再试!"),
    INVALID_SIGN("INVALID-SIGN", "签名无效，检查请求参数，重新签名后重新发起请求!"),
    INVALID_PARAMETER("INVALID-PARAMETER", "参数无效，检查请求参数，重新签名后重新发起请求!"),
    NO_PERMISSION("NO-PERMISSION", "无权限使用接口，接口未申请，请联系客服服务电话!"),
    APPID_ERROR("APPID-ERROR", "应用APP_ID填写错误，请联系客服服务电话，确认APP_ID的状态!"),
    INFO_TAMPER("INFO-TAMPER", "接口信息被篡改，更换商户业务批次号后，重新发起请求!"),
    BALANCE_NOT_ENOUGH("BALANCE-NOT-ENOUGH", "接口余额不足，请联系客服服务电话，确认接口余额是否正常!"),
    INVALID_PARAMETER_OUT_BATCH_NO("INVALID-PARAMETER-OUT-BATCH-NO", "参数无效，商户业务编号必填，并且确保编号唯一!"),
    INVALID_PARAMETER_OUT_BATCH_NO_EXIST("INVALID-PARAMETER-OUT-BATCH-NO-EXIST", "参数无效，商户业务编号重复，请勿重复发送请求!"),
    INVALID_PARAMETER_IMAGE_EMPTY("INVALID-PARAMETER-IMAGE-EMPTY", "参数无效，未找到视觉识别图片!"),
    INVALID_PARAMETER_IMAGE_MAX("INVALID-PARAMETER-IMAGE-MAX", "参数无效，识别图片数量过多!"),
    INVALID_PARAMETER_RECOGNITION_CELL_EMPTY("INVALID-PARAMETER-RECOGNITION-CELL-EMPTY", "参数无效，图片识别单元不能为空!"),
    INVALID_PARAMETER_IMAGE_FORMAT_ERROR("INVALID-PARAMETER-IMAGE-FORMAT-ERROR", "参数无效，图片文件格式错误!"),
    INTERFACE_ACCOUNT_NOT_EXIST("INTERFACE-ACCOUNT-NOT-EXIST", "接口账户不存在或异常!"),
    RECOGNITION_SERVER_ERROR("RECOGNITION-SERVER-ERROR", "识别服务器异常!"),
    NO_DATA("NO-DATA", "数据不存在!"),
    RECOGNITION_SERVER_RESULT_ERROR("RECOGNITION-SERVER-RESULT-ERROR", "识别服务器识别结果异常!"),
    UNKNOWN_ERROR("UNKNOWN-ERROR", "未知错误!"),
    MISS_PARAMTER("REQUEST PARAMETER ERROR", "请求参数错误!"),
    METHOD_ERROR("METHOD ERROR", "接口未授权"),
    METHOD_EMPTY("METHOD EMPTY","未定义的methodName");


    private String code;
    private String returnMsg;

    SubCodeEnum(String code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public static String getNameByCode(String code) {
        return param.get(code).getReturnMsg();
    }

    public static SubCodeEnum getSubCodeEnum(String code) {
        return param.get(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    private static Map<String, SubCodeEnum> param;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (param == null) {
                param = new HashMap<String, SubCodeEnum>();
                for (SubCodeEnum ec : SubCodeEnum.values()) {
                    param.put(ec.code, ec);
                }
            }
        }
    }
}
