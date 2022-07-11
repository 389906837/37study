package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;

public class SwitchOffMessage extends AntboxNoDataMessage {

    public SwitchOffMessage() {
        super();
        setMsgCode(MSG_SWITCH_OFF);
    }

    public SwitchOffMessage(/*int rollCode,*/ ByteBuf sessionId, short eventNo) {
        super(/*rollCode,*/ sessionId, eventNo, MSG_SWITCH_OFF);
    }

}
