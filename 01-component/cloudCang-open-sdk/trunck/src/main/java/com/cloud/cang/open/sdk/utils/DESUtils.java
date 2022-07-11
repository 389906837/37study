package com.cloud.cang.open.sdk.utils;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * @ClassName: DESUtil
 * @Description: DES加密和解密
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午4:19:00
 * @version 1.0
 */
public class DESUtils {

    /** 安全密钥 */
    private String keyData = "ABCDEFGHIJKLMNOPQRSTWXYZabcdefghijklmnopqrstwxyz0123456789-_.";


    public DESUtils() {
    }

    /**
     * 功能：构造
     * @param key 秘钥
     */
    public DESUtils(String key) {
        this.keyData = key;
    }

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:29:39
     * @param source 源字符串
     * @return String 加密后的字符串
     * @throws UnsupportedEncodingException 编码异常
     * @Description:功能：加密 (UTF-8)
     */
    public String encrypt(String source) throws UnsupportedEncodingException {
        return encrypt(source, "UTF-8");
    }

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:30:17
     * @param encryptedData 被加密后的字符串
     * @return String 解密后的字符串
     * @throws UnsupportedEncodingException 编码异常
     * @Description:功能：解密 (UTF-8)
     */
    public String decrypt(String encryptedData)
            throws UnsupportedEncodingException {
        return decrypt(encryptedData, "UTF-8");
    }


    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:31:11
     * @param source 源字符串
     * @param charSet 编码格式
     * @return String 加密后的字符串
     * @throws UnsupportedEncodingException 编码异常
     * @Description:功能：加密
     */
    public String encrypt(String source, String charSet)
            throws UnsupportedEncodingException {
        String encrypt = null;
        byte[] ret = encrypt(source.getBytes(charSet));
        encrypt = new String(Base64.encode(ret));
        return encrypt;
    }


    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:33:23
     * @param encryptedData 被加密后的字符串
     * @param charSet 编码格式
     * @return String 解密后的字符串
     * @throws UnsupportedEncodingException
     * @Description:功能：解密
     */
    public String decrypt(String encryptedData, String charSet)
            throws UnsupportedEncodingException {
        String descryptedData = null;
        byte[] ret = descrypt(Base64.decode(encryptedData.toCharArray()));
        descryptedData = new String(ret, charSet);
        return descryptedData;
    }


    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:34:13
     * @param primaryData 原始数据
     * @return byte[]
     * @Description:加密数据 用生成的密钥加密原始数据
     */
    private byte[] encrypt(byte[] primaryData) {

        /** 取得安全密钥 */
        //byte rawKeyData[] = getKey();
        /** DES算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();

        /** 使用原始密钥数据创建DESKeySpec对象 */
        DESKeySpec dks = null;
        try {
            dks = new DESKeySpec(keyData.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        /** 创建一个密钥工厂 */
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
        SecretKey key = null;
        try {
            key = keyFactory.generateSecret(dks);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        /** Cipher对象实际完成加密操作 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        /** 用密钥初始化Cipher对象 */
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        /** 正式执行加密操作 */
        byte encryptedData[] = null;
        try {
            encryptedData = cipher.doFinal(primaryData);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        /** 返回加密数据 */
        return encryptedData;
    }

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午4:35:26
     * @param encryptedData 加密后的数据
     * @return byte[]
     * @Description:用密钥解密数据
     */
    private byte[] descrypt(byte[] encryptedData) {

        /** DES算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();

        /** 取得安全密钥 */
        //byte rawKeyData[] = getKey();

        /** 使用原始密钥数据创建DESKeySpec对象 */
        DESKeySpec dks = null;
        try {
            dks = new DESKeySpec(keyData.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        /** 创建一个密钥工厂 */
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /** 用密钥工厂把DESKeySpec转换成一个SecretKey对象 */
        SecretKey key = null;
        try {
            key = keyFactory.generateSecret(dks);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        /** Cipher对象实际完成加密操作 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        /** 用密钥初始化Cipher对象 */
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        /** 正式执行解密操作 */
        byte decryptedData[] = null;
        try {
            decryptedData = cipher.doFinal(encryptedData);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedData;
    }
}
