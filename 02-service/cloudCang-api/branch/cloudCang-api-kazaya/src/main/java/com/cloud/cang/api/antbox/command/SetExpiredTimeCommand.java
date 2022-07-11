package com.cloud.cang.api.antbox.command;

import io.netty.buffer.ByteBuf;

import java.util.Date;

public class SetExpiredTimeCommand extends SetClockCommand {

    public SetExpiredTimeCommand() {
        super();
        setCmdCode(SET_EXPIRED_TIME);
    }

    public SetExpiredTimeCommand(/* int rollCode, */ ByteBuf s1_arr, long sn, Date expiredTime) {
        super(/* rollCode, */ s1_arr, sn, expiredTime);
        setCmdCode(SET_EXPIRED_TIME);
    }
}
