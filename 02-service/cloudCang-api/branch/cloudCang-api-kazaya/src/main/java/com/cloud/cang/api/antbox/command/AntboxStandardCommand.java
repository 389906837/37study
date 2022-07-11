package com.cloud.cang.api.antbox.command;


import com.cloud.cang.api.antbox.protocol.AntboxStandardObject;
import io.netty.buffer.ByteBuf;

public abstract class AntboxStandardCommand extends AntboxStandardObject {

    public AntboxStandardCommand() {
        super();
    }

    public AntboxStandardCommand(/*int rollCode,*/  short cmdCode, ByteBuf cmdData) {
        super(/*rollCode,*/  cmdCode, cmdData);
    }

    @Override
    protected boolean hasStatus() {
        return false;
    }

}
