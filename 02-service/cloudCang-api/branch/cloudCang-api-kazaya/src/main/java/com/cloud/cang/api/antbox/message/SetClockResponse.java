package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;

public class SetClockResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public SetClockResponse() {
        super();
        setCmdCode(SET_CLOCK);
    }

    public SetClockResponse(int rollCode, short status) {
        super(/*rollCode,*/  SET_CLOCK, status, null);
    }

}
