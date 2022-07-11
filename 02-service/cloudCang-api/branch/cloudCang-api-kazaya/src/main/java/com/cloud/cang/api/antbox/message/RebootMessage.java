package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.constant.CommandStatus;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;

public class RebootMessage extends AntboxMessage {
    public static final byte MODE_COLD = 0;

    public static final byte MODE_HOT = 1;

    public RebootMessage() {
        super();
        setMsgCode(MSG_REBOOT);
    }

    public RebootMessage(/*int rollCode, */ByteBuf sessionId, short eventNo, byte mode) {
        super(/*rollCode,*/ sessionId, eventNo, MSG_REBOOT, null);
        this.setMode(mode);
    }

    public void setMode(byte mode) {
        this.setMsgData(AntboxUtil.byteWrap(mode));
    }

    public short getMode() {
        return AntboxUtil.getUnsignedByteAt(getMsgData(), 0, CommandStatus.UNKNOWN);
    }

}
