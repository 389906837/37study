package com.cloud.cang.utils;

import java.security.MessageDigest;


/**
 *
 * @ClassName: MD5Util
 * @Description: 采用MD5加密解密
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午5:30:24
 * @version 1.0
 */
public class MD5Utils {

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:31:07
     * @param data 源数据
     * @return 加码后的数据
     * @Description:生成32位md5码
     */
    public static String string2MD5(String data){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = data.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:31:07
     * @param data 加码后的数据
     * @return 源数据
     * @Description:加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }
}
