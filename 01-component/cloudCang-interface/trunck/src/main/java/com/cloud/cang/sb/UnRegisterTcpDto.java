package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * Created by Alex on 2018/6/2.
 */
public class UnRegisterTcpDto extends SuperDto {
    private String channelId;  // 通道long ID

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "UnRegisterTcpDto{" +
                "channelId='" + channelId + '\'' +
                '}';
    }
}
