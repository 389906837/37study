package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;

public class OpenTimeoutMessage extends AntboxNoDataMessage {

    public OpenTimeoutMessage() {
        super();
        setMsgCode(MSG_OPEN_TIMEOUT);
    }

    public OpenTimeoutMessage(/*int rollCode, */ByteBuf sessionId, short eventNo) {
        super(/*rollCode,*/ sessionId, eventNo, MSG_OPEN_TIMEOUT);
    }

}
