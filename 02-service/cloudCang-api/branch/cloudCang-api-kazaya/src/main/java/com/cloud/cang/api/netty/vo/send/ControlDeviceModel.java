package com.cloud.cang.api.netty.vo.send;

import com.cloud.cang.api.netty.vo.base.BaseMsg;

/**
 * 发送给设备的消息格式
 * Created by Alex on 2018/3/20.
 */
public class ControlDeviceModel extends BaseMsg {
    private String userId;      //消息发送者ID
    private MessageBody data;   //发送给设备的消息里面的data数据
    private Integer registermsg;    //

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MessageBody getData() {
        return data;
    }

    public void setData(MessageBody data) {
        this.data = data;
    }

    public Integer getRegistermsg() {
        return registermsg;
    }

    public void setRegistermsg(Integer registermsg) {
        this.registermsg = registermsg;
    }
}
