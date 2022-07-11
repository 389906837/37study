package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.GetExpiredTimeResponse;
import com.cloud.cang.api.antbox.util.AntboxEncryptor;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 若控制器设备的有效期 < 2019年
 * 则发送指令：设置控制器的有效期为当前时间
 */
@Component
public class GetExpiredTimeResponseHandler extends BoxMessageHandler<GetExpiredTimeResponse, GetExpiredTimeResponse> {

    @Override
    public void handle(BoxContext ctx, GetExpiredTimeResponse msg) {
        ctx.logInfo("SetExpiredTimeResponseHanler");

        ByteBuf byteBuf = msg.getCmdData();

        byte[] ciphertext = new byte[12];
        byte[] bytes = byteBuf.array();
        for (int i = 8; i < bytes.length; i++) {
            ciphertext[i - 8] = bytes[i];
        }

        byte[] text = AntboxEncryptor.getInstance().decrypt(ctx.getDynamicSecretKey().array(), ciphertext);
        byte[] dateByte = new byte[6];
        for (int i = 0; i < 6; i++) {
            dateByte[i] = text[i + 6];
        }
        Date expiredTime = AntboxUtil.readDate(dateByte);
        ctx.logInfo("GetExpiredTimeResponseHandler box expiredTime : {}", ctx.fullDateSDF.format(expiredTime));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date setExpiredTime = calendar.getTime();
        // 若控制器设备的有效期 < 2019年
        if (expiredTime.before(setExpiredTime)) {
            try {
                // 则发送指令：设置控制器的有效期为当前时间
                ctx.getCommandSender().setExpiredTime(setExpiredTime);
                ctx.logInfo("GetExpiredTimeResponseHandler setting device expiredTime, expiredTime : {}", ctx.fullDateSDF.format(setExpiredTime));
            } catch (SendCommandException e) {
                ctx.logError("GetExpiredTimeResponseHandler setting device expiredTime fail , error message : {}", e);
            }
        }
        super.notifyListeners(ctx, msg);
    }
}
