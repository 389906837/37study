package com.cloud.cang.api.antbox.protocol;


import com.cloud.cang.api.antbox.constant.CommandStatus;
import com.cloud.cang.api.antbox.util.AntboxUtil;

public class AntboxAck extends AntboxObject implements TowWayDataPkg {

    public AntboxAck() {
        super();
        setPkgType(TYPE_ACK);
    }

    public AntboxAck(int rollCode, short status) {
        super(TYPE_ACK, null);
        setRollCode(rollCode);
        setStatus(status);
    }

    public short getStatus() {
        return AntboxUtil.getUnsignedByteAt(getData(), 0, CommandStatus.UNKNOWN);

    }

    public void setStatus(short status) {
        setData(AntboxUtil.byteWrap(status));
    }

    public boolean isOk() {
        short status = getStatus();
        return CommandStatus.SUCCESS == status || CommandStatus.NONEED_ACK == status;
    }

}
