package com.cloud.cang.core.common;

import com.cloud.cang.zookeeper.utils.EvnUtils;


/**
 * 支付配置参数
 *
 * @author zhouhong
 */
public class WechatConfigure {


    //这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	/*private static String key = EvnUtils.getValue("pay.weixin.key");

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = EvnUtils.getValue("pay.weixin.appID");

	private static String appSecret = EvnUtils.getValue("pay.weixin.appSecret");

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = EvnUtils.getValue("pay.weixin.mchID");

	//受理模式下给子商户分配的子商户号
	private static String subMchID = EvnUtils.getValue("pay.weixin.subMchID");

	//HTTPS证书的本地路径
	private static String certLocalPath = EvnUtils.getValue("pay.weixin.certLocalPath");

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = EvnUtils.getValue("pay.weixin.certPassword");*/

    //是否使用异步线程的方式来上报API测速，默认为异步模式
    private static boolean useThreadToDoReport = true;

    //交易方式
    public static String JSAPI_TRADE_TYPE = "JSAPI";//微信公众号支付
    public static String MWEB_TRADE_TYPE = "MWEB";//H5支付
    //机器IP

    //以下是几个API的路径：
    //0) 统一下单
    public static String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //1）被扫支付API
    public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    //2）被扫支付查询API
    public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

    //3）退款API
    public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //4）退款查询API
    public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

    //5）撤销API
    public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    //6）下载对账单API
    public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

    //7) 统计上报API
    public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

    //8) 订单支付回调
    public static String ORDER_CALLBACK_URL = EvnUtils.getValue("pay.wechat.notifyUrl");


    //9) 免密支付签约
    public static String FREE_SIGN_URL = "https://api.mch.weixin.qq.com/papay/entrustweb";

    //10) 免密支付签约
    public static String FREE_QUERY_SIGN_URL = "https://api.mch.weixin.qq.com/papay/querycontract";

    //11) 免密支付解约
    public static String FREE_UNSIGN_URL = "https://api.mch.weixin.qq.com/papay/deletecontract";

    //12) 免密支付解约
    public static String FREE_PAY_URL = "https://api.mch.weixin.qq.com/pay/pappayapply";

    //13) 免密支付查询
    public static String QUERY_FREE_PAY_URL = "https://api.mch.weixin.qq.com/pay/paporderquery";

    //14) 微信支付分查询用户是否可用
    public static String QUERY_USER_AVAILABILITY = "https://api.mch.weixin.qq.com/v3/payscore/user-service-state";

    //15) 微信支付分创建订单
    public static String CREAT_SMARTRETAIL_ORDER = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders";

    //15) 微信支付分完结订单
    public static String END_SMARTRETAIL_ORDER = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders//complete";

    //16) 微信支付分查询订单
    public static String QUERY_SMARTRETAIL_ORDER = "https://api.mch.weixin.qq.com/v3/payscore/smartretail-orders";

    private static String out_order_no;//

    public static boolean isUseThreadToDoReport() {
        return useThreadToDoReport;
    }

    public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
        WechatConfigure.useThreadToDoReport = useThreadToDoReport;
    }

    public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";


    public static void setHttpsRequestClassName(String name) {
        HttpsRequestClassName = name;
    }


}
