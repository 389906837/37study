package com.cloud.cang.open.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回枚举类
 * @author zhouhong
 * @version 2.0
 * @date 2018年1月24日11:19:34
 */
public enum ReturnCodeEnum {

    SUCCESS(200, "成功"),
    BUSY_BUSINESS(10001, "网络繁忙，请稍后再试!"),
    BUSINESS_FAILED(10002, "业务处理失败!");


    private int code;
    private String returnMsg;

    ReturnCodeEnum(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static String getNameByCode(int code) {
        return param.get(Integer.valueOf(code)).getReturnMsg();
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
