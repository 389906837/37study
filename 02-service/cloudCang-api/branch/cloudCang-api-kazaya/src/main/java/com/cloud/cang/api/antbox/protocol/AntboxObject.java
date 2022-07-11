package com.cloud.cang.api.antbox.protocol;

import com.cloud.cang.api.antbox.command.*;
import com.cloud.cang.api.antbox.constant.DecoderMode;
import com.cloud.cang.api.antbox.exception.UnexpectedCrcException;
import com.cloud.cang.api.antbox.exception.UnexpectedHeadException;
import com.cloud.cang.api.antbox.exception.UnknownCmdCodeException;
import com.cloud.cang.api.antbox.exception.UnknownPkgTypeException;
import com.cloud.cang.api.antbox.message.*;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * 自动售货机请求/响应数据对象(顶层抽象类).
 * <p>
 * CLIENT-SERVER通讯协议：
 *
 * <pre>
 * 数据块的格式
 * STX	MSB-Len16	LSB-Len16	RollCode16	PkgType	Data[]	LSB-CRC16	MSB-CRC16
 * STX：长度为1个字节的起始符。固定取值0x7E。
 * Len16：长度为2个字节的数据块长度（包括从MSB-Len16到MSB-CRC16的所有字节）。高字节在前。
 * RollCode16：长度为2个字节的滚码，高字节在前。滚码范围从0～65535。初始值为0，以后每发送一个数据包，该值加1，不管是新的还是重发的。当滚码超过65535后，重新从0开始。上位机和控制器分别对各自发送的数据包单独滚码。对于ACK数据包，其滚码需和待ACK确认的数据包的滚码相同，详见ACK数据包一节。
 * PkgType：长度为1个字节的数据包类型。
 * Data[]：数据块数据。
 * CRC16：长度为2个字节的CRC-16校验和。低字节在前。CRC计算包括了从MSB-Len16开始到Data[]的全部数据。
 * </pre>
 *
 * @author yong.ma
 * @since 0.0.1
 */
public abstract class AntboxObject implements AntboxDataPkg {

    private short head = HEADER_STX;

    /**
     * BODY长度（包括LEN字段）
     */
    private int bodyLen;

    /**
     * 长度为2个字节的滚码，高字节在前。滚码范围从0～65535。初始值为0，以后每发送一个数据包，该值加1，不管是新的还是重发的。
     * 当滚码超过65535后，重新从0开始。上位机和控制器分别对各自发送的数据包单独滚码。对于ACK数据包，其滚码需和待ACK确认的数据包的滚码相同
     */
    private int rollCode;

    /**
     * 长度为1个字节的数据包类型
     */
    private short pkgType = TYPE_UKNOWN;

    /**
     * 命令数据/响应数据
     */
    private ByteBuf data;

    /**
     * 循环冗余码校验（CRC）<br>
     * 长度为2个字节的CRC-16效验和,低字节在前.<br>
     * 计算包括了从MSB-LEN16开始到Data[]的全部数据，得到的CRC在传送时低字节在前。
     */
    private int crc = NO_CRC;

    /**
     * 注：该类及其子孙类务必保留无参构造函数
     *
     * @see #copyFrom(AntboxObject)
     */
    public AntboxObject() {

    }

    protected AntboxObject(/* int rollCode, */ short pkgType, ByteBuf data) {
        // this.rollCode = rollCode;
        this.pkgType = pkgType;
        this.setData(data);
    }

    private int bodyLen() {
        if (bodyLen == 0) {
            bodyLen = bufLen() - LENGTH_FIELD_OFFSET;
        }
        return bodyLen;
    }

    private int bufLen = 0;

    /**
     * 缓冲区长度
     */
    private int bufLen() {
        if (bufLen > 0) {
            return bufLen;
        }

        // LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
        // rollCode(2 bytes), pkgType(1 bytes), crc(2 bytes)
        int n = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH + 2 + 1 + 2;

        // if (hasStatus()) {// status
        // n++;
        // }
        // data
        n += AntboxUtil.length(getData());

        bufLen = n;
        return bufLen;
    }

    public short getHead() {
        return head;
    }

    // public short getAddr() {
    // return addr;
    // }
    //
    // public short getCommand() {
    // return command;
    // }
    //
    // public void setCommand(short command) {
    // this.command = command;
    // }

