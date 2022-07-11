package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.AcCheckMessage;
import org.springframework.stereotype.Component;
/**
 * 断电检测消息
 */
@Component
public class AcCheckMessageListener implements MessageListener<AcCheckMessage> {

    @Override
    public void onMessage(BoxInfo boxInfo, AcCheckMessage msg) {

        if (boxInfo == null || boxInfo.getBoxId() == null)
            return;
    }

}
