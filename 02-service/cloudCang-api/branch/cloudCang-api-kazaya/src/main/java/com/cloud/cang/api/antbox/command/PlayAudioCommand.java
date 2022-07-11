package com.cloud.cang.api.antbox.command;

import com.cloud.cang.api.antbox.constant.CommandStatus;
import com.cloud.cang.api.antbox.protocol.ToClientDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;

public class PlayAudioCommand extends AntboxStandardCommand implements ToClientDataPkg {

    public PlayAudioCommand() {
        super();
        setCmdCode(PLAY_AUDIO);
    }

    public PlayAudioCommand(/*int rollCode,*/  short audioNo) {
        super(/*rollCode,*/  PLAY_AUDIO, null);
        setAudioNo(audioNo);
    }

    public void setAudioNo(short audioNo) {
        setCmdData(AntboxUtil.byteWrap(audioNo));
    }

    public short getAudioNo() {
        return AntboxUtil.getUnsignedByteAt(getCmdData(), 0, CommandStatus.UNKNOWN);
    }
}
