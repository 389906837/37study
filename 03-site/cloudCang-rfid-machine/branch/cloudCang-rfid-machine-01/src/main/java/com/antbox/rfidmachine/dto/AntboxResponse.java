package com.antbox.rfidmachine.dto;

import com.antbox.rfidmachine.helper.AntboxObject;
import com.antbox.rfidmachine.helper.AntboxrHelper;
import com.antbox.rfidmachine.util.AntboxUtil;
import io.netty.buffer.ByteBuf;

import java.math.BigInteger;


/**
 * 读写器响应消息
 */
public class AntboxResponse extends AntboxObject {

	private String selectMachine;

	public AntboxResponse(String selectMachine) {
		this.selectMachine = selectMachine;
	}

	public AntboxResponse(short command, short state, ByteBuf data) {
		super(command, data);
		this.status = state;
	}

	@Override
	protected boolean hasCommand() {
		return false;
	}

	@Override
	public void setData(ByteBuf data) {

		//旧一体机查询，读取8个字节数
		if (AntboxrHelper.OLD_MACHINE_MODEL.equals(selectMachine)) {
			UID_BYTES_NUM = 8;
		}
		if (data == null || data.readableBytes() <= 0) {
			return;
		}
		this.dsfid = data.readUnsignedByte();
		byte[] uid0 = new byte[UID_BYTES_NUM];
		data.readBytes(uid0);
		String x = new BigInteger(1, uid0).toString(UID_RADIX).toUpperCase();
		this.uid = AntboxUtil.leftPad(x, 2 * UID_BYTES_NUM, '0');
	}

	private short dsfid;
	/**
	 * 十六进制字符串（大写，代表一个无符号整数，8bytes）
	 */
	private String uid;

	public short getDsfid() {
		return dsfid;
	}

	public void setDsfid(short dsfid) {
		this.dsfid = dsfid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
