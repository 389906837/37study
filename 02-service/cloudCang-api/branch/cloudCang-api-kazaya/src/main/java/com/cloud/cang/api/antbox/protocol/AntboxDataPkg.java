package com.cloud.cang.api.antbox.protocol;

/**
 * Antbox数据包对象
 *
 * @author yong.ma
 * @since 1.3
 */
public interface AntboxDataPkg extends AntboxObjectParser, ByteBufable, CrcOperation {
    public static final int ROLLCODE_FIELD_LENGTH = 2;

    public static final int MAX_ROLLCODE = 65535;

    public static final int TIME_BYTES_NUM = 6;

    public static final int SN_BYTES_NUM = 5;

    public static final int CRC_BYTES_NUM = 2;

    public static final int SESSION_ID_BYTES_NUM = 16;

    public static final int DYNAMIC_SECRET_KEY_BYTES_NUM = 12;

    /**
     * 最大数据帧长度。<br>
     * 注：依据协议MAX_FRAME_LENGTH至少为127（盘点消息）。
     */
    public static final int MAX_FRAME_LENGTH = 128;

    /**
     * 长度为1个字节的起始符。固定取值0x7E。
     */
    public static final short HEADER_STX = 0x7E;

    /**
     * 消息体长度字段偏移量
     */
    public static final int LENGTH_FIELD_OFFSET = 1;

    /**
     * 消息体长度字段所占字节数
     */
    public static final int LENGTH_FIELD_LENGTH = 2;

    /**
     * 消息体长度矫正 (the length of LEN, negative)
     */
    public static final int LENGTH_ADJUSTMENT = -LENGTH_FIELD_LENGTH;

    // public static final byte DEFAULT_ADDR = 0x00;

    public static final int NO_CRC = -1;

    // 数据包类型(6种)

    public static final byte TYPE_UKNOWN = 0x00;

    /**
     * 特殊指令
     */
    public static final byte TYPE_SPECIAL = 0x01;

    /**
     * ACK
     */
    public static final byte TYPE_ACK = 0x02;

    /**
     * 消息
     */
    public static final byte TYPE_MESSAGE = 0x03;

    /**
     * 消息已处理数据包
     */
    public static final byte TYPE_PROCESSED = 0x04;

    /**
     * 心跳
     */
    public static final byte TYPE_HEARTBEAT = 0x05;

    /**
     * 标准指令
     */
    public static final byte TYPE_STANDARD = 0x06;

    public int getRollCode();

    public void setRollCode(int rollCode);

    AntboxAck ackObject(short status);
}
