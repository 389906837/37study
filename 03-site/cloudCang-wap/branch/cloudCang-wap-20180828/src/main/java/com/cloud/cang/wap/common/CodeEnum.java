package com.cloud.cang.wap.common;

import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;


/**
 * 返回信息枚举类
 *
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2016年4月11日 下午6:27:51
 */
public enum CodeEnum {
    SUCCESS(100, "操作成功"),
    ERROR_10000(10000, "系统异常，请联系客服!"),
    ERROR_10001(10001, "来源地址错误"),
    ERROR_10002(10002, "设备不存在"),
    ERROR_10003(10003, "设备不可用"),
    ERROR_10004(10004, "设备故障不可用"),
    ERROR_10005(10005, "商户不存在"),
    ERROR_10006(10006, "商户状态异常"),
    ERROR_10007(10007, "设备暂停运营"),
    ERROR_10008(10008, "系统异常，请重新操作"),
    ERROR_10009(10009, "签约异常，请重新操作"),
    ERROR_10010(10010, "签约异常，用户免密已开通"),
    ERROR_10011(10011, "解约异常，请先开通免密"),
    ERROR_10012(10012, "连接服务器失败，请检查您当前的网络"),
    ERROR_10013(10013, "设备离线"),
    ERROR_10014(10014, "数据异常，请联系客服!"),
    ERROR_10015(10015, "购物异常，请联系客服!"),
    ERROR_10016(10016, "补货异常，请联系客服!"),
    ERROR_10017(10017, "设备二维码异常，请联系客服!"),
    ERROR_10018(10018, "开门超时，请重新操作"),
    ERROR_10019(10019, "关门结算超时，请联系客服!"),



    ERROR_20000(20000, "人脸识别设备不存在!"),
    ERROR_20001(20001, "人脸识别设备不可用!"),
    ERROR_20002(20002, "人脸识别设备离线!"),
    ERROR_20003(20003, "人脸识别设备故障!"),
    ERROR_20004(20004, "人脸识别设备已停运!"),
    ERROR_20005(20005, "授权二维码已经过期!"),
    ERROR_20006(20006, "二维码已经被扫过!"),
    ERROR_20007(20007, "注册二维码已经失效!"),
    ERROR_20008(20008, "不能重复注册!"),
    ERROR_20009(20009, "授权失败!"),
    ERROR_20010(20010, "您已经在设备上取消授权!"),
    ERROR_20011(20011, "二维码失效!"),
    ERROR_20012(20012, "二维码有效期已过，请重新获取二维码!"),

    ILLEGAL_OPERATION(-1000, "非法请求"),
    ERROR_OTHER(-1001, "其他错误"),
    ERROR_SYSTEMP(-1002, "系统异常，请重新操作"),
    ERROR_NETWORK(-1003, "网络异常，请重新操作"),
    EMPTY_MOBILE_PHONE(-1004, "请输入手机号码"),
    MOBILE_PHONE_FORMAT_ERROR(-1005, "请正确输入您的手机号码"),
    MOBILE_PHONE_EXIST(-1006, "手机号码已存在"),
    TX_CODE_EMPTY(-1007, "图形验证码不能为空"),
    TX_CODE_ERROR(-1008, "图形验证码错误"),
    SEND_MSG_ERROR(-1009, "短信发送失败");


    private int code;
    String returnMsg;

    CodeEnum(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }

    private static Map<Integer, CodeEnum> params;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (params == null) {
                params = new HashMap<Integer, CodeEnum>();
                for (CodeEnum e : CodeEnum.values()) {
                    params.put(e.code, e);
                }
            }
        }
    }

    public static String getNameByCode(int code) {
        if (null == params.get(code)) {
            return "";
        }
        return params.get(code).returnMsg;
    }

    public static CodeEnum getEnumByCode(int code) {
        return params.get(code);
    }

    public ResponseVo getResponseVo() {
        return new ResponseVo(false, this.code, this.getNameByCode(this.code));
    }
    public ResponseVo getResponseVo(String msg) {
        return new ResponseVo(false, this.code, msg);
    }
}
