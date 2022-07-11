package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

public class AntboxSpecialCommand extends AntboxObject implements ToClientDataPkg, AckableMessage {
    /**
     * 开门
     */
    public static final short OPENDOOR = 0x01;

    /**
     * 盘点
     */
    public static final short INVENTORY = 0x02;

    // Time：6个字节，时间戳。从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
    private Date time = new Date();

    // SessionID：16个字节，由服务器定义。
    private ByteBuf sessionId;

    // CmdCode：1个字节。指令代码，详见下文定义。
    private short cmdCode;

    // cmdData --

    public AntboxSpecialCommand() {
        super();
        setPkgType(TYPE_SPECIAL);
    }

    protected AntboxSpecialCommand(/* int rollCode, */ ByteBuf sessionId, short cmdCode) {
        super(/* rollCode, */ TYPE_SPECIAL, null);
        this.sessionId = sessionId;
        this.cmdCode = cmdCode;
    }

    @Override
    protected ByteBuf createData() {
        AntboxUtil.checkLength(sessionId, SESSION_ID_BYTES_NUM);

        ByteBuf d = Unpooled.buffer(6 + SESSION_ID_BYTES_NUM + 1 + 0);
        AntboxUtil.writeDate(time, d);// time
        d.writeBytes(sessionId, 0, SESSION_ID_BYTES_NUM);
        d.writeByte(cmdCode);

        return d;
    }

    @Override
    protected void setData(ByteBuf data) {
        if (!AntboxUtil.hasLength(data)) {
            return;
        }
        this.time = AntboxUtil.readDate(data);
        this.sessionId = data.readBytes(SESSION_ID_BYTES_NUM);
        this.cmdCode = data.readUnsignedByte();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setSessionId(ByteBuf sessionId) {
        this.sessionId = sessionId;
    }

    protected void setCmdCode(short cmdCode) {
        this.cmdCode = cmdCode;
    }

    public Date getTime() {
        return time;
    }

    public ByteBuf getSessionId() {
        return sessionId;
    }

    public short getCmdCode() {
        return cmdCode;
    }

}
