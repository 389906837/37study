package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;

public class SetExpiredTimeResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public SetExpiredTimeResponse() {
        super();
        setCmdCode(SET_EXPIRED_TIME);
    }

    public SetExpiredTimeResponse(int rollCode, short status) {
        super(/*rollCode,*/  SET_EXPIRED_TIME, status, null);
    }

}
