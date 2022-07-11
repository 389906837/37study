package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;

public class UnlockTimeoutMessage extends AntboxNoDataMessage {

    public UnlockTimeoutMessage() {
        super();
        setMsgCode(MSG_UNLOCK_TIMEOUT);
    }

    public UnlockTimeoutMessage(/*int rollCode, */ByteBuf sessionId, short eventNo) {
        super(/*rollCode,*/ sessionId, eventNo, MSG_UNLOCK_TIMEOUT);
    }

}
