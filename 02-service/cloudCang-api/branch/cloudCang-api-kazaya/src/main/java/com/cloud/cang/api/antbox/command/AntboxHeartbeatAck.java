package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.AntboxAck;


/**
 * 针对心跳的ACK
 *
 * @author yong.ma
 * @since 1.3
 */
public class AntboxHeartbeatAck extends AntboxAck {

    public AntboxHeartbeatAck() {
        super();
    }

    public AntboxHeartbeatAck(int rollCode, short status) {
        super(rollCode, status);
    }

}
