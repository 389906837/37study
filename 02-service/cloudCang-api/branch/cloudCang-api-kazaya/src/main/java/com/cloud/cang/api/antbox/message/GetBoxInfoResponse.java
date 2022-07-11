package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.constant.BoxGlobals;
import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.util.BitSet;
import java.util.Date;

import static com.cloud.cang.api.antbox.protocol.AntboxStandardObject.BOX_SN_BYTES_NUM;
import static com.cloud.cang.api.antbox.protocol.AntboxStandardObject.GET_BOX_INFO;

public class GetBoxInfoResponse extends AntboxStandardResponse implements ToServerDataPkg {
    // rspData:
    // Version，Time，SN，State，Msgok，
    // PwrStatus，RTCok，Register，Voice，LockStatus，
    // Reserve(1byte)，Wavok，EventNo，MsgCode，Reserve(11bytes)

    /**
     * Version：2个字节，固件的版本号。高字节为主版本号，低字节为子版本号。
     */
    private short majorVersion;

    private short minorVersion;

    /**
     * Time：6个字节，当前系统实时时间。从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
     */
    private Date boxTime;

    /**
     * SN：5个字节，系统出厂序列号。
     */
    private long boxSn;

    /**
     * State：1个字节，当前系统状态。0x01表示空闲、0x02表示开门、0x03表示盘点。
     */
    private byte state;

    /**
     * Msgok：1个字节，当前系统消息池是否为空。0x00表示空，0x01表示有消息存在。
     */
    private boolean hasMsg;

    /**
     * PwrStatus：1个字节，当前220Vac交流输入是否正常。0x00表示正常，0x01表示不正常。
     */
    private boolean powerOk;

    /**
     * RTCok：1个字节，当前系统时钟运行是否正常。0x00表示正常，0x01表示不正常。
     */
    private boolean clockOk;

    /**
     * Register：1个字节，当前设备是否已授权。0x00表示未授权，0x01表示已授权。
     */
    private boolean registered;

    /**
     * Voice：1个字节，当前系统的音量，取值范围0~99。0表示音量最小，99表示音量最大。
     */
    private byte volume;

    /**
     * LockStatus：1个字节，返回当前锁的门磁信号状态和锁舌信号状态。<br>
     * Bit0指示锁舌信号状态，为0表示锁舌缩入（蓝灰两线闭合），为1表示锁舌伸出（蓝灰两线断开）。<br>
     * Bit1指示门磁信号状态，为0表示门磁吸合（绿白两线闭合），为1表示门磁离开（绿白两线断开）。
     */
    private boolean locked;

    private boolean opened;

    /**
     * Reserve(1byte)
     */
    private short reserve1;

    /**
     * Wavok：1个字节，控制器内部保存的语音是否有效。0表示语音无效，需进行语音更新。1表示语音有效，可以正常使用。
     */
    private boolean wavOk;

    /**
     * EventNo：1个字节，最近一次上传成功（ACK确认）的消息数据包的事件序号。
     */
    private short eventNo;

    /**
     * MsgCode：1个字节，最近一次上传成功（ACK确认）的消息数据包的消息类型。
     */
    private short msgCode;

    /**
     * Reserve：11个字节，保留字节，取值0。
     */
    private ByteBuf reserve2;

    public GetBoxInfoResponse() {
        super();
        setCmdCode(GET_BOX_INFO);
    }

    public GetBoxInfoResponse(int rollCode, short status, ByteBuf cmdData) {
        super(/* rollCode, */ GET_BOX_INFO, status, cmdData);
    }

