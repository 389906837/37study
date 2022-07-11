package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.SwitchOffMessage;
import org.springframework.stereotype.Component;

@Component
public class DoorClosedMessageListener implements MessageListener<SwitchOffMessage> {


    @Override
    public void onMessage(BoxInfo boxInfo, SwitchOffMessage msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null)
            return;

    }
}
