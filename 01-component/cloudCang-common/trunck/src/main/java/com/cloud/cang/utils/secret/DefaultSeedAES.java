/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月6日
 * Description:DefaultSeedAES.java 
 */
package com.cloud.cang.utils.secret;

import java.security.Key;

import org.apache.commons.lang3.StringUtils;

import com.cloud.cang.utils.StringUtil;

/**
 * 默认加密seed实现AES加密处理
 * @author zhouhong
 * @version 1.0
 */
public class DefaultSeedAES {

	private static final String encoding = "UTF-8";
	
	/**
	 * aes密钥
	 */
	private static String AES_KEY = "898ec99bd66d49ec9cb68574024fd45b";
	
	
	private static Key DEFAULT_KEY;
	static{
		try {
			DEFAULT_KEY=EncryptAES.initSecretKey(AES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String val=DefaultSeedAES.encryptByKey("431223198704042212");
		System.out.println("123456");
		System.out.println(val);
		System.out.println(DefaultSeedAES.decryptByKey(val));
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
