package com.cloud.cang.api.antbox;


import com.cloud.cang.api.antbox.command.*;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.constant.ParamKey;
import com.cloud.cang.api.antbox.exception.BoxStatusLimitedException;
import com.cloud.cang.api.antbox.exception.SendCommandException;
import com.cloud.cang.api.antbox.message.GetServerConfigResponse;
import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.Date;

public class CommandSender {
    private transient BoxContext ctx;

    CommandSender(BoxContext ctx) {
        this.ctx = ctx;
    }

    /**
     * 向售货机发送数据包。<br>
     * 统一处理：超时重试、rollCode
     *
     * @throws SendCommandException
     */
    public void sendCommand(ToClientDataPkg cmd) throws SendCommandException {

        final boolean waitAck = cmd instanceof AckableMessage;
        try {
            // 若是开门指令，非登录状态不允许发送
            checkStatusBeforSendCommand(cmd);
            if (!waitAck) { // 发送不需要等待ACK确认包的消息
                // System.out.println("服务端发送指令："+ ByteBufUtil.hexDump(cmd.toByteBuf()));
                ctx.logInfo("send non-ackable command[{}] {}", cmd.getClass().getSimpleName(), cmd);
                ctx.writeAndFlush(cmd);
            } else {
                // 设置rollCode
                cmd.setRollCode(ctx.increaseAndGetRollCode());
                //System.out.println("服务端发送指令："+ ByteBufUtil.hexDump(cmd.toByteBuf()));
                ctx.logInfo("send AckableMessage[{}] {}", cmd.getClass().getSimpleName(), cmd);
                // 这里使用子线程处理ackable命令发送
                AntboxConfig.nettyCommandSenderExecutorService.execute(() -> sendAckableCommand((AckableMessage) cmd));
            }
        } catch (Exception e) {
            ctx.logError(e, "send command[{}] fail, {}", cmd.getClass().getSimpleName(), cmd);
            throw new SendCommandException(e.getMessage(), e);
        }
    }

    private void checkStatusBeforSendCommand(ToClientDataPkg cmd) throws BoxStatusLimitedException {
        if (cmd instanceof OpendoorCommand) {
            // 状态检查 非登录状态不能发送开门指令
            if (!ctx.isAllowOpenDoorNow())
                throw new BoxStatusLimitedException(
                        "Unable to orderSend command[openDoor] in status: Non-logged", BoxStatus.LOGIN,ctx.getBoxInfo().getStatus());
        }

        // ack状态检查: 由于有ackLock同步，无需检查
    }

    public void inventory() throws SendCommandException {
        sendCommand(new InventoryCommand(ctx.sessionIdByteBuf()));
    }

    public synchronized void openDoor() throws SendCommandException {
        sendCommand(new OpendoorCommand(ctx.sessionIdByteBuf()));
    }

    public void getBoxInfo() throws SendCommandException {
        sendCommand(new GetBoxInfoCommand());
    }

    public void getExpiredTime() throws SendCommandException {
        sendCommand(new GetExpiredTimeCommand(ctx.getDynamicSecretKey()));
    }

    public void send(GetServerConfigResponse rsp) throws SendCommandException {
        sendCommand(rsp);
    }

    public void playAudio(short audioNo) throws SendCommandException {
        sendCommand(new PlayAudioCommand(audioNo));

    }

    public void setClock(Date time) throws SendCommandException {
        sendCommand(new SetClockCommand(ctx.getDynamicSecretKey(), ctx.getBoxInfo().getBoxId(), time));
    }

    public void setExpiredTime(Date expiredTime) throws SendCommandException {
        sendCommand(
                new SetExpiredTimeCommand(ctx.getDynamicSecretKey(), ctx.getBoxInfo().getBoxId(), expiredTime));
    }

    /**
     * 获取售货机设置参数
     *
     * @param paramKeys
     * @throws BoxStatusLimitedException
     */
    public void getDeviceParam(ParamKey... paramKeys) throws SendCommandException {
        byte[] keys = new byte[paramKeys.length];
        for (int i = 0; i < paramKeys.length; i++) {
            keys[i] = paramKeys[i].getCode();
        }
        sendCommand(new GetParamCommand(Unpooled.wrappedBuffer(keys)));
        ctx.addFirstToGetParamQueue(Lists.newArrayList(paramKeys));
    }

    /**
     * 设置售货机参数
     *
     * @param paramKey   参数名称 @see ParamKey
     * @param paramValue
     * @throws BoxStatusLimitedException
     */
    public void setDeviceParam(ParamKey paramKey, short paramValue) throws SendCommandException {
        ByteBuf cmdData = Unpooled.buffer(2);
        cmdData.writeByte(paramKey.getCode());
        cmdData.writeByte(paramValue);
        this.sendCommand(new SetParamCommand(cmdData));
    }

    /**
     * 设置售货机参数
     *
     * @param paramKey   参数名称 @see ParamKey
     * @param paramValue
     * @throws BoxStatusLimitedException
     */
    public void setDeviceParam(ParamKey paramKey, byte[] paramValue) throws SendCommandException {
        ByteBuf cmdData = Unpooled.buffer(1 + paramValue.length);
        cmdData.writeByte(paramKey.getCode());
        cmdData.writeBytes(paramValue);
        this.sendCommand(new SetParamCommand(cmdData));
    }

    private void sendAckableCommand(final AckableMessage cmd) {
        // 滚码放入集合
        ctx.markAck(cmd.getRollCode());
        try {
            for (int i = 0; i < 10; i++) {
                ctx.writeAndFlush(cmd);
                Thread.sleep(500);
                if (!ctx.isWaitingAck(cmd.getRollCode())) {
                    break;
                }
            }
        } catch (Exception e) {
            ctx.logError(e, "send AckableMessage[{}] fail, {}", cmd.getClass().getSimpleName(), cmd);
        } finally {
//            ctx.ackUnlock();
        }
    }

    /**
     * 重启设备
     */
//    public void reboot(){
//        this.ctx.getChannelContext().writeAndFlush(Command.valueOf("off1").toByteBuf());
//    }

    private String leftpad2(int a) {
        return a < 10 ? "0" + a : String.valueOf(a);
    }
}
