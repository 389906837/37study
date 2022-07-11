package com.cloud.cang.api.antbox.command;

import io.netty.buffer.ByteBuf;

public class InventoryCommand extends AntboxSpecialCommand {

    public InventoryCommand() {
        super();
        setCmdCode(INVENTORY);
    }

    public InventoryCommand(/*int rollCode,*/ ByteBuf sessionId) {
        super(/*rollCode,*/ sessionId, INVENTORY);
    }

}
