package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.SwitchOnMessage;
import org.springframework.stereotype.Component;

@Component
public class DoorOpenedMessageListener implements MessageListener<SwitchOnMessage> {


    @Override
    public void onMessage(BoxInfo boxInfo, SwitchOnMessage msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null)
            return;

    }

}
