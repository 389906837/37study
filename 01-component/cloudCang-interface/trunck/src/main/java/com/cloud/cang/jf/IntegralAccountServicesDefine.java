package com.cloud.cang.jf;

/**
 * 积分账户服务定义
 * @author zhouhong
 *
 */
public class IntegralAccountServicesDefine {
	
	/**
	 * 积分账户开户 
	 * @param {@link com.cloud.cang.jf.OpenIntegralAccountDto}
	 * @return IntegralAccount 接收参数执行类型
	 */
	public static final String INTEGRAL_OPEN_ACCOUNT_SERVICE ="com.cloud.cang.pac.ws.PACService.openAccount";
	
	
	/**
	 * 积分账户积分变更
	 * @param {@link ChangeIntegralDto}
	 */
	public static final String INTEGRAL_CHANGE_INTEGRAL_SERVICE ="com.cloud.cang.pac.ws.PACService.changeIntegral";
	
	/**
	 * 积分账户积分变更返回vo
	 * @param {@link ChangeIntegralDto}
	 */
	public static final String INTEGRAL_CHANGE_INTEGRAL_VO_SERVICE ="com.cloud.cang.pac.ws.PACService.changeIntegralForRes";
	
	
	/**
	 * 批量积分账户积分变更
	 * @param {@link List<ChangeIntegralDto>}
	 */
	public static final String BATCH_INTEGRAL_CHANGE_INTEGRAL_SERVICE ="com.cloud.cang.pac.ws.PACService.batchChangeIntegral";
	
	
	
	
	public static final String CLEAR_ALL_ACCOUNT_INTEGRAL="com.cloud.cang.pac.ws.PACService.clearUserIntegral";

}
