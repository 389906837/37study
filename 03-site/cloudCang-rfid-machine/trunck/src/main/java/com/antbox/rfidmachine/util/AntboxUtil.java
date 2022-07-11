package com.antbox.rfidmachine.util;

import io.netty.buffer.ByteBuf;

import java.util.Calendar;
import java.util.Date;

/**
 * Antbox Utilities.例如：计算AntboxObject的CRC值。
 * 
 * @author yong.ma
 * @since 0.0.1
 */
public abstract class AntboxUtil {

	/**
	 * CRC: Polynomial: POLYNOMIAL=0x8408;
	 */
	static final int CRC_POLYNOMIAL = 0x8408;

	/**
	 * CRC: Start Value: PRESET_VALUE=0xffff;
	 */
	static final int CRC_START_VALUE = 0xffff;

	/**
	 * 循环冗余码校验（CRC）<br>
	 * 长度为2个字节的CRC-16效验和,低字节在前.<br>
	 * 计算包括了从MSB-LEN16开始到Data[]的全部数据，得到的CRC在传送时低字节在前。
	 */
	public static int calcCrc(ByteBuf buf, int offset) {
		int i, j;
		int crc = CRC_START_VALUE;
		final int LEN = buf.readableBytes();
		for (i = offset; i < LEN; i++) {
			crc = crc ^ (buf.getUnsignedByte(i));
			for (j = 0; j < 8; j++) {
				if (notZero(crc & 0x0001)) {
					crc = (crc >> 1) ^ CRC_POLYNOMIAL;
				} else {
					crc = (crc >> 1);
				}
			}
		}
		int lsb = crc & 0x00ff;
		int msb = (crc >> 8) & 0x00ff;
		return (lsb << 8) | msb;
	}

	/**
	 * 写日期至ByteBuf<br>
	 * 从左到右，每个字节依次为年、月、日、时、分、秒，以16进制表示。
	 */
	public static void writeDate(Date date, ByteBuf dataBuf) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int i = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		dataBuf.writeByte(toByteForDate(y % 100));
		dataBuf.writeByte(toByteForDate(m));
		dataBuf.writeByte(toByteForDate(d));
		dataBuf.writeByte(toByteForDate(h));
		dataBuf.writeByte(toByteForDate(i));
		dataBuf.writeByte(toByteForDate(s));
	}

	/**
	 * TODO 从ByteBuf读取日期
	 * 
	 * @param dataBuf
	 *            [Time(6)，SN(5)，State(1)，Msgok(1)，Reserve(19 bytes)]
	 */
	public static Date readDate(ByteBuf dataBuf) {
		byte[] time = new byte[6];
		dataBuf.readBytes(time, 0, time.length);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 100 * (c.get(Calendar.YEAR) / 100) + time[0]);
		c.set(Calendar.MONTH, time[1] - 1);
		c.set(Calendar.DAY_OF_MONTH, time[2]);
		c.set(Calendar.HOUR_OF_DAY, time[3]);
		c.set(Calendar.MINUTE, time[4]);
		c.set(Calendar.SECOND, time[5]);
		return c.getTime();
	}

	/**
	 * 取第pos个字节
	 * 
	 * @param x
	 *            长整数
	 * @param pos
	 *            数字大的表示高位。从0开始。
	 * @return pos位置上的字节
	 */
	public static byte byteAt(long x, int pos) {
		return (byte) ((x >>> (8 * pos)) & 0xff);
	}

	/**
	 * 位运算-向左移动<tt>8*byteOffset</tt>位
	 * 
	 * @param b
	 *            the digit
	 * @param byteOffset
	 *            向左移动字节数(<tt>8*byteOffset</tt>位)
	 * @return 位移后的整数
	 */
	public static long shiftLeft(byte b, int byteOffset) {
		return (0xff & b) << (8 * byteOffset);
	}

	private static short toByteForDate(int i) {
		return (short) i;
	}

	public static boolean notZero(int k) {
		return k != 0L;
	}

	public static String hexDump(byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		for (byte b : buffer) {
			sb.append(' ');
			String x = Integer.toHexString(0xff & b);
			if (x.length() < 2) {
				sb.append('0');
			}
			sb.append(x);
		}
		return sb.substring(1);
	}

	public static String leftPad(String str, int len, char pad) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len - str.length(); i++) {
			sb.append(pad);
		}
		sb.append(str);
		return sb.toString();
	}

}
