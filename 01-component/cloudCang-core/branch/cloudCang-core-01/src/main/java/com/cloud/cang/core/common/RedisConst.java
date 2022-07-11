package com.cloud.cang.core.common;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.model.sh.MerchantClientConf;

/**
 * Redis缓存信息
 */
public class RedisConst {


    //后台系统登录验证码
    public static final String MGR_LOGIN_CODE = "MGR_LOGIN_CODE_";
    public static final int MGR_LOGIN_CODE_DURATION = 24 * 3600;

    //------------商户信息缓存--------------------------
    /**
     * 商户信息缓存 主KEY
     */
    public static final String MERCHANT_INFO = "MERCHANT_INFO";
    /**
     * 从KEY: Redis前缀_+商户编号编号 商户信息  ${@link JSON}字符串
     */
    public static final String MERCHANT_INFO_DETAILS = "MERCHANT_INFO_DETAILS_";
    /**
     * 从KEY: Redis前缀_+商户编号编号 商户客户端配置  ${@link MerchantClientConf}对象
     */
    public static final String SH_CLIENT_CONFIG = "SH_CLIENT_CONFIG_";
    /**
     * 从KEY: Redis前缀_+商户编号编号 微信商户配置  ${@link JSON}字符串
     */
    public static final String SH_WECHAT_CONFIG = "SH_WECHAT_CONFIG_";
    /**
     * 从KEY: Redis前缀_+商户编号编号 支付宝商户配置 ${@link JSON}字符串
     */
    public static final String SH_ALIPAY_CONFIG = "SH_ALIPAY_CONFIG_";


    /**
     * 平台昨日数据统计+商户编号 ${@link JSON}字符串
     */
    public static final String YESTERDAY_PLATFORM_DATA_INFO = "yesterday_platform_data_info_";
    /**
     * 平台每天数据统计+商户编号 ${@link JSON}字符串
     */
    public static final String YESTERDAY_TODAY_PLATFORM_DATA_INFO = "yesterday_today_platform_data_info_";
    /**
     * 昨日交易额取值为 YESTERDAY_TRADE_AMOUNT_PREDIX+yyyyMMdd ${@link JSON}字符串
     */
    public static final String YESTERDAY_ORDER_AMOUNT_PREDIX = "yesterday_order_amount_";
    /**
     * 平台实时数据统计+商户编号 ${@link JSON}字符串
     */
    public static final String CURRENT_PLATFORM_DATA_INFO = "current_platform_data_info_";


    //------------网站运营位--------------------------
    /**
     * 网站运营位 key CODE为运营位对像的key
     */
    public static final String WZ_REGIO_MAIN = "WZ_REGIO_MAIN";

    /**
     * 网站运营位+商户编号 主key
     */
    public static final String WZ_REGIO = "WZ_REGIO_";
    /**
     * 网站运营广告位+商户编号 主key
     */
    public static final String WZ_REGIO_ADVERTIS = "WZ_REGIO_ADVERTIS_";
    /**
     * 网站运营系统公告位+商户编号 主key
     */
    public static final String WZ_REGIO_ANNOUNCEMENT = "WZ_REGIO_ANNOUNCEMENT_";
    /**
     * 从key: WZ_REGIO_+模块编号+商户编号
     */
    public static final String WZ_REGIO_DETAIL_ = "WZ_REGIO_DETAIL_";
    //----------------------------------------------------

    //----------------------平台应用管理------------------
    /**
     * 平台应用 key 应用appid
     */
    public static final String OP_APP_MANAGE = "OPEN_SDK";

    /**
     * 云端柜子实时盘货key
     */
    public static final String CLOUD_REALTIME_DISTINGUISH = "CLOUD_REALTIME_DISTINGUISH";
}
