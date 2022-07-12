package com.cloud.cang.mgr.common;

/**
 * 消息常量
 * @author zhouhong
 * @version 1.0
 */
public class Constrants {
    
    /**
     * 签名
     */
    public static final String MESSAGE_SIGNATURE ="【37号仓】";
    
    /**
     * 梦网短信Code
     */
    public static final String SMS_MENGWANG_CODE ="SMS001";
    
    /**
     * 梦网营销短信Code
     */
    public static final String SMS_MENGWANG_CODE_SALE ="SMS006";
    
    /**
     * 漫道短信Code
     */
    public static final String SMS_MANDAO_CODE ="SMS002";
    /**
     * 建周短信Code
     */
    public static final String SMS_JIANZHAOU_CODE ="SMS003";
    /**
     * 创明营销短信Code
     */
    public static final String SMS_CHUANGMING_CODE ="SMS004";
    /**
     * SMS EMAIL
     */
    public static final String SMS_EMAIL_CODE ="SMS005";
    
    /**
     * 手机信息类型
     */
    public static final int MOBILE_MESSAGE = 1;
    
    /**
     * 邮件信息类型
     */
    public static final int EMAIL_MESSAGE = 2;
    
    /**
     * 消息KEY
     */
    public static final String MESSAGE_LIMIT_KEY = "message_limit_cache";

    /**
     * 消息KEY prefix
     */
    public static final String MESSAGE_LIMIT_USER_PREFIX = "msg_";
    
    /**
     * 协议定义
     * @author zhouhong
     * @version 1.0
     */
    public class InvestProtocol {
    	
    	/**
    	 * 债权pdf协议ID
    	 */
    	public static final String DEBT_PROTOCOL_PDF_ID="D81FD44540704948925C5ABD78219015";
    	
    	/**
    	 * 债权pdf协议ID
    	 */
    	public static final String DEBT_PROTOCOL_HTML_ID="178707C55584454C81FCF4BD15C8FD08";
    	
    }

    /**
     * 商品上架
     */
    public static final String COMMONDITY_SHELF = "shelf";

    /**
     * 商品下架
     */
    public static final String COMMONDITY_DROPOFF = "dropOff";

    /**
     * 商品上架
     */
    public static final String SKU_COMMONDITY_SHELF = "shelf";

    /**
     * 商品下架
     */
    public static final String SKU_COMMONDITY_DROPOFF = "dropOff";

    /**
     * 商品停用
     */
    public static final String SKU_COMMONDITY_DISABLE = "disable";

    /**
     * 商品启用
     */
    public static final String SKU_COMMONDITY_ENABLE = "enable";

    /**
     * 设备启用
     */
    public static final String DEVICE_ENABLE = "enable";

    /**
     * 设备禁用
     */
    public static final String DEVICE_DISABLE = "disable";

}
