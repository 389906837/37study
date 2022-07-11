package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.message.SwitchOffMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 关门消息
 */
@Component
public class DoorClosedMessageHandler extends BoxMessageHandler<SwitchOffMessage, SwitchOffMessage> {

    @Override
    public void handle(BoxContext ctx, SwitchOffMessage msg) {
        // 关门时，设置设备状态：盘点中
        ctx.setStatus(BoxStatus.BUSY);
        CustomerDto currentUser = ctx.getCurrentCustomerDto();
        if (currentUser == null) {
            ctx.logIncredible("the door has been opened");
            return;
        }
        // 设置关门时间，不退出登录等待盘点
        currentUser.closeDoor(new Date());

        super.notifyListeners(ctx, msg);
    }

}
