package com.cloud.cang.core.common;

/**
 * 支付宝配置参数
 * @author zhouhong
 *
 */
public class AlipayConfigure {
   /* //app_id
    public static String appID = EvnUtils.getValue("pay.alipay.appID");

    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
    public static String seller_id =  EvnUtils.getValue("pay.alipay.sellerID");

    // 商户的私钥,使用支付宝自带的openssl工具生成。
    public static String private_key = EvnUtils.getValue("pay.alipay.privateKey");

    // 支付宝的公钥
    public static String ali_public_key  = EvnUtils.getValue("pay.alipay.publicKey");

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url =  EvnUtils.getValue("pay.alipay.notifyUrl");

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //public static String return_url = "";



    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    public static String log_path = "D:\\log";*/

    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式 目前支持utf-8
    public static String charset = "utf-8";

    // 参数格式类型
    public static String format = "json";

    // 销售产品码
    public static String productCode = "QUICK_WAP_PAY";

    // 调用支付宝的接口名,固定的
    public static String UNIFIED_ORDER_API = "https://openapi.alipay.com/gateway.do";

}
