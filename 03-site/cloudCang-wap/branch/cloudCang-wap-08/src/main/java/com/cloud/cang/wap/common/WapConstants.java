package com.cloud.cang.wap.common;


/**
 * @author zhouhong
 */
public class WapConstants {

    //微信授权码
    public static final String WEIXIN_SESSION_AUTH_CODE = "weixin_session_auth_code";
    //支付宝授权码
    public static final String ALIPAY_SESSION_AUTH_CODE = "alipay_session_auth_code";
    //微信联合登录session 用户信息
    public static final String WEIXIN_SESSION_USER = "accessUser";
    //支付宝联合登录session 用户信息
    public static final String ALIPAY_SESSION_USER = "accessAlipayUser";
    //登录错误次数前缀
    public static final String PREFIX_CACHE_SECURITY_CODE_ERROR_TIMES = "user_security_code_error_";
    // 验证码记录错误有效时间
    public static final int SECURITY_CODE_ERROR_TIMES_DURATION = 24 * 60 * 60;
    //开门有效期
    public static final String OPEN_DOOR_VALIDITY = "open_door_validity";
    //cookie 供应商NAME
    public static final String MERCHANT_COOKIE_NAME = "merchant_cookie_name";
    //cookie 设备编号NAME
    public static final String DEVICE_COOKIE_NAME = "device_cookie_name";
    //cookie 供应商有效期
    public static final String MERCHANT_COOKIE_VALIDITY = "merchant_cookie_validity";
    //cookie 设备编号有效期
    public static final String DEVICE_COOKIE_VALIDITY = "device_cookie_validity";
    //每页条数  常用
    public static final int PAGE_SIZE = 10;

    //缓存数据ID
    public class RedisConst {

        /**
         * 会员注册短信验证码 主key member_register_auth_code_+手机号
         */
        public static final String MEMBER_BIND_AUTH_CODE = "member_bind_auth_code_";
        /**
         * 会员授权短信验证码 主key member_bind_auth_trash_code+手机号
         */
        public static final String MEMBER_BIND_AUTH_TRASH_CODE = "member_bind_auth_trash_code_";

    }
}
