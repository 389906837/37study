package com.cloud.cang.core.common;

/**
 * 常量定义
 * @author zhouhong
 *
 */
public class CoreConstant {
    
  //重新开始
  public static final String NEW_BEGIN = "isNewBegin";
  	
  //数据库当前日期
  public static final String QUERY_DATE = "queryDate";
  
  //业务记录条数失效时间
  public static final int BIZ_OUT_OF_TIME = 7 * 24 * 60 * 60;

  //默认商户编号 运营参数
  public static final String DEFAULT_MERCHANT_CONFIG = "SP000106";
  //支付宝免密 参数配置
  public static final String ALIPAY_FREE_CONFIG = "SP000139";
  //微信免密 参数配置
  public static final String WECHAT_FREE_CONFIG = "SP000149";
  
  /**********************************系统代码**************************************/
  public static final String CANG_SITE_DATA_CODE = "SP000003";
  /**
   * WEB前置系统代码
   */
  public static final String CANG_WEB_SITE_CODE = "SYS-WEB";
  
  /**
   * WAP系统代码
   */
  public static final String CANG_WAP_SITE_CODE = "SYS-WAP";
  
  
  /**
   * 参与活动来源Session key
   */
  public static final String SOURCE_JOIN_ACTIVITY_CODE_SESSION_KEY ="source_join_activity_code_seesion_key";
  
  /**
   * 来源渠道Session key
   */
  public static final String SOURCE_CHANNEL_SESSION_KEY ="souece_channel_session_key";
  
  
  
  


}
