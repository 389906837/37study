package com.cloud.cang.api.antbox.message;


import com.cloud.cang.api.antbox.protocol.ToServerDataPkg;

public class PlayAudioResponse extends AntboxStandardResponse implements ToServerDataPkg {

    public PlayAudioResponse() {
        super();
        setCmdCode(PLAY_AUDIO);
    }

    public PlayAudioResponse(int rollCode, short status) {
        super(/*rollCode,*/  PLAY_AUDIO, status, null);
    }

}