    @Override
    public int getRollCode() {
        return rollCode;
    }

    @Override
    public void setRollCode(int rollCode) {
        this.rollCode = rollCode;
    }

    public short getPkgType() {
        return pkgType;
    }

    protected void setPkgType(short pkgType) {
        this.pkgType = pkgType;
    }

    /**
     * 钩子方法。用于构建数据包
     *
     */
    protected ByteBuf createData() {
        return null;
    }

    /**
     * 钩子方法。用于解析数据包
     */
    protected void setData(ByteBuf data) {
        this.data = data;
    }

    protected ByteBuf getData() {
        if (data == null) {
            data = createData();
        }
        return data;
    }

    protected String stringOfData() {
        if (getData() == null) {
            return "";
        }

        return "0x" + ByteBufUtil.hexDump(data);
    }

    public int getCrc() {
        return crc;
    }

    /**
     * CRC校验
     */
    @Override
    public void checkCrc(final int expected) {
        if (expected != this.crc) {
            throw new UnexpectedCrcException("expected: 0x" + Integer.toHexString(expected)
                    + ", actual: 0x" + Integer.toHexString(this.crc));
        }
    }

    /**
     * 头字节校验
     */
    public void checkHead() {
        if (HEADER_STX != this.head) {
            throw new UnexpectedHeadException("expected: " + HEADER_STX + ", actual: " + this.head);
        }
    }

    @Override
    public ByteBuf toByteBuf() {
        ByteBuf buf = toByteBufWithoutCrc();
        this.crc = AntboxUtil.calcCrc(buf, LENGTH_FIELD_OFFSET); // CRC
        buf.writeShort(this.crc);
        return buf;
    }

    /**
     * Convert to ByteBuf without CRC
     */
    private ByteBuf toByteBufWithoutCrc() {
        ByteBuf buf = Unpooled.buffer(bufLen() + 1);
        writeToWithoutCrc(buf);
        return buf;
    }

    @Override
    public void writeTo(ByteBuf out) {
        writeToWithoutCrc(out);
        this.crc = AntboxUtil.calcCrc(out, LENGTH_FIELD_OFFSET);
        out.writeShort(this.crc);
    }


    /**
     * Write to ByteBuf without CRC
     */
    private void writeToWithoutCrc(ByteBuf out) {
        out.writeByte(HEADER_STX);// header
        out.writeShort(bodyLen());// bodyLen
        out.writeShort(this.rollCode);// rollCode
        out.writeByte(this.pkgType);// pkgType

        if (AntboxUtil.hasLength(getData())) {// data
            out.writeBytes(getData().duplicate());
        }
    }

    @Override
    public AntboxObject parse(ByteBuf buf) {
        this.head = buf.readUnsignedByte();
        this.bodyLen = buf.readUnsignedShort();
        this.rollCode = buf.readUnsignedShort();
        this.pkgType = buf.readUnsignedByte();

        // 2 (bytes) for crc
        int dataLen = buf.readableBytes() - 2;
        if (dataLen > 0) {
            ByteBuf d = Unpooled.buffer(dataLen);
            buf.readBytes(d, dataLen);
            setData(d);
        } else {
            setData(Unpooled.EMPTY_BUFFER);
        }
        this.crc = buf.readUnsignedShort();
        return this;
    }

    public void copyFrom(AntboxObject src) {
        this.head = src.getHead();
        this.bodyLen = src.bodyLen();
        this.rollCode = src.getRollCode();
        this.pkgType = src.getPkgType();
        this.copyDataFrom(src);
        this.crc = src.getCrc();
    }

