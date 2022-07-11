package com.cloud.cang.api.netty.vo.base;

/**
 * Created by Alex on 2018/3/20.
 */
public class BaseMsg {
    private String deviceID;//设备ID
    private String userId;//用户Id
    private String extend;//预留扩展参数
    private String msg;//成功（“0”）    失败（“原因”）
    private int code = -1;//返回码，成功返回0，失败返回错误码
    private String pawd;//唯一标识（客户端注册时，token为32位安全密钥）
    private String source;  //日志信息

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPawd() {
        return pawd;
    }

    public void setPawd(String pawd) {
        this.pawd = pawd;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BaseMsg{" +
                "deviceID='" + deviceID + '\'' +
                ", userId='" + userId + '\'' +
                ", extend='" + extend + '\'' +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", pawd='" + pawd + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
