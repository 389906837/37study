package com.cloud.cang.api.antbox.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.HashSet;
import java.util.Set;

/**
 * 售货机卡号消息
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class CardMessage extends AntboxMessage {

    public static final int SEQNO_START_VALUE = 0;

    static final int UID_RADIX = 16;

    static final int UID_FIX_LEN = 16;

    static final byte LINK_TRUE = 1;

    /**
     * Reserve(2bytes)
     */
    private int reserve;

    /**
     * Type：1个字节，本轮盘点的触发类型。0表示关门盘点，1表示指令盘点。
     */
    private byte type;

    /**
     * Round：1个字节，盘点轮次序号。序号范围从0～255。<br>
     * 每执行一轮新的盘点，轮次序号会自动加1，当序号超过255，会重新从0开始。<br>
     * 同一轮盘点的盘点消息轮次序号相同。
     */
    private short round;

    /**
     * Code：1个字节，同一轮盘点的盘点消息序号。数据包序号范围从0～255。<br>
     * 每执行一次盘点，数据包序号都会重新从0开始，每成功上传一个数据包，序号会自动加1，<br>
     * 当数据包序号超过255，会重新从0开始。
     */
    private short seqNo;

    /**
     * Link：1个字节，用于指示是否本轮盘点的最后一条消息。<br>
     * 为1表示后续还有盘点消息要传送，为0表示当前数据包为本轮盘点最后一条消息
     */
    private boolean hasNext;

    private Set<String> uidSet;

    public CardMessage() {
        super();
        setMsgCode(MSG_INVENTORIED);
    }

    public CardMessage(/* int rollCode, */ ByteBuf sessionId, short eventNo, ByteBuf msgData) {
        super(/* rollCode, */ sessionId, eventNo, MSG_INVENTORIED, msgData);
    }

    @Override
    protected void setMsgData(ByteBuf data) {
        this.reserve = data.readUnsignedShort();
        this.type = data.readByte();
        this.round = data.readUnsignedByte();
        this.seqNo = data.readUnsignedByte();
        this.hasNext = (LINK_TRUE == data.readByte());
        final byte num = data.readByte();
        this.uidSet = new HashSet<>();
        for (int i = 0; i < num; i++) {
            ByteBuf uid = Unpooled.buffer(UID_BYTES_NUM);
            data.readBytes(uid, UID_BYTES_NUM);
            this.uidSet.add(ByteBufUtil.hexDump(uid).toUpperCase());
        }
    }

    @Override
    protected String stringOfMsgData() {
        StringBuilder sb = new StringBuilder();
        sb.append("reserve=0x").append(Integer.toHexString(this.reserve)).append(", ");
        sb.append("type=").append(this.type).append(", ");
        sb.append("round=").append(this.round).append(", ");
        sb.append("seqNo=").append(this.seqNo).append(", ");
        sb.append("hasNext=").append(this.hasNext).append(", ");
        sb.append("num=").append(getUidNum()).append(", ");
        sb.append("uidSet=").append(this.uidSet == null ? "[]" : this.uidSet);
        return sb.toString();
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(short seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * UID数量
     */
    public short getUidNum() {
        return (short) (this.uidSet == null ? 0 : this.uidSet.size());
    }

    public Set<String> getUidSet() {
        return uidSet;
    }

    public void setUidSet(Set<String> uidSet) {
        this.uidSet = uidSet;
    }

    public byte getType() {
        return type;
    }

    public short getRound() {
        return round;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setRound(short round) {
        this.round = round;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

//    @Override
//    public String toString() {
//        return "[reserve=" + reserve + ", type=" + type + ", round=" + round + ", seqNo=" + seqNo
//                + ", hasNext=" + hasNext + ", uidSet=" + uidSet + "]";
//    }
}
