package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;

public class SwitchOnMessage extends AntboxNoDataMessage {

    public SwitchOnMessage() {
        super();
        setMsgCode(MSG_SWITCH_ON);
    }

    public SwitchOnMessage(/*int rollCode,*/ ByteBuf sessionId, short eventNo) {
        super(/*rollCode,*/ sessionId, eventNo, MSG_SWITCH_ON);
    }

}
