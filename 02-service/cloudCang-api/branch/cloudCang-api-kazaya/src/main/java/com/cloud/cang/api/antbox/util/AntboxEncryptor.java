package com.cloud.cang.api.antbox.util;


import com.cloud.cang.api.antbox.protocol.AntboxDataPkg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * 蚂蚁盒子12bytes日期加密算法
 *
 * @author yong.ma
 * @since 1.3
 */
public class AntboxEncryptor {
    public static final byte C1 = 0x3A;
    private static final byte[] D1 = {0x4D, 0x41, 0x59, 0x49, 0x48, 0x45, 0x5A, 0x49, 0x2E, 0x43, 0x4F, 0x4D};
    private static final byte[] D2 = {0x52, 0x46, 0x49, 0x44, 0x48, 0x46, 0x52, 0x45, 0x41, 0x44, 0x45, 0x52};

    static final int BUFFER_LEN = D1.length;

    private static AntboxEncryptor ENCRYPTOR = new AntboxEncryptor();

    private AntboxEncryptor() {

    }

    public static AntboxEncryptor getInstance() {
        return ENCRYPTOR;
    }

    /**
     * 12bytes日期加密
     *
     * @param s1_arr 动态密钥（12bytes）
     * @param sn     序列号（5bytes）
     * @param t1     日期（明文）
     * @return 密文（12bytes）
     */
    public byte[] encrypt(byte[] s1_arr, long sn, Date t1) {
        ByteBuf buf = Unpooled.buffer(8);
        buf.writeLong(sn);
        byte[] dst = new byte[AntboxDataPkg.SN_BYTES_NUM];
        buf.getBytes(8 - dst.length, dst);
        return encrypt(s1_arr, dst, t1);
    }

    /**
     * 12bytes日期加密
     *
     * @param s1_arr 动态密钥（12bytes）
     * @param sn     序列号（5bytes）
     * @param t1     日期（明文）
     * @return 密文（12bytes）
     */
    public byte[] encrypt(byte[] s1_arr, byte[] sn, Date t1) {
        ByteBuf d3_arr = Unpooled.buffer(BUFFER_LEN);
        d3_arr.writeBytes(sn);
        d3_arr.writeByte(C1);
        AntboxUtil.writeDate(t1, d3_arr);
        return encrypt(s1_arr, d3_arr.array());
    }

    /**
     * 12bytes日期加密
     *
     * @param s1_arr 动态密钥（12bytes）
     * @param d3_arr 明文（12bytes）
     * @return 密文（12bytes）
     */
    public byte[] encrypt(byte[] s1_arr, byte[] d3_arr) {
        // s2
        byte[] s2 = xor(s1_arr, D1);

        // s3
        ByteBuf s3 = Unpooled.buffer(BUFFER_LEN);
        s3.writeBytes(s2, 1, s2.length - 1);
        s3.writeByte(s2[0]);// 再写入左边第一个byte => S3

        // s4
        byte[] s4 = xor(s3.array(), d3_arr);

        // s5
        ByteBuf s5 = Unpooled.buffer(BUFFER_LEN);
        // s4_arr右边末2个byte
        s5.writeBytes(s4, BUFFER_LEN - 2, 2);
        // s4_arr剩余的byte
        s5.writeBytes(s4, 0, BUFFER_LEN - 2);

        return xor(s5.array(), D2);
    }

    /**
     * 12bytes 日期格式解密
     *
     * @param dynamicSecretKeyBytes
     * @param ciphertext
     * @return 明文
     */
    public byte[] decrypt(byte[] dynamicSecretKeyBytes, byte[] ciphertext) {
        // 反 位异或
        byte[] last = xor(ciphertext, D2);

        // 异或后的s5
        byte[] s5 = new byte[12];
        s5[10] = last[0];
        s5[11] = last[1];
        for (int i = 2; i < 12; i++) {
            s5[i - 2] = last[i];
        }

        // s2
        byte[] s2 = xor(dynamicSecretKeyBytes, D1);
        // s3
        ByteBuf s3 = Unpooled.buffer(BUFFER_LEN);
        s3.writeBytes(s2, 1, s2.length - 1);
        s3.writeByte(s2[0]);// 再写入左边第一个byte => S3

        return xor(s3.array(), s5);

    }


    /**
     * xor(arr1, arr2)<br>
     * 两个数组的长度必须一致
     */
    static byte[] xor(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = (byte) (arr1[i] ^ arr2[i]);
        }
        return result;
    }

}