    private void copyDataFrom(AntboxObject src) {
        if (src.getData() != null) {
            this.setData(src.getData().duplicate());
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(getClass().getSimpleName()).append("[");
        b.append("head=0x").append(Integer.toHexString(getHead())).append(", ");
        b.append("bodyLen=").append(bodyLen()).append(", ");
        b.append("rollCode=").append(getRollCode()).append(", ");
        b.append("pkgType=0x").append(Integer.toHexString(getPkgType())).append(", ");
        b.append("data=").append(stringOfData()).append(", ");
        b.append("crc=0x").append(Integer.toHexString(getCrc()));
        b.append("]");
        return b.toString();
    }

    /**
     * 控制器工作的状态有4种：空闲状态、开门状态和盘点状态、语音更新
     *
     * 关门盘点，指令盘点
     * 盘点消息：1、门锁打开后再次关闭时（正常流程或者防作弊检测中），系统进入盘点状态，启动一轮盘点的同时产生一条关门消息。
     *          2、盘点消息产生于一轮盘点结束时，控制器将读取到的标签UID存入消息池。如果一轮盘点结束而未获得任何UID时，控制器也将产生一条张数为0的盘点消息。
     * ------------------------pkgType---------Data域--------------------------------------
     *    ACK数据包     |       0x02      |     Status
     * -------------------------------------------------------------------------------------
     *   心跳数据包     |       0x05      |     Time
     * --------------------------------------------------------------------------------------
     *   消息数据包     |       0x03      |     Time、SessionID、EventNo、MsgCode、MsgData[]
     *                                                                 0x01超时上锁消息、
     *                                                                 0x02开门消息、
     *                                                                0x03关门消息、
     *                                                                0x04开门超时消息、
     *                                                                0x05盘点消息、
     *                                                                 0x06设备重启消息
     *                                                                 0x07断电检测消息
     *------------------------------------------------------------------------------------
     * 消息已处理数据包 |       0x04      |    Time、SessionID、EventNo、MsgCode
     *                                                                0x01超时上锁消息、
     *                                                                0x02开门消息、
     *                                                                0x03关门消息、
     *                                                                0x04开门超时消息、
     *                                                                0x05盘点消息、
     *                                                                0x06设备重启消息
     *                                                                0x07断电检测消息
     *------------------------------------------------------------------------------------
     *   特殊指令数据包 |       0x01     |   Time、SessionID、CmdCode、CmdData[]
     *                                                      0x01开门指令
     *                                                      0x02盘点指令
     * ------------------------------------------------------------------------------------
     *   标准指令数据包 |       0x06
     *                         命令数据块  Time、CmdCode、CmdData[]
     *                                          0x01获取服务器配置信息指令
     *                                          0x02获取系统信息指令
     *                                          0x21设置系统工作参数指令
     *                                          0x22获取系统工作参数指令
     *                                          0x26播放指定语音指令
     *                                          0x16设置有效使用期限指令
     *                                          0x17获取有效使用期限指令
     *                                          0x18设置系统实时时钟指令
     *                         响应数据块  Time、ReCmdCode、Status、RespData[]
     *                                          0x01获取服务器配置信息指令
     *                                          0x02获取系统信息指令
     *                                          0x21设置系统工作参数指令
     *                                          0x22获取系统工作参数指令
     *                                          0x26播放指定语音指令
     *                                          0x16设置有效使用期限指令
     *                                          0x17获取有效使用期限指令
     *                                          0x18设置系统实时时钟指令
     *
     *--------------------------------------------------------------------------------
     *
     *
     */
    public static AntboxObject valueOf(ByteBuf buf, DecoderMode mode) {
        int typeOffset = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH + ROLLCODE_FIELD_LENGTH;
        final short pkgType = buf.getUnsignedByte(typeOffset);
        if (TYPE_ACK == pkgType) {
            return new AntboxAck().parse(buf);
        } else if (TYPE_HEARTBEAT == pkgType) {
            return new AntboxHeartbeat().parse(buf);
        } else if (TYPE_MESSAGE == pkgType) {
            typeOffset += 1 + TIME_BYTES_NUM + SESSION_ID_BYTES_NUM + 1;// +1: for EventNo
            final short msgCode = buf.getUnsignedByte(typeOffset);
            return valueOfAntboxMessage(msgCode, buf);
        } else if (TYPE_PROCESSED == pkgType) {
            return new AntboxMessageProcessed().parse(buf);
        } else if (TYPE_SPECIAL == pkgType) {
            typeOffset += 1 + TIME_BYTES_NUM + SESSION_ID_BYTES_NUM;
            final short cmdCode = buf.getUnsignedByte(typeOffset);
            return valueOfSpecialCommand(cmdCode, buf);
        } else if (TYPE_STANDARD == pkgType) {
            typeOffset += 1 + TIME_BYTES_NUM;
            final short cmdCode = buf.getUnsignedByte(typeOffset);
            return valueOfStandardObject(cmdCode, buf, mode);
        } else {
            throw new UnknownPkgTypeException("pkgType: 0x" + Integer.toHexString(pkgType));
        }
    }

    static AntboxObject valueOfAntboxMessage(short msgCode, ByteBuf buf) {
        switch (msgCode) {
            case AntboxMessage.MSG_AC_CHECK:
                return new AcCheckMessage().parse(buf);
            case AntboxMessage.MSG_INVENTORIED:
                return new CardMessage().parse(buf);
            case AntboxMessage.MSG_OPEN_TIMEOUT:
                return new OpenTimeoutMessage().parse(buf);
            case AntboxMessage.MSG_REBOOT:
                return new RebootMessage().parse(buf);
            case AntboxMessage.MSG_SWITCH_OFF:
                return new SwitchOffMessage().parse(buf);
            case AntboxMessage.MSG_SWITCH_ON:
                return new SwitchOnMessage().parse(buf);
            case AntboxMessage.MSG_UNLOCK_TIMEOUT:
                return new UnlockTimeoutMessage().parse(buf);
            default:
                throw new UnknownCmdCodeException(
                        "AntboxMessage.msgCode: 0x" + Integer.toHexString(msgCode));
        }
    }

    static AntboxObject valueOfSpecialCommand(short cmdCode, ByteBuf buf) {
        switch (cmdCode) {
            case AntboxSpecialCommand.OPENDOOR:
                return new OpendoorCommand().parse(buf);
            case AntboxSpecialCommand.INVENTORY:
                return new InventoryCommand().parse(buf);
            default:
                throw new UnknownCmdCodeException(
                        "AntboxSpecialCommand.cmdCode: 0x" + Integer.toHexString(cmdCode));
        }
    }

    static AntboxObject valueOfStandardObject(short cmdCode, ByteBuf buf, DecoderMode mode) {
        if (DecoderMode.SERVER.equals(mode)) {
            switch (cmdCode) {
                case AntboxStandardCommand.GET_SERVER_CONFIG:
                    return new GetServerConfigCommand().parse(buf);
                case AntboxStandardCommand.GET_BOX_INFO:
                    return new GetBoxInfoResponse().parse(buf);
                case AntboxStandardCommand.SET_PARAM:
                    return new SetParamResponse().parse(buf);
                case AntboxStandardCommand.GET_PARAM:
                    return new GetParamResponse().parse(buf);
                case AntboxStandardCommand.PLAY_AUDIO:
                    return new PlayAudioResponse().parse(buf);
                case AntboxStandardCommand.SET_EXPIRED_TIME:
                    return new SetExpiredTimeResponse().parse(buf);
                case AntboxStandardCommand.GET_EXPIRED_TIME:
                    return new GetExpiredTimeResponse().parse(buf);
                case AntboxStandardCommand.SET_CLOCK:
                    return new SetClockResponse().parse(buf);
                default:
                    throw new UnknownCmdCodeException("AntboxStandardObject(SERVER mode).cmdCode: 0x"
                            + Integer.toHexString(cmdCode));
            }
        } else {
            switch (cmdCode) {
                case AntboxStandardCommand.GET_SERVER_CONFIG:
                    return new GetServerConfigResponse().parse(buf);
                case AntboxStandardCommand.GET_BOX_INFO:
                    return new GetBoxInfoCommand().parse(buf);
                case AntboxStandardCommand.SET_PARAM:
                    return new SetParamCommand().parse(buf);
                case AntboxStandardCommand.GET_PARAM:
                    return new GetParamCommand().parse(buf);
                case AntboxStandardCommand.PLAY_AUDIO:
                    return new PlayAudioCommand().parse(buf);
                case AntboxStandardCommand.SET_EXPIRED_TIME:
                    return new SetExpiredTimeCommand().parse(buf);
                case AntboxStandardCommand.GET_EXPIRED_TIME:
                    return new GetExpiredTimeCommand().parse(buf);
                case AntboxStandardCommand.SET_CLOCK:
                    return new SetClockCommand().parse(buf);
                default:
                    throw new UnknownCmdCodeException("AntboxStandardObject(CLIENT mode).cmdCode: 0x"
                            + Integer.toHexString(cmdCode));
            }
        }

    }

    @Override
    public AntboxAck ackObject(short status) {
        return new AntboxAck(getRollCode(), status);
    }

}
