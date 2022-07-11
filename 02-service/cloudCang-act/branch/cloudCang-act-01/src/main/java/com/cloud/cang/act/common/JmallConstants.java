package com.cloud.cang.act.common;

/**
 * 积分商城常量类
 * @author Liuzhuo
 * @version 1.0
 */
public class JmallConstants {
	//--------------------积分商品状态---------
	/**积分商品状态：待审核*/
	public static final int  COMMODITYINFO_ISTATUS_WAIT_TRIAL=1;
	/**积分商品状态：审核驳回*/
	public static final int  COMMODITYINFO_ISTATUS_NO_PASS=2;
	/**积分商品状态：已发布*/
	public static final int  COMMODITYINFO_ISTATUS_PASS=3;
	/**积分商品状态：已售罄*/
	public static final int  COMMODITYINFO_ISTATUS_OVER=4;
	//----------------------------------------------
	
	//--------------------积分商品类型---------
	/**积分商品类型：实物商品*/
	public static final String  ICOMMODITYTYPE_PHYSICAL="1";
	/**积分商品类型：虚拟商品*/
	public static final String  ICOMMODITYTYPE_VIRTUAL="2";
	/**积分商品类型：内部券*/
	public static final String  ICOMMODITYTYPE_COUPON="3";
	//----------------------------------------------
	
	//--------------------虚拟商品类型---------
	/**虚拟商品类型：话费*/
	public static final int  ICAT_COST=10;
	/**虚拟商品类型：流量*/
	public static final int  ICAT_FLOW=20;
	/**虚拟商品类型：加油卡*/
	public static final int  ICAT_GSA=30;
	//----------------------------------------------
	
	//--------------------虚拟商品渠道---------
	/**虚拟商品渠道：移动*/
	public static final String  ISUPPORT_COMPANY_YD_10="移动";
	/**虚拟商品渠道：联通*/
	public static final String  ISUPPORT_COMPANY_LT_20="联通";
	/**虚拟商品渠道：电信*/
	public static final String  ISUPPORT_COMPANY_DX_30="电信";
	/**虚拟商品渠道：中石油*/
	public static final String  ISUPPORT_COMPANY_ZSY_40="中石油";
	/**虚拟商品渠道：中石化*/
	public static final String  ISUPPORT_COMPANY_ZSH_50="中石化";
	//----------------------------------------------
	
	//--------------------商品订单状态---------
	/**商品订单状态：待充值*/
	public static final int  ORDER_ISTATUS_WAIT=1;
	/**商品订单状态：充值中*/
	public static final int  ORDER_ISTATUS_ING=2;
	/**商品订单状态：充值成功*/
	public static final int  ORDER_ISTATUS_OK=3;
	/**商品订单状态：充值失败*/
	public static final int  ORDER_ISTATUS_FAIL=4;
	/**商品订单状态：退回 */
	public static final int  ORDER_ISTATUS_RETURN=5;
	/**商品订单状态：异常*/
	public static final int  ORDER_ISTATUS_ERROR=6;
	/**商品订单状态：人工处理*/
	public static final int  ORDER_ISTATUS_DELL=7;
	//----------------------------------------------
}
