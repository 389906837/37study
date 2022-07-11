package com.antbox.rfidmachine.dto;

import com.antbox.rfidmachine.helper.AntboxObject;
import io.netty.buffer.ByteBuf;

/**
 * 读写器命令消息
 */
public class AntboxRequest extends AntboxObject {

	public AntboxRequest(short command, short state, ByteBuf data) {
		super(command, data);
		this.status = state;
	}

	@Override
	protected boolean hasCommand() {
		return true;
	}

}
