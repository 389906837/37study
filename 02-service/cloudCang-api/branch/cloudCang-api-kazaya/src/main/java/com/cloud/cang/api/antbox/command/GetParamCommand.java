package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import io.netty.buffer.ByteBuf;

public class GetParamCommand extends AntboxStandardCommand implements ToClientDataPkg {

    public GetParamCommand() {
        super();
        setCmdCode(GET_PARAM);
    }

    public GetParamCommand(/* int rollCode, */ ByteBuf cmdData) {
        super(/* rollCode, */ GET_PARAM, cmdData);
    }

}
