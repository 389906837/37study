package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import io.netty.buffer.ByteBuf;

public class SetParamCommand extends AntboxStandardCommand implements ToClientDataPkg {

    public SetParamCommand() {
        super();
        setCmdCode(SET_PARAM);
    }

    public SetParamCommand(/*int rollCode,*/  ByteBuf cmdData) {
        super(/*rollCode,*/  SET_PARAM, cmdData);
    }
}
