package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.message.OpenTimeoutMessage;
import org.springframework.stereotype.Component;
/**
 * 开门超时消息<br>
 * 当控制器接收到开门指令且门锁打开后，在定义的时间内门锁没有被关闭，则产生一条开门超时消息。
 */
@Component
public class OpenTimeoutMessageHandler
        extends BoxMessageHandler<OpenTimeoutMessage, OpenTimeoutMessage> {

    @Override
    public void handle(BoxContext ctx, OpenTimeoutMessage msg) {
        super.notifyListeners(ctx, msg);
    }
}
