package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/7/23.
 */
public class OpenNotifyUserData {
    //必填
    private String appid;//调用开启服务接口提交的公众账号ID
    private String mchid;//调用开启服务接口提交的商户号
    private String service_id;//调用开启服务接口提交的服务ID
    private String openid;//微信用户在商户对应appid下的唯一标识。


    //非必填
    private String out_request_no;//调用开启服务接口提交的商户请求唯一标识（仅开启服务回调会返回此字段）
    private String user_service_status;//USER_OPEN_SERVICE：开启成功；USER_CLOSE_SERVICE：停用成功
    private String openorclose_time;//服务开启/停用成功时间




    public String getUser_service_status() {
        return user_service_status;
    }

    public void setUser_service_status(String user_service_status) {
        this.user_service_status = user_service_status;
    }

    public String getOpenorclose_time() {
        return openorclose_time;
    }

    public void setOpenorclose_time(String openorclose_time) {
        this.openorclose_time = openorclose_time;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "OpenNotifyUserData{" +
                "appid='" + appid + '\'' +
                ", mchid='" + mchid + '\'' +
                ", service_id='" + service_id + '\'' +
                ", openid='" + openid + '\'' +
                ", out_request_no='" + out_request_no + '\'' +
                ", user_service_status='" + user_service_status + '\'' +
                ", openorclose_time='" + openorclose_time + '\'' +
                '}';
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
