package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;

public class SetParamResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public SetParamResponse() {
        super();
        setCmdCode(SET_PARAM);
    }

    public SetParamResponse(int rollCode, short status) {
        super(/*rollCode,*/  SET_PARAM, status, null);
    }

}
