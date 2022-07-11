package com.cloud.cang.pay;


/***
 * 免密服务声明
 */
public class FreeServicesDefine {

    /***
     * 支付宝免密签约（代扣协议）
     * 请求参数：{@link SignDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ALIPAY_SIGN = "com.cloud.cang.pay.ws.AlipayFreeService.sign";

    /***
     * 支付宝免密协议查询（代扣协议）
     * 请求参数：{@link QuerySignDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<SignResult>}
     */
    public static final String ALIPAY_QUERY_SIGN = "com.cloud.cang.pay.ws.AlipayFreeService.querySign";

    /***
     * 支付宝免密解约（代扣协议）
     * 请求参数：{@link UnsignDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ALIPAY_UNSIGN = "com.cloud.cang.pay.ws.AlipayFreeService.unsign";

    /***
     * 支付宝免密支付接口
     * 请求参数：{@link FreePaymentDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<FreePaymentResult>}
     */
    public static final String ALIPAY_PAYMENT = "com.cloud.cang.pay.ws.AlipayFreeService.payment";

    /***
     * 支付宝免密支付且签约接口
     * 请求参数：{@link FreePaymentDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ALIPAY_PAYMENT_AND_SIGN = "com.cloud.cang.pay.ws.AlipayFreeService.paymentAndSign";

    /***
     * 支付宝免密支付查询接口
     * 请求参数：{@link PaymentDto}
     * 返回参数：ResponseVo<QueryPaymentResult>
     */
    public static final String ALIPAY_QUERY_PAYMENT = "com.cloud.cang.pay.ws.AlipayFreeService.queryPayment";

    /***
     * 支付宝免密支付撤销接口
     * 请求参数：{@link PaymentDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ALIPAY_CANCEL_PAYMENT = "com.cloud.cang.pay.ws.AlipayFreeService.cancelPayment";

    /***
     * 支付宝免密支付关闭接口
     * 请求参数：{@link PaymentDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String ALIPAY_CLOSE_PAYMENT = "com.cloud.cang.pay.ws.AlipayFreeService.closePayment";


    /***
     * 微信免密签约（代扣协议）
     * 请求参数：{@link SignDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String WECHAT_SIGN = "com.cloud.cang.pay.ws.WechatFreeService.sign";

    /***
     * 微信支付分开通
     * 请求参数：{@link SignDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String WECHAT_OPEN = "com.cloud.cang.pay.ws.WechatFreeService.openService";

    /***
     * 微信免密协议查询（代扣协议）
     * 请求参数：{@link QuerySignDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<WechatSignResult>}
     */
    public static final String WECHAT_QUERY_SIGN = "com.cloud.cang.pay.ws.WechatFreeService.querySign";

    /***
     * 微信免密解约（代扣协议）
     * 请求参数：{@link UnsignDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String WECHAT_UNSIGN = "com.cloud.cang.pay.ws.WechatFreeService.unsign";

    /***
     * 微信免密代扣支付接口
     * 请求参数：{@link FreePaymentDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<FreePaymentResult>}
     */
    public static final String WECHAT_PAYMENT = "com.cloud.cang.pay.ws.WechatFreeService.payment";

    /***
     * 微信免密支付查询接口
     * 请求参数：{@link PaymentDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<QueryWechatFreePayResult>}
     */
    public static final String WECHAT_QUERY_PAYMENT = "com.cloud.cang.pay.ws.WechatFreeService.queryPayment";
    /***
     * 微信支付分创建订单
     * 请求参数：{@link CreatSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<CreatSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_CREAT_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.creatSmartretailOrder";
    /***
     * 微信支付分创建订单
     * 请求参数：{@link CreatSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<CreatSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_CREAT_ORDER_N = "com.cloud.cang.pay.ws.WechatFreeService.creatSmartretailOrderN";
    /***
     * 微信支付分查询订单
     * 请求参数：{@link QuerySmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<QuerySmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_QUERY_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.querySmartretailOrder";

    /***
     * 微信支付分完结订单
     * 请求参数：{@link EndSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<EndSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_END_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.endSmartretailOrder";
    /***
     * 微信支付分完结订单
     * 请求参数：{@link EndSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<EndSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_END_ORDER_N = "com.cloud.cang.pay.ws.WechatFreeService.endSmartretailOrderN";


    /***
     * 微信支付分生成订单  (创建订单,查询订单完结凭证,完结订单)
     * 请求参数：{@link CreatSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<EndSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_DENERATING_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.deneratingOrders";
    /***
     * 微信支付分查询并且完结订单  (查询订单完结凭证,完结订单)
     * 请求参数：{@link QueryAndEndSmartretailOrderDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<EndSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_QUERY_AND_END_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.creatAndEndSmartretailOrder";

    /***
     * 微信支付分撤销订单
     * 请求参数：{@link CancelSmartretailOrdersDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<CancelSmartretailOrderResult>}
     */
    public static final String WECHAT_POINT_CANCEL_ORDER = "com.cloud.cang.pay.ws.WechatFreeService.cancelOrders";
    /***
     * 微信支付分查询用户开启状态
     * 请求参数：{@link QueryUserAvaiDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<QueryUserAvaiResult>}
     */
    public static final String WECHAT_POINT_QUERY_USER_AVAILABILITY = "com.cloud.cang.pay.ws.WechatFreeService.queryUserAvailability";

    /***
     * vendstop 推送订单
     * 请求参数：{@link FreePaymentDto}
     * 返回参数：{@link com.cloud.cang.common.ResponseVo<FreePaymentResult>}
     */
    public static final String VENDSTOP_PAYMENT = "com.cloud.cang.pay.ws.VendstopService.pushOrder";

}
