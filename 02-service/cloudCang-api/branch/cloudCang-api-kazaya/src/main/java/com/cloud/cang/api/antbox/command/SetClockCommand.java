package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import com.cloud.cang.api.antbox.util.AntboxEncryptor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

public class SetClockCommand extends AntboxStandardCommand implements ToClientDataPkg {
    private ByteBuf s1_arr;
    private long sn;
    private Date time;

    public SetClockCommand() {
        super();
        setCmdCode(SET_CLOCK);
    }

    public SetClockCommand(/* int rollCode, */ ByteBuf s1_arr, long sn, Date time) {
        super(/* rollCode, */ SET_CLOCK, null);
        this.s1_arr = s1_arr;
        this.sn = sn;
        this.time = time;
    }

    @Override
    protected ByteBuf createData() {
        ByteBuf cmdData = Unpooled.buffer(24);
        cmdData.writeBytes(s1_arr);
        cmdData.writeBytes(AntboxEncryptor.getInstance().encrypt(s1_arr.array(), sn, time));
        setCmdData(cmdData);
        return super.createData();
    }

    public ByteBuf getS1_arr() {
        return s1_arr;
    }

    public void setS1_arr(ByteBuf s1_arr) {
        this.s1_arr = s1_arr;
    }

    public long getSn() {
        return sn;
    }

    public void setSn(long sn) {
        this.sn = sn;
    }

    public Date getExpiredTime() {
        return time;
    }

    public void setExpiredTime(Date expiredTime) {
        this.time = expiredTime;
    }

}
