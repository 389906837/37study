package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.AntboxDeviceContextRegistry;
import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.GetBoxInfoResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 获取控制器系统的固件和状态信息。
 */
@Component
public class GetBoxInfoResponseHandler
        extends BoxMessageHandler<GetBoxInfoResponse, GetBoxInfoResponse> {

    @Override
    public void handle(BoxContext ctx, GetBoxInfoResponse msg) {
        if (ctx.getBoxInfo().getBoxId() != null) {
            super.notifyListeners(ctx, msg);
            return;
        }
        // step1 在BoxContext中，设置boxInfo的设备ID
        // step2 预设盘点参数
        ctx.setBoxId(msg.getBoxSn());
        // step3 注册上下文BoxContext [ boxId-->BoxContext ]
        AntboxDeviceContextRegistry.register(msg.getBoxSn(), ctx);
        try {
            // step4 发送指令：设置控制器的当前时间
            ctx.getCommandSender().setClock(new Date());
        } catch (SendCommandException e) {
            e.printStackTrace();
        }

        // 设置售货机状态：空闲
        ctx.setStatus(BoxStatus.IDLE);
        // step5 发送指令：盘点
        /*if (BoxStatus.INIT.equals(ctx.getBoxInfo().getStatus())) {
            try {
                ctx.getCommandSender().inventory();
            } catch (SendCommandException e) {
                e.printStackTrace();
            }
        }*/
        super.notifyListeners(ctx, msg);
    }
}
