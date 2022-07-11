package com.cloud.cang.wap.common.utils;

import java.math.BigDecimal;

public class PriceUtil {

	
	/**
	 * 提供加法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static BigDecimal add(BigDecimal a1,BigDecimal a2){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).add(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 提供减法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal a1,BigDecimal a2){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 提供减法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param count
	 * @return
	 */
	public static BigDecimal sub(BigDecimal a1,int  count){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 提供乘法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param count
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal a1,int count){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 提供乘法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal a1,BigDecimal a2){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
	}
	
	/**
	 * 提供除法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param count
	 * @return
	 */
	public static BigDecimal divide(BigDecimal a1,int count){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP ),2,BigDecimal.ROUND_HALF_UP);
	}
	
	
	/**
	 * 提供除法计算，约定为先做四舍五入再做计算
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal a1,BigDecimal a2){
		return a1.setScale(2,BigDecimal.ROUND_HALF_UP).divide(a2.setScale(2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP );
	}
	
	
}
