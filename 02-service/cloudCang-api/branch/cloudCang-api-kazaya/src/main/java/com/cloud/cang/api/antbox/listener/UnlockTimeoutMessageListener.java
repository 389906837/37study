package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.UnlockTimeoutMessage;
import org.springframework.stereotype.Component;

@Component
public class UnlockTimeoutMessageListener implements MessageListener<UnlockTimeoutMessage> {


    @Override
    public void onMessage(BoxInfo boxInfo, UnlockTimeoutMessage msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null)
            return;
    }

}
