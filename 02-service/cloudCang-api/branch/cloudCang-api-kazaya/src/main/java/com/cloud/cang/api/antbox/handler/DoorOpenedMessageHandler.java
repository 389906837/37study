package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.message.SwitchOnMessage;
import org.springframework.stereotype.Component;

@Component
public class DoorOpenedMessageHandler extends BoxMessageHandler<SwitchOnMessage, SwitchOnMessage> {

    @Override
    public void handle(BoxContext ctx, SwitchOnMessage msg) {
        CustomerDto currentUser = ctx.getCurrentCustomerDto();
        if (currentUser == null) {
            ctx.logIncredible("the door has been opened");
            return;
        }
        // 设置售货机开门状态：
        ctx.setStatus(BoxStatus.OPENED);
        // 设置当前用户开门时间
        currentUser.openDoor(msg.getMsgTime());

        super.notifyListeners(ctx, msg);
    }

}
