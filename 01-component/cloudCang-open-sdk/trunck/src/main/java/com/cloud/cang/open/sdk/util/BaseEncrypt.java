package com.cloud.cang.open.sdk.util;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @version 1.0
 * @Description: 加密工具类
 * @Author: zhouhong
 * @Date: 2018/9/4 11:44
 */
public class BaseEncrypt {

    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv("AES/CBC/PKCS5Padding");

    public BaseEncrypt() {
    }

    public static String encryptContent(String content, String encryptType, String encryptKey, String charset) throws CloudCangException {
        if("AES".equals(encryptType)) {
            return aesEncrypt(content, encryptKey, charset);
        } else {
            throw new CloudCangException("当前不支持该算法类型：encrypeType=" + encryptType);
        }
    }

    public static String decryptContent(String content, String encryptType, String encryptKey, String charset) throws CloudCangException {
        if("AES".equals(encryptType)) {
            return aesDecrypt(content, encryptKey, charset);
        } else {
            throw new CloudCangException("当前不支持该算法类型：encrypeType=" + encryptType);
        }
    }

    private static String aesEncrypt(String content, String aesKey, String charset) throws CloudCangException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            //cipher.init(1, new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), "AES"), iv);
            cipher.init(1, new SecretKeySpec(aesKey.getBytes(), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encodeBase64(encryptBytes));
        } catch (Exception var6) {
            throw new CloudCangException("AES加密失败：Aescontent = " + content + "; charset = " + charset, var6);
        }
    }

    private static String aesDecrypt(String content, String key, String charset) throws CloudCangException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(initIv("AES/CBC/PKCS5Padding"));
            //cipher.init(2, new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES"), iv);
            cipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception var6) {
            throw new CloudCangException("AES解密失败：Aescontent = " + content + "; charset = " + charset, var6);
        }
    }

    private static byte[] initIv(String fullAlg) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception var5) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }
}
