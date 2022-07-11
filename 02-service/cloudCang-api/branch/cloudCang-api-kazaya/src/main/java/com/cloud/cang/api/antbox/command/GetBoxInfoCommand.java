package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;


public class GetBoxInfoCommand extends AntboxStandardCommand implements ToClientDataPkg {

    public GetBoxInfoCommand() {
        super(GET_BOX_INFO, null);
    }

}