    @Override
    protected void setCmdData(ByteBuf cmdData) {
        this.majorVersion = cmdData.readUnsignedByte();
        this.minorVersion = cmdData.readUnsignedByte();
        this.boxTime = AntboxUtil.readDate(cmdData);
        this.boxSn = AntboxUtil.readLong(cmdData, BOX_SN_BYTES_NUM);
        this.state = cmdData.readByte();
        this.hasMsg = cmdData.readByte() == BoxGlobals.BOOL_TRUE;
        this.powerOk = cmdData.readByte() == BoxGlobals.STAUS_OK;
        this.clockOk = cmdData.readByte() == BoxGlobals.STAUS_OK;
        this.registered = cmdData.readByte() == BoxGlobals.BOOL_TRUE;
        this.volume = cmdData.readByte();
        final BitSet lockStatus = BitSet.valueOf(new byte[]{cmdData.readByte()});
        this.locked = lockStatus.get(0);
        this.opened = lockStatus.get(1);
        this.reserve1 = cmdData.readUnsignedByte();
        this.wavOk = cmdData.readByte() == BoxGlobals.BOOL_TRUE;
        this.eventNo = cmdData.readUnsignedByte();
        this.msgCode = cmdData.readUnsignedByte();
        this.reserve2 = cmdData.readBytes(cmdData.readableBytes());
    }

    @Override
    protected String stringOfCmdData() {
        StringBuilder b = new StringBuilder();
        b.append("[majorVersion=").append(this.majorVersion).append(", ");
        b.append("minorVersion=").append(this.minorVersion).append(", ");
        b.append("boxTime=").append(this.boxTime).append(", ");
        b.append("boxSn=").append(this.boxSn).append(", ");
        b.append("state=").append(this.state).append(", ");
        b.append("hasMsg=").append(this.hasMsg).append(", ");
        b.append("powerOk=").append(this.powerOk).append(", ");
        b.append("clockOk=").append(this.clockOk).append(", ");
        b.append("registered=").append(this.registered).append(", ");
        b.append("volume=").append(this.volume).append(", ");
        b.append("locked=").append(this.locked).append(", ");
        b.append("opened=").append(this.opened).append(", ");
        b.append("reserve1=0x").append(Integer.toHexString(this.reserve1)).append(", ");
        b.append("wavOk=").append(this.wavOk).append(", ");
        b.append("eventNo=").append(this.eventNo).append(", ");
        b.append("msgCode=").append(this.msgCode).append(", ");
        b.append("reserve2=0x").append(ByteBufUtil.hexDump(this.reserve2)).append("]");
        return b.toString();
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public short getMinorVersion() {
        return minorVersion;
    }

    public Date getBoxTime() {
        return boxTime;
    }

    public long getBoxSn() {
        return boxSn;
    }

    public byte getState() {
        return state;
    }

    public boolean hasMsg() {
        return hasMsg;
    }

    public boolean isPowerOk() {
        return powerOk;
    }

    public boolean isClockOk() {
        return clockOk;
    }

    public boolean isRegistered() {
        return registered;
    }

    public byte getVolume() {
        return volume;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isOpened() {
        return opened;
    }

    public short getReserve1() {
        return reserve1;
    }

    public boolean isWavOk() {
        return wavOk;
    }

    public short getEventNo() {
        return eventNo;
    }

    public short getMsgCode() {
        return msgCode;
    }

    public ByteBuf getReserve2() {
        return reserve2;
    }

    public void setMajorVersion(short majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setMinorVersion(short minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setBoxTime(Date boxTime) {
        this.boxTime = boxTime;
    }

    public void setBoxSn(long boxSn) {
        this.boxSn = boxSn;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public void setHasMsg(boolean hasMsg) {
        this.hasMsg = hasMsg;
    }

    public void setPowerOk(boolean powerOk) {
        this.powerOk = powerOk;
    }

    public void setClockOk(boolean clockOk) {
        this.clockOk = clockOk;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setReserve1(short reserve1) {
        this.reserve1 = reserve1;
    }

    public void setWavOk(boolean wavOk) {
        this.wavOk = wavOk;
    }

    public void setEventNo(short eventNo) {
        this.eventNo = eventNo;
    }

    public void setMsgCode(short msgCode) {
        this.msgCode = msgCode;
    }

    public void setReserve2(ByteBuf reserve2) {
        this.reserve2 = reserve2;
    }

}
