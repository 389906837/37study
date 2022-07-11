package com.cloud.cang.api.common;

import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 请严格准命名规范
 * ERROR_错误类型 <br />
 * 10001 -20000 通用错误码
 *
 * @author zengzexiong
 * @version 2.0
 * @date 2018年1月24日11:19:34
 */
public enum ReturnCodeEnum {
    //  成功
    SUCCESS(1000, "成功"),
    ERROR_DEVICEINFO_REGISTERED(201, "该注册信息已经被使用"),
    ERROR_NO_REGISTER_INFO(202, "没有该设备注册信息"),
    ERROR_NO_DEVICE_INFO(203, "没有该设备信息"),


    /* -----------------------长连接后台服务——返回错误码——开始----------------------- */
    ERROR_JSON(1001, "json转换失败"),
    ERROR_TOKEN(1002, "token校验失败"),
    ERROR_PARAM(1003, "参数不能为null"),
    ERROR_OTHER(1004, "其他未知错误"),
    ERROR_REGISTER(1005, "注册失败"),
    ERROR_TYPE(1006, "type类型不正确"),
    ERROR_REPEAT(1007, "TCP连接重复"),
    ERROR_METHOD(1008, "未知操作"),

    /* -----------------------长连接后台服务——返回错误码——结束----------------------- */

    ERROR_EXIST(2001, "设备已经注册"),
    ERROR_CHANNEL(2002, "客户端通道不存在"),
    ERROR_PARAM_FAILED(2003,"参数错误"),
    ERROR_PARAM_STATUS(2004,"非法状态"),
    ERROR_DEVICE_UNKNOWN(2005,"未知设备"),
    ERROR_SEND_MESSAGE(2006,"向客户端发送消息失败"),
    ERROR_DOOR_OPEN(2007,"门已经被打开了"),
    FAIL_DOOR_OPEN(20071,"门正在打开，请等待"),
    ERROR_DEVICE_OFFLINE(2008,"设备离线"),
    ERROR_ALIPAY_OFFLINE(2009,"支付宝断开连接"),
    ERROR_OPENDOOR_FAILED(2010,"开门失败"),
    ERROR_REPLENISHMENT_EXCEPTION(2011,"补货异常"),
    ERROR_SHOPPING_EXCEPTION(2012,"购物异常"),
    ERROR_CLOSEDOOR_INVENTORY_EXCEPTION(2013,"关门盘货异常"),



    /* -----------------------短连接后台服务——返回错误码——开始----------------------- */

    ERROR_NO_AD(3001,"没有查到广告");
    /* -----------------------短连接后台服务——返回错误码——结束----------------------- */

    private int code;
    String returnMsg;

    ReturnCodeEnum(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }
    public static String getNameByCode(int code) {
        return ((ReturnCodeEnum)param.get(Integer.valueOf(code))).returnMsg;
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

    private static Map<Integer, ReturnCodeEnum> param;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (param == null) {
                param = new HashMap<Integer, ReturnCodeEnum>();
                for (ReturnCodeEnum ec : ReturnCodeEnum.values()) {
                    param.put(ec.code, ec);
                }
            }
        }
    }
}
