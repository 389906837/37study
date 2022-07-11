package com.cloud.cang.pay.wechat.notify;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by YLF on 2019/8/6.
 */
public class WechatPointsNotifyData {
    private String id;//通知ID
    private String create_time;//通知创建的时间，格式为yyyyMMddHHmmss
    private String event_type;//通知的类型，开启成功通知的类型为PAYSCORE.USER_OPEN_SERVICE；停用成功通知的类型为PAYSCORE.USER_CLOSE_SERVICE
    private String resource_type;//通知的资源数据类型，开启成功通知为encrypt-resource
    private JSONObject resource;//通知资源数据json格式


    @Override
    public String toString() {
        return "OpenNotifyData{" +
                "id='" + id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", event_type='" + event_type + '\'' +
                ", resource_type='" + resource_type + '\'' +
                ", resource=" + resource +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public JSONObject getResource() {
        return resource;
    }

    public void setResource(JSONObject resource) {
        this.resource = resource;
    }
}
