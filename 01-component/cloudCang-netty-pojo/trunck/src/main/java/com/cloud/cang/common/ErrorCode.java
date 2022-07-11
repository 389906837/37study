package com.cloud.cang.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 错误代码
 * @Author: zengzexiong
 * @Date: 2018年5月14日13:12:46
 */
public enum ErrorCode {

    CLOSEDOOR_SERVICE_FAILED(4001, "调用关门盘货服务失败"),
    CLOSEDOOR_SERVICE_EXCEPTION(4002, "调用关门盘货服务异常"),
    JSON_CONVERT_EXCEPTION(4003, "实时盘货结果中商品数据json转换异常"),
    REALTIME_ORDER_FAILED(4004, "创建开门实时盘货生成实时订单失败"),
    REALTIME_ORDER_EXCEPTION(4005, "创建开门实时盘货生成实时订单出现异常"),
    REALTIME_ORDER_NULL(4006, "创建开门实时盘货生成实时订单为空"),
    MEMBER_ID_ISNULL(4007, "开门盘货消息体中会员ID不能为空"),

    REALTIME_GRAVITY_ORDER_EXCEPTION(5001, "重力柜创建实时订单和库存对比重量没变化"),
    REALTIME_GRAVITY_ORDER_EXCEPTION_EX(5002, "创建开门盘货生成实时订单,库存重量小于设备商品重量"),




    /* -----------------------占位符----------------------- */
    PLACEHOLDER(1008611, "占位符");


    private int code;
    String returnMsg;

    ErrorCode(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }

    public static String getNameByCode(int code) {
        return ((ErrorCode) param.get(Integer.valueOf(code))).returnMsg;
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

    private static Map<Integer, ErrorCode> param;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (param == null) {
                param = new HashMap<Integer, ErrorCode>();
                for (ErrorCode ec : ErrorCode.values()) {
                    param.put(ec.code, ec);
                }
            }
        }
    }
}
