package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.RebootMessage;
import org.springframework.stereotype.Component;
/**
 * 设备重启消息<br>
 * 控制器上电启动后或者因某种原因复位而重新启动后，将产生一条设备重启消息。
 */
@Component
public class RebootMessageHandler extends BoxMessageHandler<RebootMessage, RebootMessage> {

    @Override
    public void handle(BoxContext ctx, RebootMessage msg) {
        /*if (ctx.getCurrentCustomerDto() != null) {
            try {
                ctx.getCommandSender().inventory();
            } catch (SendCommandException e) {
                e.printStackTrace();
            }
        }*/

        super.notifyListeners(ctx, msg);
    }

}
