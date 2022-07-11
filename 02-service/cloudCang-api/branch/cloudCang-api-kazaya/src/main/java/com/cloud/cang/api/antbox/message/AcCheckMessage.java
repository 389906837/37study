package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.constant.CommandStatus;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;

/**
 * 断电检测消息
 * 控制器检测到交流输入220Vac状态有变化时，将产生一条断电检测消息
 *
 * @Author chipun.cheng
 * @Date 2017年4月8日 下午12:52:12
 */
public class AcCheckMessage extends AntboxMessage {

    public AcCheckMessage() {
        super();
        setMsgCode(MSG_AC_CHECK);
    }

    public AcCheckMessage(/* int rollCode, */ ByteBuf sessionId, short eventNo, byte acStatus) {
        super(/* rollCode, */ sessionId, eventNo, MSG_AC_CHECK, null);
        this.setAcStatus(acStatus);
    }

    public void setAcStatus(byte acStatus) {
        this.setMsgData(AntboxUtil.byteWrap(acStatus));
    }

    public short getAcStatus() {
        return AntboxUtil.getUnsignedByteAt(getMsgData(), 0, CommandStatus.UNKNOWN);
    }

}
