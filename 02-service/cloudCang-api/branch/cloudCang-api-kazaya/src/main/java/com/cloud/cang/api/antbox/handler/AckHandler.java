package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.protocol.AntboxAck;
import org.springframework.stereotype.Component;

@Component
public class AckHandler extends BoxMessageHandler<AntboxAck, AntboxAck> {

    @Override
    public void handle(BoxContext ctx, AntboxAck msg) {
        if (msg.isOk()) {
//            ctx.ackLock();
            try {
                ctx.getAckRollCodeSet().remove(msg.getRollCode());
            } finally {
//                ctx.ackUnlock();
            }
        }

        super.notifyListeners(ctx, msg);
    }

}
