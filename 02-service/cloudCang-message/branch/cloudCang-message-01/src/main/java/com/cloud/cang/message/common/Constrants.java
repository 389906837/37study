package com.cloud.cang.message.common;

/**
 * 消息常量
 *
 * @author zhouhong
 * @version 1.0
 */
public class Constrants {

    /**
     * 签名
     */
    public static final String MESSAGE_SIGNATURE = "【互融宝】";

    /**
     * 梦网短信Code
     */
    public static final String SMS_MENGWANG_CODE = "SMS001";

    /**
     * 梦网营销短信Code
     */
    public static final String SMS_MENGWANG_MARKING_CODE = "SMS006";
    /**
     * 漫道短信Code
     */
    public static final String SMS_MANDAO_CODE = "SMS002";
    /**
     * 建周短信Code
     */
    public static final String SMS_JIANZHAOU_CODE = "SUPP000";
    /**
     * 创明营销短信Code
     */
    public static final String SMS_CHUANGMING_CODE = "SMS004";
    /**
     * SMS EMAIL
     */
    public static final String SMS_EMAIL_CODE = "SMS005";

    /**
     * 大汉三通短信Code
     */
    public static final String SMS_SANTONG_CODE = "SMS007";

    /**
     * 大汉三通营销短信Code
     */
    public static final String SMS_SANTONG_MARKING_CODE = "SMS008";

    /**
     * 玄武短信Code
     */
    public static final String SMS_XUANWU_CODE = "SUPP0007";

    /**
     * 玄武营销短信Code
     */
    public static final String SMS_XUANWU_MARKING_CODE = "SUPP0008";

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
     * 发送给内部人员消息(每日发送次数限制) 主KEY + 今天日期
     */
    public static final String INTERNAL_SMS = "internal_sms";


    /**
     * 协议定义
     *
     * @author zhouhong
     * @version 1.0
     */
    public class InvestProtocol {

        /**
         * 债权pdf协议ID
         */
        public static final String DEBT_PROTOCOL_PDF_ID = "D81FD44540704948925C5ABD78219015";

        /**
         * 债权pdf协议ID
         */
        public static final String DEBT_PROTOCOL_HTML_ID = "178707C55584454C81FCF4BD15C8FD08";

    }


}
