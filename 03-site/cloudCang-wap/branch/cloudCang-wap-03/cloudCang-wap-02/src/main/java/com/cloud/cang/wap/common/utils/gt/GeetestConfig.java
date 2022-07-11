package com.cloud.cang.wap.common.utils.gt;


/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "9da9a9c42b1c5139142d70b71039e359";
	private static final String geetest_key = "5720583911fbc4d776c901c6bd73941f";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
