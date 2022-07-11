package com.antbox.rfidmachine.helper;

import com.antbox.rfidmachine.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * 读写器请求/响应数据对象(顶层抽象类).
 * 
 * <pre>
 * 
 * A.	命令数据块
 * Len	Com_adr	Cmd	State	Data[]	LSB-CRC16	MSB-CRC16
 * 
 * Len：长度为1个字节的命令数据块长度（不包括自身的一个字节），取值范围5～25。Len的长度等于（5+Data[]）的长度。注意，Len的值必须和后面所跟的实际数据个数相符。
 * Com_adr：长度为1个字节的读写器地址。取值为0～254时，只有与此地址相符的读写器会对该命令数据块有响应。取值为255是广播地址，所有读写器都会对命令数据块有响应。
 * Cmd：长度为1个字节的操作命令符，一共定义了34条命令。
 * State：长度为1个字节的操作控制符，低4位控制操作模式（取值含义详见每条命令）；高4位控制操作类型，取值为“0”表示ISO/IEC15693协议命令，为“F”表示读写器自定义命令，为“E”表示透明命令，别的值保留。
 * Data[]：命令操作数，给出运行命令所必须的数据。若Len=5则无此项。
 * CRC16：长度为2个字节的CRC-16效验和。低字节在前。
 * 
 * B.	响应数据块
 * Len	Com_adr	Status	Data[]	LSB-CRC16	MSB-CRC16
 * 
 * Len：长度为1个字节的响应数据块长度，取值范围4～28，为4表示无操作数。Len的长度等于（4+Data[]）的长度。
 * Com_adr：长度为1个字节的读写器地址，取值为0～254。
 * Status：长度为1个字节的命令执行结果状态值，它的含义详见后面的表说明。
 * Data[]：响应数据，运行命令后得到的电子标签信息。若Len=4则无此项。
 * CRC16：长度为2个字节的CRC-16效验和。低字节在前。
 * 
 * 注意，当命令数据块不符合要求的时候，读写器将不会有任何响应。
 * 读写器地址Com_adr的缺省配置是0x00。用户可以通过读写器自定义命令中的“Write Com_adr”来改变。
 * 循环冗余码校验（CRC）的计算包括了从Len开始的全部数据，得到的CRC在传送时低字节在前。所用的CRC生成多项式同ISO/IEC 15693协议中定义的一样，但是需要注意，这里的计算结果不取反。例子：我们给定一个数据块0x05,0xFF,0x01,0x00,LSB-CRC,MSB-CRC，通过CRC计算得到的数据是LSB-CRC＝0x5D，MSB-CRC＝0xB2。这样，当收到0x05,0xFF,0x01,0x00,0x5D,0xB2这样的数据块时，对它们（全部的6个字节）进行CRC计算，如果所得到的值是0x00和0x00就通过了校验。下面给出一个C语言的CRC计算程序供参考：
 * 
 * Polynomial:    POLYNOMIAL=0x8408;
 * Start Value:   PRESET_VALUE=0xffff;
 * C-Example:
 *    int	        	i,j;
 *    unsigned	int	current_crc_value=PRESET_VALUE;
 * 
 *    for(i=0;i<len;i++)  /*len=number of protocol bytes without CRC* /
 *    {
 *       current_crc_value=current_crc_value^((unsigned  int)pData[i]);
 *       for(j=0;j<8;j++)
 *       {
 * 	  if(current_crc_value&0x0001)
 * 	  {
 * 	     current_crc_value=(current_crc_value>>1)^POLYNOMIAL;
 * 	  }
 * 	  else
 * 	  {
 * 	     current_crc_value=(current_crc_value>>1);
 * 	  }
 *       }
 *    }
 *       pData[i++]=(unsigned char)(current_crc_value&0x00ff);
 *       pData[i]=(unsigned char)((current_crc_value>>8)&0x00ff);
 * 
 * </pre>
 * 
 */
public abstract class AntboxObject {

	/**
	 * 标签（RFID）的字节数
	 */
	public int UID_BYTES_NUM = 9;

	/**
	 * UID数字进制
	 */
	public static final int UID_RADIX = 16;

	/**
	 * 消息体长度字段偏移量
	 */
	public static final int LENGTH_FIELD_OFFSET = 0;

	/**
	 * 消息体长度字段所占字节数
	 */
	public static final int LENGTH_FIELD_LENGTH = 1;

	public static final byte DEFAULT_ADDR = 0x00;

	private static final int NO_CRC = -1;

	private static final int CRC_LENGTH = 2;

	/**
	 * BODY长度（包括LEN字段）
	 */
	private int bodyLen;

	/**
	 * 读写器地址，固定取值0。
	 */
	private short addr = DEFAULT_ADDR;

