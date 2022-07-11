package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * 消息已处理数据包
 */
public class AntboxMessageProcessed extends AntboxObject implements ToClientDataPkg, AckableMessage {

    // Time：6个字节，时间戳。从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
    private Date time = new Date();

    // SessionID：16个字节，由服务器定义。
    private ByteBuf sessionId;

    // EventNo：1个字节，事件序号，用于指示已处理的具体事件。序号范围从0～255。和已处理的消息数据包的事件序号相同。
    private short eventNo;

    // MsgCode：1个字节。消息类型
    private short msgCode;

    public AntboxMessageProcessed() {
        super();
        setPkgType(TYPE_PROCESSED);
    }

    public AntboxMessageProcessed(/* int rollCode, */ ByteBuf sessionId, short eventNo, short msgCode) {
        super(/* rollCode, */TYPE_PROCESSED, null);
        this.sessionId = sessionId;
        this.eventNo = eventNo;
        this.msgCode = msgCode;
    }

    @Override
    protected ByteBuf createData() {
        AntboxUtil.checkLength(sessionId, SESSION_ID_BYTES_NUM);

        ByteBuf d = Unpooled.buffer(6 + SESSION_ID_BYTES_NUM + 1 + 1);
        AntboxUtil.writeDate(time, d);
        d.writeBytes(sessionId, 0, SESSION_ID_BYTES_NUM);
        d.writeByte(eventNo);
        d.writeByte(msgCode);

        return d;
    }

    @Override
    protected void setData(ByteBuf data) {
        if (!AntboxUtil.hasLength(data)) {
            return;
        }
        this.time = AntboxUtil.readDate(data);
        this.sessionId = data.readBytes(SESSION_ID_BYTES_NUM);
        this.eventNo = data.readUnsignedByte();
        this.msgCode = data.readUnsignedByte();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setSessionId(ByteBuf sessionId) {
        this.sessionId = sessionId;
    }

    public void setEventNo(short eventNo) {
        this.eventNo = eventNo;
    }

    public void setMsgCode(short msgCode) {
        this.msgCode = msgCode;
    }

    public Date getTime() {
        return time;
    }

    public ByteBuf getSessionId() {
        return sessionId;
    }

    public short getEventNo() {
        return eventNo;
    }

    public short getMsgCode() {
        return msgCode;
    }

    @Override
    protected String stringOfData() {
        StringBuilder b = new StringBuilder();
        b.append("[time=").append(this.time).append(", ");
        b.append("sessionId=0x").append(ByteBufUtil.hexDump(this.sessionId)).append(", ");
        b.append("eventNo=").append(this.eventNo).append(", ");
        b.append("msgCode=").append(this.msgCode).append("]");
        return b.toString();
    }

}
