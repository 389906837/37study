package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;

public interface MessageListener<T> {

    public void onMessage(BoxInfo boxInfo, T msg);
}
