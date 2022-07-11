/**
 * 
 */
package com.cang.os.common.utils;

import java.util.regex.Pattern;

/**
 * mongoDb工具类
 * @author hunter
 *
 */
public class MongoDbUtil {
	
	/**
	 * 模糊查询值
	 * @param value
	 * @return
	 */
	public static Pattern getLikeQueryCondition(String value){
		Pattern pattern = Pattern.compile("^.*"+value+".*$", Pattern.CASE_INSENSITIVE);
		return pattern;
		
	}

}
