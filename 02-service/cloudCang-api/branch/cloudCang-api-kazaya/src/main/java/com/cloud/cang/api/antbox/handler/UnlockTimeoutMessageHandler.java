package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.message.UnlockTimeoutMessage;
import org.springframework.stereotype.Component;
/**
 * 超时上锁消息 <br>
 * 当控制器接收到开门指令后，在超时上锁时间内门锁没有被打开，则锁芯弹出并产生一条超时上锁消息
 */
@Component
public class UnlockTimeoutMessageHandler
        extends BoxMessageHandler<UnlockTimeoutMessage, UnlockTimeoutMessage> {

    @Override
    public void handle(BoxContext ctx, UnlockTimeoutMessage msg) {
        if (ctx.getCurrentCustomerDto() == null) {
            ctx.logIncredible("no body login but unlock timeout.");
            return;
        }

        if (!BoxStatus.LOGIN.equals(ctx.getBoxInfo().getStatus())) {
            ctx.logIncredible("unlock timeout in current status.");
            return;
        }

        // 开门超时直接退出登录
        ctx.logout();

        super.notifyListeners(ctx, msg);
    }

}
