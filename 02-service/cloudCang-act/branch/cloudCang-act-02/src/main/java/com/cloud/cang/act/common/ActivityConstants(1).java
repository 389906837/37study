package com.cloud.cang.act.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动相关常量
 * @author Hunter
 */
public class ActivityConstants implements Serializable{

	private static final long serialVersionUID = 1051563432412611635L;
	
	/*------------------------------券类型-------------------------------------*/

	/**现金券*/
	public static final Integer ICOUPON_TYPE_XIANJIN=10;

	/**满减券*/
	public static final Integer ICOUPON_TYPE_MANJIAN=20;

	 /**抵扣券*/
    public static final Integer ICOUPON_TYPE_DIKOU=30;

    /**商品券*/
    public static final Integer ICOUPON_TYPE_SHANGPIN=40;
    
    public final static Map<Integer,String> couponTypeMap = new HashMap<Integer,String>();  
    static {  
    	couponTypeMap.put(ICOUPON_TYPE_DIKOU, "抵扣券");  
    	couponTypeMap.put(ICOUPON_TYPE_MANJIAN, "满减券");  
    	couponTypeMap.put(ICOUPON_TYPE_XIANJIN, "现金券");  
    	couponTypeMap.put(ICOUPON_TYPE_SHANGPIN, "商品券");  
    } 
	
	/*------------------------------券有效期类型-------------------------------------*/

	/**券有效期类型-天*/
	public static final Integer COUPON_VALIDITY_TYPE_DAY=1;
	
	/**券有效期类型-月*/
	public static final Integer COUPON_VALIDITY_TYPE_MONTH=2;
	
	/**券有效期类型-指定日期*/
	public static final Integer COUPON_VALIDITY_TYPE_TARGET=3;
	
	/*------------------------------券状态-------------------------------------*/
	
	/**券状态：未使用*/
	public static final Integer COUPON_STATUS_NOTUSE=1;
	
	/**券状态：已使用*/
	public static final Integer COUPON_STATUS_USE=2;
	
	/**券状态：冻结*/
	public static final Integer COUPON_STATUS_FREEZE=3;
	
	/**券状态：已失效*/
	public static final Integer COUPON_STATUS_INVALID=4;
	
	/**券状态：删除*/
	public static final Integer COUPON_STATUS_DELETE=5;
	
	/**券状态：锁定*/
	public static final Integer COUPON_STATUS_LOCKED=6;
	
	/*--------------------------------活动类型-----------------------------------*/
	
	/**活动类型：积分*/
	public static final Integer TYPE_INTEGRAL=1;
	
	/**活动类型：劵*/
	public static final Integer TYPE_COUPON=2;
	
	/*-------------------------------积分计算方式------------------------------------*/
	
	
	/**不限制*/
	public static final Integer NO_LIMIT=0;
	
	/**只有一次*/
	public static final Integer ONLY_ONCE=1;
	
	/**
	 * 共一次
	 */
	public static final Integer INTE_CAL_METHOD_FIRST = 10;
	
	/**
	 * 按天限制
	 */
	public static final Integer INTE_CAL_METHOD_DAY = 20;
	
	
	
	/**固定分值*/
	public static final Integer INTE_CAL_METHOD_FIXED=20;
	
	/**按比例*/
	public static final Integer INTE_CAL_METHOD_RATIO=10;
	
	
	
    /**来源单据类型编号*/
	public static final String SOURCE_TYPE_CODE="SP000006";
	
	/**
	 * 宝友日 对应运营参数key
	 */
	public static final String HURBAO_DAY ="hurbaoDay";
	
	
	/****************************券码状态**************************/
	
	/**
	 * 未兑换
	 */
	public static final Integer NOT_EXCHANGE =  1;
	
	/**
	 * 兑换中
	 */
	public static final Integer EXCHANGING = 2;
	
	/**
	 * 已兑换
	 */
	public static final Integer EXCHANGED = 3;
	
	/**
	 * 兑换失败
	 */
	public static final Integer EXCHANGE_ERROR = 4;
	
	/**
	 * 可多次兑换
	 */
	public static final Integer CAN_EXCHANGE_MANY_TIMES = 5;
	
	/**
	 * 券码兑换次数限制 仅有一次
	 */
	public static final Integer EXCHANGE_LIMIT_ONLY_ONCE = 10;
	
	/**
	 * 券码兑换次数限制 每月一次
	 */
	public static final Integer EXCHANGE_LIMIT_MONTH_ONCE = 20;
	
}
