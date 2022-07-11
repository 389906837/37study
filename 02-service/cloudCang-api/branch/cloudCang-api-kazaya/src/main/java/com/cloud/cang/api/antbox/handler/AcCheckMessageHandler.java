package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.message.AcCheckMessage;
import org.springframework.stereotype.Component;

/**
 * 断电检测消息
 */
@Component
public class AcCheckMessageHandler extends BoxMessageHandler<AcCheckMessage, AcCheckMessage> {

    @Override
    public void handle(BoxContext ctx, AcCheckMessage msg) {
        super.notifyListeners(ctx, msg);
    }

}
