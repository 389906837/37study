package com.cloud.cang.open.sdk.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * @version 1.0
 * @Description: http请求签名工具类
 * @Author: zengzexiong
 * @Date: 2018年8月31日09:40:47
 */
public class SignUtils {

    private static final String MD5 = "MD5";         // md5算法
    private static final String SHA1 = "SHA-1";      // sha-1算法
    private static final String SHA256 = "SHA-256";  // sha-256算法

    public static void main(String[] args) {
        HashMap<String, String> signMap = new HashMap<String, String>();
        signMap.put("a", "01");
        signMap.put("c", "02");
        signMap.put("b", "03");
        String secret = "test";
        System.out.println("signMap:" + signMap.toString() + ",MD5签名:" + signByMD5(signMap, secret));
        System.out.println("signMap:" + signMap.toString() + ",SHA-1签名:" + signBySHA1(signMap, secret));
        System.out.println("signMap:" + signMap.toString() + ",SHA-256签名:" + signBySHA256(signMap, secret));
    }

    /**
     * MD5签名
     *
     * @param paramValues 待签名参数
     * @param privateKey  私钥
     * @return
     */
    public static String signByMD5(Map<String, String> paramValues, String privateKey) {
        return sign(paramValues, null, privateKey, MD5);
    }

    /**
     * SHA-1签名
     *
     * @param paramValues 待签名参数
     * @param privateKey  私钥
     * @return
     */
    public static String signBySHA1(Map<String, String> paramValues, String privateKey) {
        return sign(paramValues, null, privateKey, SHA1);
    }

    /**
     * SHA-256签名
     *
     * @param paramValues 待签名参数
     * @param privateKey  私钥
     * @return
     */
    public static String signBySHA256(Map<String, String> paramValues, String privateKey) {
        return sign(paramValues, null, privateKey, SHA256);
    }

    /**
     * 根据算法类型生成不同的加密字符串
     *
     * @param paramValues      加密参数
     * @param ignoreParamNames 忽略参数
     * @param privateKey       私钥
     * @param encryptType      加密类型
     * @return
     */
    private static String sign(Map<String, String> paramValues, List<String> ignoreParamNames, String privateKey, String encryptType) {
        try {
            StringBuffer sb = new StringBuffer();
            if (MapUtils.isEmpty(paramValues)) {
                return "";
            }
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            // 将map按参数名从小到大升序排序
            Collections.sort(paramNames);
            sb.append(privateKey);
            for (String paramName : paramNames) {
                String v = paramValues.get(paramName);
                // 如果值为空，跳过本次循环
                if (StringUtils.isBlank(v)) {
                    continue;
                }
                sb.append(paramName).append(v);
            }
            sb.append(privateKey);
            byte[] bytes = null;
            // 开始对字符串进行加密
            if (MD5.equals(encryptType)) {
                bytes = getMD5Digest(sb.toString());
            } else if (SHA1.equals(encryptType)) {
                bytes = getSHA1Digest(sb.toString());
            } else if (SHA256.equals(encryptType)) {
                bytes = getSHA256Digest(sb.toString());
            }
            String sign = byte2hex(bytes);
            return sign;
        } catch (IOException e) {
            throw new RuntimeException("加密签名计算错误", e);
        }
    }

    /**
     * SHA-256加密算法
     *
     * @param data 加密字符串
     * @return
     * @throws IOException
     */
    private static byte[] getSHA256Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * SHA-1加密算法
     *
     * @param data
     * @return
     * @throws IOException
     */
    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * md5加密
     *
     * @param data 加密字符串
     * @return MD5加密后的字节数组
     * @throws IOException
     */
    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }


    /**
     * 将字节数组转为16进制字符串
     *
     * @param bytes 数据
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
