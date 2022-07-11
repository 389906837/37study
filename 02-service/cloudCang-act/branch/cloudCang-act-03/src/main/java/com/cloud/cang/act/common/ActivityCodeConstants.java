package com.cloud.cang.act.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动参数常量
 * @author Hunter 
 * @version 1.0
 */
public class ActivityCodeConstants {
	
	/**
	 * 推荐注册
	 */
	public static final String RECOMMEND_REGIST ="AC001";
	
	/**
	 * 推荐开通托管
	 */
	public static final String RECOMMEND_OPEN_ACCOUNT ="AC002";
	
	/**
	 * 推荐投资
	 */
	public static final String RECOMMEND_INVEST="AC0005";
	
	/**
	 * 二级推荐开通托管
	 */
	public static final String SECOND_RECOMMED_OPEN_ACCOUNT="AC0028";
	
	/**
	 * 二级推荐复投
	 */
	public static final String SECOND_RECOMMED_REPEAT_INVEST="AC0027";
	
	/**
	 * 二级推荐首投
	 */
	public static final String SECOND_RECOMMED_FIRST_INVEST="AC0026";
	
	/**
	 * 二级推荐注册
	 */
	public static final String SENDCOD_RECOMMEND_REGIST ="AC0025";
	
    public final static Map<String,String> map = new HashMap<String,String>();  
    static {  
        map.put(RECOMMEND_REGIST, "推荐注册奖励");  
        map.put(RECOMMEND_OPEN_ACCOUNT, "推荐开托管账户奖励");  
        map.put(RECOMMEND_INVEST, "推荐投资奖励");  
    } 
	
	

}
