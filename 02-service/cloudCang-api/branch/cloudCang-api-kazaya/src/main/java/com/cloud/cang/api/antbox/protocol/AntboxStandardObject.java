package com.cloud.cang.api.antbox.protocol;


import com.cloud.cang.api.antbox.constant.BoxGlobals;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.util.Date;

public abstract class AntboxStandardObject extends AntboxObject {
    /**
     * 获取服务器配置信息指令<br>
     * 控制器启动后，发送该命令上位机，获取配置信息来初始化控制器的某些工作参数。如果获取失败，这些参数到将采用缺省值来初始化。
     */
    public static final short GET_SERVER_CONFIG = 0x01;

    /**
     * 获取系统信息指令<br>
     * 该命令用于获取控制器系统的固件和状态信息
     */
    public static final short GET_BOX_INFO = 0x02;

    /**
     * 设置系统工作参数指令<br>
     * 该命令用于设置控制器单个系统工作参数。
     */
    public static final short SET_PARAM = 0x21;

    /**
     * 获取系统工作参数指令<br>
     * 该命令用于同时读取任意多个控制器工作参数。
     */
    public static final short GET_PARAM = 0x22;

    /**
     * 播放指定语音指令<br>
     * 上位机发送该命令请求控制器播放指定编号的语音。
     */
    public static final short PLAY_AUDIO = 0x26;

    /**
     * 设置有效使用期限指令<br>
     * 上位机发送该命令设置设备的有效使用期限。<br>
     * 当控制器中的实时时间超过该期限时间时，将禁止开门，同时忽略接收到的开门指令。<br>
     * 此外，“获取系统信息执行” 的Register字节用来指示设备是否有权使用
     */
    public static final short SET_EXPIRED_TIME = 0x16;

    /**
     * 获取有效使用期限指令<br>
     * 上位机发送该命令获取设备的有效使用期限
     */
    public static final short GET_EXPIRED_TIME = 0x17;

    /**
     * 设置系统实时时钟指令<br>
     * 上位机发送该命令设置控制器的当前时间。
     */
    public static final short SET_CLOCK = 0x18;

    // ---------------------------------------------

    public static final int BOX_SN_BYTES_NUM = 5;

    // Time：6个字节，时间戳。从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。采用24小时制。
    private Date time = new Date();

    private short cmdCode;

    /**
     * Status： 1个字节，指令执行结果状态值
     */
    private short status = BoxGlobals.STAUS_OK;

    /**
     * 命令/响应数据
     */
    private ByteBuf cmdData;

    public AntboxStandardObject() {
        super();
        setPkgType(TYPE_STANDARD);
    }

    protected AntboxStandardObject(/* int rollCode, */short cmdCode, ByteBuf cmdData) {
        super(/* rollCode, */ TYPE_STANDARD, null);
        this.cmdCode = cmdCode;
        this.setCmdData(cmdData);
    }

    @Override
    protected ByteBuf createData() {
        ByteBuf data = Unpooled.buffer(6 + 1 + AntboxUtil.length(cmdData));
        AntboxUtil.writeDate(time, data);
        data.writeByte(cmdCode);
        if (hasStatus()) {// 如果有status字段
            data.writeByte(status);
        }
        if (AntboxUtil.hasLength(cmdData)) {
            data.writeBytes(cmdData);
        }
        return data;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    protected void setCmdCode(short cmdCode) {
        this.cmdCode = cmdCode;
    }

    @Override
    protected void setData(ByteBuf data) {
        if (!AntboxUtil.hasLength(data)) {
            return;
        }
        this.time = AntboxUtil.readDate(data);
        this.cmdCode = data.readUnsignedByte();
        if (hasStatus()) {// 如果有status字段
            this.status = data.readUnsignedByte();
        }
        if (data.isReadable()) {
            this.setCmdData(data);
        }
    }

    /**
     * 钩子方法。用于解析cmdData
     */
    protected void setCmdData(ByteBuf cmdData) {
        this.cmdData = cmdData;
    }

    public Date getTime() {
        return time;
    }

    public short getCmdCode() {
        return cmdCode;
    }

    public ByteBuf getCmdData() {
        return cmdData;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    protected abstract boolean hasStatus();

    @Override
    protected String stringOfData() {
        return "[time=" + time + ", cmdCode=" + cmdCode + ", status="
                + (hasStatus() ? "0x" + Integer.toHexString(status) : "") + ", cmdData=" + stringOfCmdData() + "]";
    }

    protected String stringOfCmdData() {
        if (cmdData == null) {
            return "";
        }

        return "0x" + ByteBufUtil.hexDump(cmdData);
    }

}
