package com.cloud.cang.vendstop;

import com.cloud.cang.vendstop.dto.AuthUserDto;

/**
 * @program: 37cang
 * @description: Vendstop接口
 * @author: qzg
 * @create: 2019-08-08 14:41
 **/
public class VendstopDefine {

    /**
     * 用户授权
     * 请求参数：{@link AuthUserDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String AUTH_USER = "com.cloud.cang.pay.ws.VendstopService.authUser";

    /**
     * 推送订单
     * 请求参数：{@link AuthUserDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String PUSH_ORDER = "com.cloud.cang.pay.ws.VendstopService.pushOrder";

    /**
     * 退款
     * 请求参数：{@link AuthUserDto}
     * 返回参数：ResponseVo<String>
     */
    public static final String REFUND_ORDER = "com.cloud.cang.pay.ws.VendstopService.refundOrder";

    /**
     * 开门成功
     * 请求参数： appSessionId
     * 返回参数：ResponseVo<String>
     */
    public static final String OPEN_SUCCESS = "com.cloud.cang.pay.ws.VendstopService.openSuccess";

}
