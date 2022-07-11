package com.cloud.cang.open.sdk.utils;


import java.io.UnsupportedEncodingException;


/**
 * @version 1.0
 * @Description: 字符串加密工具类
 * @Author: zengzexiong
 * @Date: 2018年8月30日19:08:25
 */
public class EncryptUtils {


    /**
     * 对字符串进行加密
     *
     * @param data
     * @return
     */
    public static String encryptStringUnZip(String data) {
        String encryptedData = "";
        //生成8位秘钥
        String secretKey = IdGenerator.randomUUID8();//秘钥
        //通过秘钥创建加密对象
        DESUtils desUtil = new DESUtils(secretKey);
        //加密数据
        String secretData = null;
        try {
            secretData = desUtil.encrypt(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //生成MD532位签名
        String md532 = MD5Utils.string2MD5(secretData);
        StringBuffer sb = new StringBuffer();
        //连接字符串
        sb.append(secretKey + md532 + secretData);
        encryptedData = sb.toString();
        return encryptedData;
    }

    /**
     * 对字符串进行加密（压缩）
     *
     * @param data
     * @return
     */
    public static String encryptStringZip(String data) {
        String encryptedData = "";
        //生成8位秘钥
        String secretKey = IdGenerator.randomUUID8();//秘钥
        //通过秘钥创建加密对象
        DESUtils desUtil = new DESUtils(secretKey);
        //加密数据
        String secretData = null;
        try {
            secretData = desUtil.encrypt(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //生成MD532位签名
        String md532 = MD5Utils.string2MD5(secretData);
        StringBuffer sb = new StringBuffer();
        //连接字符串
        sb.append(secretKey + md532 + secretData);
        //压缩
        encryptedData = ZipUtils.gzip(sb.toString());
        return encryptedData;
    }


    /**
     * 对字符串进行解密（未压缩）
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptStringUnZip(String data) throws Exception {
        String cipherText = data;
        String secretKey = cipherText.substring(0, 8);//秘钥
        String md532 = cipherText.substring(8, 40);//32位MD5签名
        String encryptedData = cipherText.substring(40);//加密后的数据
        String md532Temp = MD5Utils.string2MD5(encryptedData);//生成临时32位MD5签名 判断数据是否有更改
        if (!md532.equals(md532Temp)) {
            return null;
        }
        DESUtils desUtil = new DESUtils(secretKey);
        String clearText = desUtil.decrypt(encryptedData);
        return clearText;
    }


    /**
     * 对字符串进行解密(压缩过)
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptStringZip(String data) throws Exception {
        String cipherText = ZipUtils.gunzip(data);//先解压
        String secretKey = cipherText.substring(0, 8);//秘钥
        String md532 = cipherText.substring(8, 40);//32位MD5签名
        String encryptedData = cipherText.substring(40);//加密后的数据
        String md532Temp = MD5Utils.string2MD5(encryptedData);//生成临时32位MD5签名 判断数据是否有更改
        if (!md532.equals(md532Temp)) {
            return null;
        }
        DESUtils desUtil = new DESUtils(secretKey);
        String clearText = desUtil.decrypt(encryptedData);
        return clearText;
    }

}
