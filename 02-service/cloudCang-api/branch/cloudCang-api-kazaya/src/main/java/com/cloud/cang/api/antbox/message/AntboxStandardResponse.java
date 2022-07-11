package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.AntboxStandardObject;
import io.netty.buffer.ByteBuf;

public abstract class AntboxStandardResponse extends AntboxStandardObject {

    public AntboxStandardResponse() {
        super();
    }

    public AntboxStandardResponse(/* int rollCode, */ short cmdCode, short status, ByteBuf cmdData) {
        super(/* rollCode, */ cmdCode, cmdData);
        setStatus(status);
    }

    @Override
    protected boolean hasStatus() {
        return true;
    }

}
