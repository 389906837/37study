package com.cloud.cang.api.antbox.constant;


/**
 * 命令执行结果状态值(Status)
 */
public interface CommandStatus {
    /**
     * 待定/未知
     */
    public static final short UNKNOWN = 0xFF;

    /**
     * 0x00 操作成功 当成功执行命令后返回给上位机的状态值。数据块包含了所要信息。
     */
    public static final byte SUCCESS = 0x00;

    /**
     * 0xF1 命令不支持 命令数据块的操作命令不被支持时返回给上位机的状态值。
     */
    public static final short UNSUPPORTED_COMMAND = 0xF1;

    /**
     * 0xF2 状态出错 当前状态下不允许执行指定命令时返回此状态
     */
    public static final short STATUS_ERROR = 0xF2;

    /**
     * 0xF3 长度错误 命令长度不符合此命令要求时返回给上位机的状态值。
     */
    public static final short LENGTH_ERROR = 0xF3;

    /**
     * 0xF4 参数范围不符 命令数据域中的操作数不在允许的范围之内时返回给上位机的状态值。
     */
    public static final short PARAM_ERROR = 0xF4;

    /**
     * 0xF5 ROM操作出错 向ROM中写入数据，但是操作失败时返回给上位机的状态值。
     */
    public static final short ROM_ERROR = 0xF5;

    /**
     * 0xF6 系统时钟异常 控制器实时时钟停止计时或者计时变慢
     */
    public static final short CLOCK_ERROR = 0xF6;

    /**
     * 0xF7 序列号不正确 设置有效使用期限或实时时钟时密文解析得到的设备序列号不符
     */
    public static final short SN_ERROR = 0xF7;

    /**
     * 0xF8 系统未授权 控制器当前时间超过有效使用期限时间
     */
    public static final short UNAUTH_ERROR = 0xF8;

    /**
     * 0xF9 消息已处理出错 当前没有消息数据包需要已处理确认
     */
    public static final short NONEED_ACK = 0xF9;
}
