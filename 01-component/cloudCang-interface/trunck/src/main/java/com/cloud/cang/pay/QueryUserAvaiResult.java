package com.cloud.cang.pay;

/**
 * Created by YLF on 2019/7/19.
 */
public class QueryUserAvaiResult {
    private String service_id;
    private String openid;
    private String appid;
    private String use_service_state;//标识用户开启服务情况：
                                      /*  UNAVAILABLE - 用户未开启服务；
                                        AVAILABLE - 用户已开启服务*/

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }


    public String getUse_service_state() {
        return use_service_state;
    }

    public void setUse_service_state(String use_service_state) {
        this.use_service_state = use_service_state;
    }

    @Override
    public String toString() {
        return "QueryUserAvaiResult{" +
                "service_id='" + service_id + '\'' +
                ", openid='" + openid + '\'' +
                ", appid='" + appid + '\'' +
                ", user_service_state='" + use_service_state + '\'' +
                '}';
    }
}
