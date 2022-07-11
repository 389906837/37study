package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import io.netty.buffer.ByteBuf;

public class GetExpiredTimeCommand extends AntboxStandardCommand implements ToClientDataPkg {

    public GetExpiredTimeCommand() {
        super();
        setCmdCode(GET_EXPIRED_TIME);
    }

    public GetExpiredTimeCommand(/* int rollCode, */ ByteBuf s1_arr) {
        super(/* rollCode, */ GET_EXPIRED_TIME, s1_arr);
    }
}
