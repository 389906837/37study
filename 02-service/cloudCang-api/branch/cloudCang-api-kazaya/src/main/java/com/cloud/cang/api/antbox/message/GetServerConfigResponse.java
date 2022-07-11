package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

public class GetServerConfigResponse extends AntboxStandardResponse implements ToClientDataPkg {
    /**
     * ReactTime：1个字节，控制器或服务器发送新的非ACK数据包后，等待ACK数据包或者响应数据包的最大时间。取值范围为1~255，单位为1s。
     * 默认3s。
     */
//    private short reactTime = AntboxConfig.REACT_TIME_SECONDS;
    private short reactTime = 3;

    /**
     * RetryTime：1个字节，控制器或服务器重发非ACK数据包后，等待ACK数据包的最大时间。取值范围为1~255，单位为100ms。
     * 默认500ms。
     */
//    private short retryTime = (short) (AntboxConfig.RETRY_TIME_MILLIS / 100);
    private short retryTime = (short) (500);

    /**
     * RetryCnt：1个字节，数据包重发次数。取值范围为0~255，设置为0表示不重发。默认10次。
     */
//    private short retryCnt = AntboxConfig.RETRY_COUNT;
    private short retryCnt = 10;

    /**
     * OverTime：1个字节，开门超时时间。当控制器接收到开门指令且门锁打开后，<br>
     * 在定义的开门超时时间内门锁没有被关闭，则产生一条开门超时消息。<br>
     * 取值范围为1~255，单位为10s，默认90*10s（15分钟）。
     */
    //private short openTimeout = (short) (AntboxConfig.OPEN_TIMEOUT_SECONDS / 10);
    private short openTimeout = (short) (15);

    /**
     * HBTime：1个字节，心跳包间隔时间。取值范围为1~255，单位为1s，默认10s。
     */
   // private short heartbeatTime = AntboxConfig.HEARTBEAT_SECONDS;
    private short heartbeatTime =10;

    /**
     * Reserver：20个字节，保留字节，取值0。
     */
    private ByteBuf reserve = DEFAULT_RESERVE;

    static final ByteBuf DEFAULT_RESERVE = Unpooled.wrappedBuffer(new byte[20]);

    public GetServerConfigResponse() {
        super();
        setCmdCode(GET_SERVER_CONFIG);
    }

    public GetServerConfigResponse(/* int rollCode, */ short status) {
        super(/* rollCode, */ GET_SERVER_CONFIG, status, null);
    }

    @Override
    protected ByteBuf createData() {
        ByteBuf cmdData = Unpooled.buffer(5 * 1 + DEFAULT_RESERVE.readableBytes());
        cmdData.writeByte(this.reactTime);
        cmdData.writeByte(this.retryTime);
        cmdData.writeByte(this.retryCnt);
        cmdData.writeByte(this.openTimeout);
        cmdData.writeByte(this.heartbeatTime);
        cmdData.writeBytes(this.reserve.duplicate());
        setCmdData(cmdData);

        return super.createData();
    }

    public short getReactTime() {
        return reactTime;
    }

    public short getRetryTime() {
        return retryTime;
    }

    public short getRetryCnt() {
        return retryCnt;
    }

    public short getOpenTimeout() {
        return openTimeout;
    }

    public short getHeartbeatTime() {
        return heartbeatTime;
    }

    public ByteBuf getReserve() {
        return reserve;
    }

    public void setReactTime(short reactTime) {
        this.reactTime = reactTime;
    }

    public void setRetryTime(short retryTime) {
        this.retryTime = retryTime;
    }

    public void setRetryCnt(short retryCnt) {
        this.retryCnt = retryCnt;
    }

    public void setOpenTimeout(short openTimeout) {
        this.openTimeout = openTimeout;
    }

    public void setHeartbeatTime(short heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public void setReserve(ByteBuf reserve) {
        this.reserve = reserve;
    }

    @Override
    protected String stringOfCmdData() {
        StringBuilder b = new StringBuilder();
        b.append("[reactTime=").append(this.reactTime).append(", ");
        b.append("retryTime=").append(this.retryTime).append(", ");
        b.append("retryCnt=").append(this.retryCnt).append(", ");
        b.append("openTimeout=").append(this.openTimeout).append(", ");
        b.append("heartbeatTime=").append(this.heartbeatTime).append(", ");
        b.append("reserve=0x").append(ByteBufUtil.hexDump(this.reserve)).append("]");

        return b.toString();
    }

}