	/**
	 * 操作命令符（1个字节）
	 */
	private short command;

	/**
	 * 操作控制符/命令执行结果状态值
	 */
	protected short status;

	/**
	 * 命令数据/响应数据
	 */
	private ByteBuf data;

	/**
	 * 循环冗余码校验（CRC）<br>
	 * 长度为2个字节的CRC-16效验和,低字节在前.<br>
	 * 计算包括了从MSB-LEN16开始到Data[]的全部数据，得到的CRC在传送时低字节在前。
	 */
	private int crc = NO_CRC;

	public AntboxObject() {

	}

	public AntboxObject(short command, ByteBuf data) {
		this.command = command;
		this.data = data;
	}

	/**
	 * 是否有命令字段
	 */
	protected abstract boolean hasCommand();

	public short getStatus() {
		return status;
	}

	private int bodyLen() {
		if (bodyLen == 0) {
			bodyLen = bufLen() - LENGTH_FIELD_LENGTH;
		}
		return bodyLen;
	}

	private int bufLen = 0;

	/**
	 * 缓冲区长度
	 */
	private int bufLen() {
		if (bufLen > 0) {
			return bufLen;
		}

		/* addr + status + crc */
		int n = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH + 1 + 1 + CRC_LENGTH;
		if (hasCommand()) {// command
			n++;
		}
		if (getData() != null) {// data
			n += getData().readableBytes();
		}
		bufLen = n;
		return bufLen;
	}

	public short getAddr() {
		return addr;
	}

	public short getCommand() {
		return command;
	}

	private ByteBuf getData() {
		if (data == null) {
			data = createData();
		}
		return data;
	}

	protected ByteBuf createData() {
		return null;
	}

	public void setData(ByteBuf data) {
		this.data = data;
	}

	public int getCrc() {
		return crc;
	}

	public ByteBuf toByteBuf() {
		ByteBuf buf = toByteBufWithoutCrc();
		this.crc = AntboxUtil.calcCrc(buf, LENGTH_FIELD_OFFSET); // CRC
		buf.writeShort(this.crc);
		return buf;
	}

	/**x
	 * Convert to ByteBuf without CRC
	 */
	private ByteBuf toByteBufWithoutCrc() {
		ByteBuf buf = Unpooled.buffer(bufLen() + 1);
		writeToWithoutCrc(buf);
		return buf;
	}

	/**
	 * Write to ByteBuf without CRC
	 */
	private void writeToWithoutCrc(ByteBuf out) {
		out.writeByte(bodyLen());// contentLen
		out.writeByte(this.addr);// addr
		if (hasCommand()) {
			out.writeByte(this.command);// cmd
		}
		out.writeByte(this.status);// status

		if (getData() != null) {// data
			out.writeBytes(getData().duplicate());
		}
	}


	public AntboxObject parse(ByteBuf buf) {
		this.bodyLen = buf.readUnsignedByte();
		this.addr = buf.readUnsignedByte();
		if (this.hasCommand()) {
			this.command = buf.readUnsignedByte();
		}
		this.status = buf.readUnsignedByte();
		int dataLen = buf.readableBytes() - CRC_LENGTH;
		if (dataLen > 0) {
			ByteBuf d = Unpooled.buffer(dataLen);
			buf.readBytes(d, dataLen);
			setData(d);
		} else {
			setData(Unpooled.EMPTY_BUFFER);
		}
		this.crc = buf.readUnsignedShort();
		return this;
	}

	public void copyFrom(AntboxObject src) {
		this.bodyLen = src.bodyLen();
		this.addr = src.getAddr();
		this.command = src.getCommand();
		this.status = src.getStatus();
		this.copyDataFrom(src);
		this.crc = src.getCrc();
	}

	private void copyDataFrom(AntboxObject src) {
		if (src.getData() != null) {
			this.setData(src.getData().duplicate());
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends AntboxObject> T castTo(Class<T> targetType) {
		if (targetType.equals(getClass())) {
			return (T) this;
		}
		try {
			AntboxObject obj = targetType.newInstance();
			obj.copyFrom(this);
			return (T) obj;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getClass().getSimpleName()).append("[");
		b.append("bodyLen=").append(bodyLen()).append(", ");
		b.append("addr=").append(getAddr()).append(", ");
		b.append("command=0x").append(Integer.toHexString(getCommand())).append(", ");
		b.append("data=").append(stringOfData()).append(", ");
		b.append("crc=0x").append(Integer.toHexString(getCrc())).append(", ");
		b.append("status=0x").append(Integer.toHexString(getStatus())).append("]");
		return b.toString();
	}

	protected String stringOfData() {
		if (data == null) {
			return "";
		}

		return "0x" + ByteBufUtil.hexDump(data);
	}

}
