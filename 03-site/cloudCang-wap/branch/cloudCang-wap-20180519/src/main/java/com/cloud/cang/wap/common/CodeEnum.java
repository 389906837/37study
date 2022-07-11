package com.cloud.cang.wap.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 返回信息枚举类
 * @Author: zhouhong
 * @Date: 2016年4月11日 下午6:27:51
 * @version 1.0
 */
public enum CodeEnum {
    SUCCESS(100,"操作成功"),
	ERROR_10000(10000,"系统异常，请联系客服400-828-3737"),
	ERROR_10001(10001,"来源地址错误"),
	ERROR_10002(10002,"设备不存在"),
	ERROR_10003(10003,"设备不可用"),
	ERROR_10004(10004,"设备故障不可用"),
	ERROR_10005(10005,"商户不存在"),
	ERROR_10006(10006,"商户状态异常"),
	ERROR_10007(10007,"设备暂停运营"),
	ERROR_10008(10008,"系统异常，请重新操作"),
    ERROR_10009(10009,"签约异常，请重新操作"),
    ERROR_10010(10010,"签约异常，用户免密已开通"),
    ERROR_10011(10011,"解约异常，请先开通免密"),
    ERROR_10012(10012,"连接服务器失败，请检查您当前的网络"),
    ERROR_10013(10013,"设备离线"),
    ERROR_10014(10014,"数据异常，请联系客服400-828-3737"),
    ERROR_10015(10015,"购物异常，请联系客服400-828-3737"),
    ERROR_10016(10016,"补货异常，请联系客服400-828-3737"),
    ILLEGAL_OPERATION(-1000,"非法请求"),
    ERROR_OTHER(-1001,"其他错误"),
    ERROR_SYSTEMP(-1002,"系统异常，请重新操作"),
    ERROR_NETWORK(-1003,"网络异常，请重新操作"),
    EMPTY_MOBILE_PHONE(-1004,"请输入手机号码"),
    MOBILE_PHONE_FORMAT_ERROR(-1005,"请正确输入您的手机号码"),
    MOBILE_PHONE_EXIST(-1006,"手机号码已存在"),
    TX_CODE_EMPTY(-1007,"图形验证码不能为空"),
    TX_CODE_ERROR(-1008,"图形验证码错误"),
    SEND_MSG_ERROR(-1009,"短信发送失败")
	;
	
	
	
	private int code;String returnMsg;
	CodeEnum(int code,String returnMsg){
        this.code = code;
        this.returnMsg = returnMsg;
    }
    public int getCode(){
        return this.code;
    }
    private static Map<Integer, CodeEnum> params ;
    private static Object lock = new Object();
    static{
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
}
