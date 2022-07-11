package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.GetServerConfigCommand;
import com.cloud.cang.api.antbox.message.GetServerConfigResponse;
import org.springframework.stereotype.Component;
/**
* 获取服务器配置信息指令<br>
* 控制器启动后，发送该命令上位机，获取配置信息来初始化控制器的某些工作参数。如果获取失败，这些参数到将采用缺省值来初始化。
*/
@Component
public class GetServerConfigCommandHandler
        extends BoxMessageHandler<GetServerConfigCommand, GetServerConfigCommand> {

    @Override
    public void handle(BoxContext ctx, GetServerConfigCommand msg) {
        try {
            ctx.getCommandSender().sendCommand(new GetServerConfigResponse());
        } catch (SendCommandException e) {
            e.printStackTrace();
        }

        super.notifyListeners(ctx, msg);
    }

}
