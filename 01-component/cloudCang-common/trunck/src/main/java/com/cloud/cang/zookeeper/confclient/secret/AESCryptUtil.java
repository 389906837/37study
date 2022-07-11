/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月6日
 * Description:DefaultSeedAES.java 
 */
package com.cloud.cang.zookeeper.confclient.secret;

import java.security.Key;

import com.cloud.cang.utils.secret.EncryptAES;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认加密seed实现AES加密处理
 * @author zhouhong
 * @version 1.0
 */
public class AESCryptUtil {

	private static final String encoding = "UTF-8";
	
	/**
	 * aes密钥
	 */
	private static String AES_KEY = "9b2ea105638049a2a4d29b8ca659ddaf";
	
	
	private static Key DEFAULT_KEY;
	static{
		try {
			DEFAULT_KEY= EncryptAES.initSecretKey(AES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println(decryptByKey(encryptByKey("111")));
	}
	
	/**
	 * 加密
	 */
	public static String encryptByKey(String inputContent) {
		try {
			byte[] val= EncryptAES.encrypt(inputContent.getBytes(encoding), DEFAULT_KEY);
			return StringUtil.parseByte2HexStr(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 */
	public static String decryptByKey(String content) {
		if (StringUtils.isBlank(content)) {
			return null;
		}
		try {
			byte[] oraVal=EncryptAES.decrypt(StringUtil.parseHexStr2Byte(content), DEFAULT_KEY);
			return new String(oraVal, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
}
