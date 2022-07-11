package com.cloud.cang.pay.vendstop;

import com.cloud.cang.zookeeper.utils.EvnUtils;

/**
 * @program: 37cang
 * @description: 常量类
 * @author: qzg
 * @create: 2019-08-08 13:58
 **/
public class VendstopConstant {
    //venstop提供的调用api的key
    public static final String API_KEY = "57b0ddf3-87ef-4ff3-a11f-48b8534accb9";
    /**
     * vendstop请求地址
     */
    public static class API{
        private static final String PREFIX = EvnUtils.getValue("vendstop.api.url");
        private static final String VERSION = "/v1";
        private static final String BASE_URL = PREFIX + VERSION;
        //授权
        public static final String AUTH_USER = BASE_URL + "/authUser";
        //推送订单
        public static final String PUSH_ORDER = BASE_URL + "/pushOrder";
        //退款
        public static final String REFUND_ORDER =  BASE_URL + "/refundOrder";
        //开门成功
        public static final String OPEN_SUCCESS =  BASE_URL + "/user/createOrder";
    }


}
