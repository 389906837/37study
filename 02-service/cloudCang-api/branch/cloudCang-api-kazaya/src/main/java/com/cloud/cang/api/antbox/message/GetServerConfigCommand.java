package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.command.AntboxStandardCommand;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;


public class GetServerConfigCommand extends AntboxStandardCommand implements ToServerDataPkg {

    public GetServerConfigCommand() {
        super(/* rollCode, */ GET_SERVER_CONFIG, null);
    }

}
