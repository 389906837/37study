package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import io.netty.buffer.ByteBuf;

public class GetExpiredTimeResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public GetExpiredTimeResponse() {
        super();
        setCmdCode(GET_EXPIRED_TIME);
    }

    public GetExpiredTimeResponse(int rollCode, short status, ByteBuf cmdData) {
        super(/*rollCode,*/  GET_EXPIRED_TIME, status, cmdData);
    }

}
