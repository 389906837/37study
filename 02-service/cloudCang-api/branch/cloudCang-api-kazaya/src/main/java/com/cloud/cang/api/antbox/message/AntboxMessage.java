package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.AckableMessage;
import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * 售货机消息响应对象。
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxMessage extends AntboxObject implements ToServerDataPkg, AckableMessage {
    /**
     * 超时上锁消息 <br>
     * 当控制器接收到开门指令后，在超时上锁时间内门锁没有被打开，则锁芯弹出并产生一条超时上锁消息
     */
    public static final byte MSG_UNLOCK_TIMEOUT = 0x01;

    // /**
    // * 开关门消息
    // */
    // byte SWITCH = 0x02;

    /**
     * 开门消息<br>
     * 当控制器接收到开门指令后，如果门锁被打开，系统进入开门状态，同时产生一条开门消息。
     */
    public static final byte MSG_SWITCH_ON = 0x02;

    /**
     * 关门消息<br>
     * 门锁打开后再次关闭时（正常流程或者防作弊检测中），系统进入盘点状态，启动一轮盘点的同时产生一条关门消息
     */
    public static final byte MSG_SWITCH_OFF = 0x03;

    /**
     * 开门超时消息<br>
     * 当控制器接收到开门指令且门锁打开后，在定义的时间内门锁没有被关闭，则产生一条开门超时消息。
     */
    public static final byte MSG_OPEN_TIMEOUT = 0x04;

    // /**
    // * 卡号消息
    // */
    // byte CARD = 0x03;

    /**
     * 盘点消息<br>
     * 盘点消息产生于一轮盘点结束时，控制器将读取到的标签UID存入消息池。
     * 如果一轮盘点结束而未获得任何UID时， 控制器也将产生一条张数为0的盘点消息。
     */
    public static final byte MSG_INVENTORIED = 0x05;

    // /**
    // * 强制盘点
    // */
    // byte COMPULSORYINVENTORY = 0x12;

    /**
     * 设备重启消息<br>
     * 控制器上电启动后或者因某种原因复位而重新启动后，将产生一条设备重启消息。
     */
    public static final byte MSG_REBOOT = 0x06;

    /**
     * 断电检测消息<br>
     * 控制器检测到交流输入220Vac状态有变化时，将产生一条断电检测消息
     */
    public static final byte MSG_AC_CHECK = 0x07;

    // --------------------------------

    static final int UID_BYTES_NUM = 8;

    private Date msgTime = new Date();// 6 bytes
    private ByteBuf sessionId;// 16 bytes
    private short eventNo;// 1 byte
    private short msgCode;// 1 byte
    private ByteBuf msgData;// nullable, 0~n bytes

    public AntboxMessage() {
        super();
        setPkgType(TYPE_MESSAGE);
    }

    protected AntboxMessage(/* int rollCode, */ ByteBuf sessionId, short eventNo, short msgCode, ByteBuf msgData) {
        super(/* rollCode, */TYPE_MESSAGE, null);
        this.sessionId = sessionId;
        this.eventNo = eventNo;
        this.msgCode = msgCode;
        this.setMsgData(msgData);
    }

    @Override
    public void setData(ByteBuf data) {
        if (!AntboxUtil.hasLength(data)) {
            return;
        }
        this.msgTime = AntboxUtil.readDate(data);
        this.sessionId = data.readBytes(16);
        this.eventNo = data.readUnsignedByte();
        this.msgCode = data.readUnsignedByte();
        if (data.isReadable()) {
            this.setMsgData(data);
        }
    }

    protected ByteBuf createData() {
        ByteBuf data = Unpooled.buffer(6 + 16 + 1 + 1 + AntboxUtil.length(msgData));
        AntboxUtil.writeDate(this.msgTime, data);
        data.writeBytes(sessionId);
        data.writeByte(eventNo);
        data.writeByte(msgCode);
        if (msgData != null && msgData.isReadable()) {
            data.writeBytes(msgData);
        }

        return data;
    }

    @Override
    protected String stringOfData() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("msgTime=").append(this.msgTime).append(", ");
        sb.append("sessionId=0x").append(ByteBufUtil.hexDump(sessionId)).append(", ");
        sb.append("eventNo=").append(this.eventNo).append(", ");
        sb.append("msgCode=0x").append(Integer.toHexString(this.msgCode)).append(", ");
        sb.append("msgData=").append(stringOfMsgData());
        sb.append("]");

        return sb.toString();
    }

    protected String stringOfMsgData() {
        if (msgData == null || !msgData.isReadable()) {
            return "--";
        }
        return "0x" + ByteBufUtil.hexDump(msgData);
    }

    public Date getMsgTime() {
        return msgTime;
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

    public ByteBuf getMsgData() {
        return msgData;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public void setSessionId(ByteBuf sessionId) {
        this.sessionId = sessionId;
    }

    public void setEventNo(short eventNo) {
        this.eventNo = eventNo;
    }

    protected void setMsgCode(short msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * 钩子方法。用于解析msgData
     */
    protected void setMsgData(ByteBuf msgData) {
        this.msgData = msgData;
    }

}
