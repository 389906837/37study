package com.cloud.cang.api.antbox.util;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.ChannelAttrKey;
import com.cloud.cang.api.antbox.exception.UnexpectedBufferLengthException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Date;

/**
 * Antbox Utilities.例如：计算AntboxObject的CRC值。
 *
 * @author yong.ma
 * @since 0.0.1
 */
public abstract class AntboxUtil {
    // public static final String INVENTORY_COUNT =
    // AntboxConfig.get("INVENTORY_COUNT");
    // public static final String INVENTORY_TIME =
    // AntboxConfig.get("INVENTORY_TIME");

    public static Gson GSON = new GsonBuilder().create();

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
        return calcCrc(buf, offset, 0);
    }

    /**
     * 循环冗余码校验（CRC）<br>
     * 长度为2个字节的CRC-16效验和,低字节在前.<br>
     * 计算包括了从MSB-LEN16开始到Data[]的全部数据，得到的CRC在传送时低字节在前。
     */
    public static int calcCrc(ByteBuf buf, int offset, int bytesToEnd) {
        int i, j;
        int crc = CRC_START_VALUE;
        final int LEN = buf.readableBytes();
        for (i = offset; i < LEN - bytesToEnd; i++) {
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
     * 从dateBytes读取日期
     *
     * @param dateBytes
     */
    public static Date readDate(byte[] dateBytes) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 100 * (c.get(Calendar.YEAR) / 100) + dateBytes[0]);
        c.set(Calendar.MONTH, dateBytes[1] - 1);
        c.set(Calendar.DAY_OF_MONTH, dateBytes[2]);
        c.set(Calendar.HOUR_OF_DAY, dateBytes[3]);
        c.set(Calendar.MINUTE, dateBytes[4]);
        c.set(Calendar.SECOND, dateBytes[5]);
        return c.getTime();
    }

    /**
     * 从ByteBuf读取日期
     *
     * @param dataBuf [Time(6)，SN(5)，State(1)，Msgok(1)，Reserve(19 bytes)]
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

    public static long readLong(ByteBuf dataBuf, int bytesNum) {
        byte[] buf = new byte[bytesNum];
        dataBuf.readBytes(buf);
        return new BigInteger(1, buf).longValue();
    }

    /**
     * 取第pos个字节
     *
     * @param x   长整数
     * @param pos 数字大的表示高位。从0开始。
     * @return pos位置上的字节
     */
    public static byte byteAt(long x, int pos) {
        return (byte) ((x >>> (8 * pos)) & 0xff);
    }

    /**
     * 位运算-向左移动<tt>8*byteOffset</tt>位
     *
     * @param b          the digit
     * @param byteOffset 向左移动字节数(<tt>8*byteOffset</tt>位)
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

    public static BoxContext getAntboxChannelContext(ChannelHandlerContext ctx) {
        if (ctx == null) {
            return null;
        }
        return ctx.channel().attr(ChannelAttrKey.ANTBOX_CHANNEL_CONTEXT).get();
    }

    public static void bindAntboxChannelContext(Channel ch, BoxContext val) {
        if (ch == null) {
            return;
        }
        ch.attr(ChannelAttrKey.ANTBOX_CHANNEL_CONTEXT).setIfAbsent(val);
    }

    public static String hexDump(ByteBuf buf) {
        if (buf == null) {
            return "";
        }
        return ByteBufUtil.hexDump(buf);
    }

    public static ByteBuf byteWrap(int val) {
        ByteBuf data = Unpooled.buffer(1);
        data.writeByte(val);
        return data;
    }

    public static void checkLength(ByteBuf byteBuf, int expectedLength) {
        if (byteBuf == null || byteBuf.readableBytes() != expectedLength) {
            throw new UnexpectedBufferLengthException(
                    byteBuf == null ? -1 : byteBuf.readableBytes(), expectedLength);
        }
    }

    public static int length(ByteBuf byteBuf) {
        if (byteBuf == null) {
            return 0;
        } else {
            return byteBuf.readableBytes();
        }
    }

    public static boolean hasLength(ByteBuf byteBuf) {
        return length(byteBuf) > 0;
    }

    public static short getUnsignedByteAt(ByteBuf buf, int index) {
        return getUnsignedByteAt(buf, index, (short) 0xFF);
    }

    public static short getUnsignedByteAt(ByteBuf buf, int index, short unknownByte) {
        if (length(buf) > index) {
            return buf.getUnsignedByte(index);
        } else {
            return unknownByte;
        }
    }


    /**
     * 获取远程客户端 ip
     *
     * @param channelHandlerContext
     * @return
     */
    public static String getRemoteIp(ChannelHandlerContext channelHandlerContext) {
        InetSocketAddress socketAddress = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
        InetAddress inetaddress = socketAddress.getAddress();
        String ipAddress = inetaddress.getHostAddress(); // IP address of client
        return ipAddress;
    }

    /**
     * 判断是否lvs监听 , 通过请求的客户端ip做判断
     *
     * @param ipAddress
     * @return
     */
    public static boolean isLvsListen(String ipAddress) {
        return ipAddress.startsWith("100.116")
                || ipAddress.startsWith("10.159")
                || ipAddress.startsWith("10.155")
                || ipAddress.startsWith("10.49")
                || ipAddress.startsWith("100.64")
                || ipAddress.startsWith("100.109")
                || ipAddress.startsWith("100.97")
                || ipAddress.startsWith("10.158");
    }

}
