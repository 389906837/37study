package com.cloud.cang.jy;

/**
 * 订单优惠计算服务接口名称
 *
 * @version 1.0
 * @Author: zengzexiong
 * @Date: 2017年12月28日10:18:50
 */
public class OrderDiscountDefine {

    /**
     * 计算优惠服务
     * 请求参数：{@link OrderDiscountInfoDto}
     * 返回参数：ResponseVo<OrderDiscountInfoResult>
     */
    public static final String CREATE_ORDER_DISCOUNT = "com.cloud.cang.bzc.ws.OrderDiscountService.orderDiscount";


    /**
     * 创建订单服务
     * 请求参数：{@link OrderCommDto}
     * 返回参数：{@link  com.cloud.cang.common.ResponseVo<GeneratingOrderResults>}
     */
    public static final String CREATE_ORDER = "com.cloud.cang.bzc.ws.OrderService.createOrder";

    /**
     * 创建异常订单服务
     * 请求参数：{@link ExceptionOrderDto}
     * 返回参数：ResponseVo
     */
    public static final String CREATE_EXCEPTION_ORDER = "com.cloud.cang.bzc.ws.OrderService.createExceptionOrder";
}
