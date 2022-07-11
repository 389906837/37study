package com.cloud.cang.api.antbox.command;


import io.netty.buffer.ByteBuf;

public class OpendoorCommand extends AntboxSpecialCommand {

    public OpendoorCommand() {
        super();
        setCmdCode(OPENDOOR);
    }

    public OpendoorCommand(/* int rollCode, */ ByteBuf sessionId) {
        super(/* rollCode, */ sessionId, OPENDOOR);
    }

}
