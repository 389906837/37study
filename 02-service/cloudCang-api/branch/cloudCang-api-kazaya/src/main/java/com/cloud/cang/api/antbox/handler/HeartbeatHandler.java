package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.AntboxHeartbeat;
import com.cloud.cang.api.antbox.util.RelayServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 心跳 >>>
 */
@Component
public class HeartbeatHandler extends BoxMessageHandler<AntboxHeartbeat, AntboxHeartbeat> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(BoxContext ctx, AntboxHeartbeat msg) {
        ctx.pulsating();
        ctx.setBoxLocalTime(msg.getBoxTime());
        Long boxId = ctx.getBoxInfo().getBoxId();

        if (boxId == null || /*prop.nettyServerDebugMode*/ false) {
            try {
                // 发送：获取系统信息指令
                ctx.getCommandSender().getBoxInfo();
                // 发送：命令获取设备的有效使用期限
                ctx.getCommandSender().getExpiredTime();
            } catch (SendCommandException e) {
                e.printStackTrace();
            }
        }
        // 临时监控模块
        /*try {
            MonitorDto monitorDto = null;
            String monitorValue = redisTemplate.boundValueOps("server_monitor_" + boxId).get();
            if (StringUtils.isNotBlank(monitorValue)) {
                monitorDto = Utils.objectMapper.readValue(monitorValue, MonitorDto.class);
            }
            if (monitorDto == null) {
                monitorDto = new MonitorDto();
                monitorDto.setStatus(true);
            }
            Long currentTimeMillis = System.currentTimeMillis();
            monitorDto.setDealWithTimeList(new ArrayList<>());
            monitorDto.setHeartbeatTime(currentTimeMillis);
            // 记录设备状态
            if (monitorDto.getBoxStatus() != ctx.getBoxInfo().getStatus()) {
                monitorDto.setBoxStatus(ctx.getBoxInfo().getStatus());
                monitorDto.setBoxStatusTime(currentTimeMillis);
            }

            String value = Utils.objectMapper.writeValueAsString(monitorDto);
            redisTemplate.boundValueOps("server_monitor_" + boxId).set(value);
        } catch (Exception e) {
            ctx.logError(e.getMessage());
            e.printStackTrace();
        }*/


        if (ctx.getBoxInfo().getStatus() == BoxStatus.BUSY) {// BUYS状态计数器
            ctx.setBusyStatusCounter(ctx.getBusyStatusCounter() + 1);
            ctx.setLoginStatusCounter(0);
            ctx.setInitStatusCounter(0);
            this.resetStatus(ctx.getBusyStatusCounter(), 20, ctx);
        } else if (ctx.getBoxInfo().getStatus() == BoxStatus.LOGIN) {// LOGIN状态计数器
            ctx.setLoginStatusCounter(ctx.getLoginStatusCounter() + 1);
            ctx.setBusyStatusCounter(0);
            ctx.setInitStatusCounter(0);
            this.resetStatus(ctx.getLoginStatusCounter(), ctx);
        } else if (ctx.getBoxInfo().getStatus() == BoxStatus.INIT) {// INIT状态计数器
            ctx.setInitStatusCounter(ctx.getInitStatusCounter() + 1);
            ctx.setLoginStatusCounter(0);
            ctx.setBusyStatusCounter(0);
            this.resetStatus(ctx.getInitStatusCounter(), ctx);
        } else {
            ctx.setBusyStatusCounter(0);
            ctx.setLoginStatusCounter(0);
            ctx.setInitStatusCounter(0);
        }
        super.notifyListeners(ctx, msg);
    }

    private void resetStatus(int statusCounter, BoxContext ctx) {
        resetStatus(statusCounter, 10, ctx);
    }

    private void resetStatus(int statusCounter, int maxCounter, BoxContext ctx) {
        if (statusCounter > maxCounter) {
            RelayServerUtils.reboot(ctx.getBoxInfo().getBoxId(), log);
            // todo
            //ctx.getCommandSender().reboot();
            ctx.logInfo("fix the fxxking error box status , boxId : {}", ctx.getBoxInfo().getBoxId());
            ctx.getBoxInfo().setBoxId(null);
            ctx.setStatus(BoxStatus.INIT);
            ctx.setBusyStatusCounter(0);
            ctx.setLoginStatusCounter(0);
            ctx.setInitStatusCounter(0);
        }
    }
}
