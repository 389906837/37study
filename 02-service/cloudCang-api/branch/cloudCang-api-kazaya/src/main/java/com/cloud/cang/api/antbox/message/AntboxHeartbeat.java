package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.command.AntboxHeartbeatAck;
import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxAck;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * 售货机心跳包对象
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxHeartbeat extends AntboxObject implements ToServerDataPkg, AckableMessage {

    /**
     * Time：6个字节，当前系统实时时间。从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
     */
    private Date boxTime;

    public AntboxHeartbeat() {
        setPkgType(TYPE_HEARTBEAT);
    }

    @Override
    public void setData(ByteBuf data) {
        if (!AntboxUtil.hasLength(data)) {
            return;
        }
        this.boxTime = AntboxUtil.readDate(data);
    }


    @Override
    public ByteBuf createData() {
        ByteBuf buf = Unpooled.buffer(6);
        AntboxUtil.writeDate(boxTime, buf);
        // this.writeBoxSn(buf);
        // buf.writeByte(this.boxStatus);
        // buf.writeByte(this.messageStatus);
        // buf.writeBytes(this.reserved.duplicate());
        return buf;
    }

    @Override
    protected String stringOfData() {
        StringBuilder b = new StringBuilder("[");
        b.append("boxTime=").append(boxTime);
        b.append("]");
        return b.toString();
    }

    public Date getBoxTime() {
        return boxTime;
    }

    public void setBoxTime(Date boxTime) {
        this.boxTime = boxTime;
    }

    @Override
    public AntboxAck ackObject(short status) {
        return new AntboxHeartbeatAck(getRollCode(), status);
    }

}
