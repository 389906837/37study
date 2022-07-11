package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;

public abstract class AntboxNoDataMessage extends AntboxMessage {

    public AntboxNoDataMessage() {
        super();
    }

    public AntboxNoDataMessage(/*int rollCode,*/ ByteBuf sessionId, short eventNo, short msgCode) {
        super(/*rollCode, */sessionId, eventNo, msgCode, null);
    }

}
