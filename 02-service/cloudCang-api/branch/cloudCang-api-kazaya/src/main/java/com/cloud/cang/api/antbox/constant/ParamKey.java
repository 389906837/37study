package com.cloud.cang.api.antbox.constant;


import com.cloud.cang.api.antbox.protocol.ParamValueReader;

/**
 * 售货机参数列表
 *
 * <pre>
 * 序号	名称	长度	读写允许	描述
 * 0	出厂序列号	5字节	只读	标识该设备的唯一编码，出厂时设定。
 * 1	系统状态	1字节	只读	用于上位机查询当前系统处于何种工作状态，0x01表示空闲、0x02表示开门、0x03表示盘点。
 * 2	实时时钟	6字节	只读	从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
 * 3	超时上锁时间	1字节	读和写（掉电保存）	控制器接收到开门指令后允许开门的时间。如果在定义的时间内门锁没有被打开，则锁芯弹起禁止开门。单位为秒，取值范围1~255。默认10秒。
 * 4	喇叭音量	1字节	读和写（掉电保存）	用于调节控制器的喇叭音量，取值范围0~99。0表示最小音量，99表示最大音量。
 * 5	天线配置	1字节	读和写（掉电保存）	用于设置盘点状态时控制器在哪些天线上盘点。详见附录2。
 * 7	语音配置	14字节	读和写（掉电保存）	用于指定控制器在各种场景下播放的语音。详见附录3。
 * 8	功率配置	1字节	读和写（掉电保存）	用于设置读写器射频输出功率，取值范围0~7。0表示最小功率，7表示最大功率。
 * </pre>
 */
public enum ParamKey {
    /**
     * 0 出厂序列号 5字节 只读 标识该设备的唯一编码，出厂时设定。
     */
    BOX_SN((byte) 0x00, ParamValueReader.LONG_READER),

    /**
     * 1 系统状态 1字节 只读 用于上位机查询当前系统处于何种工作状态，0x01表示空闲、0x02表示开门、0x03表示盘点。
     */
    STATUS((byte) 0x01, ParamValueReader.SINGLE_BYTE_READER),

    /**
     * 2 实时时钟 6字节 只读 从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
     */
    CLOCK((byte) 0x02, ParamValueReader.DATE_READER),

    /**
     * 3 超时上锁时间 1字节 读和写（掉电保存） 控制器接收到开门指令后允许开门的时间。如果在定义的时间内门锁没有被打开，则锁芯弹起禁止开门。单位为秒，取值范围1~255。默认10秒。
     */
    TIMEOUT_SECONDS((byte) 0x03, ParamValueReader.SINGLE_BYTE_READER),

    /**
     * 4 喇叭音量 1字节 读和写（掉电保存） 用于调节控制器的喇叭音量，取值范围0~99。0表示最小音量，99表示最大音量。
     */
    VOLUME((byte) 0x04, ParamValueReader.SINGLE_BYTE_READER),

    /**
     * 5 天线配置 1字节 读和写（掉电保存） 用于设置盘点状态时控制器在哪些天线上盘点。详见附录2。
     */
    ANT((byte) 0x05, ParamValueReader.SINGLE_BYTE_READER),

    /**
     * 7 语音配置 14字节 读和写（掉电保存） 用于指定控制器在各种场景下播放的语音。详见附录3。
     */
    AUDIO((byte) 0x07, ParamValueReader.LONG_READER),

    /**
     * 8 功率配置 1字节 读和写（掉电保存） 用于设置读写器射频输出功率，取值范围0~7。0表示最小功率，7表示最大功率。
     */
    POWER((byte) 0x08, ParamValueReader.SINGLE_BYTE_READER),

    /**
     * 读写器噪音 1字节 读和写
     */
    NOISE((byte) 0x23, ParamValueReader.SINGLE_BYTE_READER);

    /**
     * 代号
     */
    private byte code;

    private ParamValueReader valueReader;

    ParamKey(byte code, ParamValueReader valueReader) {
        this.code = code;
        this.valueReader = valueReader;
    }

    public byte getCode() {
        return this.code;
    }

    public ParamValueReader getValueReader() {
        return valueReader;
    }

    public static ParamKey getByCode(byte code) {
        for (ParamKey key : ParamKey.values()) {
            if (Byte.parseByte(Integer.toHexString(key.code)) == code)
                return key;
        }

        return null;
    }
}
