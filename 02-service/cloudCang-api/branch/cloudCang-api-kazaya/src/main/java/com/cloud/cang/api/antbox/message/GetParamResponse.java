package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import io.netty.buffer.ByteBuf;

public class GetParamResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public GetParamResponse() {
        super();
        setCmdCode(GET_PARAM);
    }

    public GetParamResponse(int rollCode, short status, ByteBuf cmdData) {
        super(/*rollCode,*/  GET_PARAM, status, cmdData);
    }

}
