package com.cloud.cang.bzc.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


public interface OrderRecordService extends GenericService<com.cloud.cang.model.om.OrderRecord, String> {

    /**
     * 订单优惠服务抽象类
     *
     * @param orderDisDto
     * @return
     */
     ResponseVo<OrderDiscountInfoResult> calculateOrderDiscount(OrderDiscountInfoDto orderDisDto) throws Exception;


    /**
     * 生成订单
     *
     * @param orderCommDto
     * @throws Exception
     */
    /*ResponseVo<List<CreatOrderResult>>*/ Map generateOrder(OrderCommDto orderCommDto) throws Exception;

    /**
     * 生成异常订单
     *
     * @param exceptionOrderDto
     * @throws Exception
     */
    ResponseVo generateExceptionOrder(ExceptionOrderDto exceptionOrderDto) throws Exception;


    /**
     * 调用更新优惠券服务
     *
     * @param
     * @throws Exception
     */
    void upCoupon(OrderDto orderDto, ResponseVo<List<CreatOrderResult>> orderVo);

    /***
     * 创建实时订单服务
     * @param orderDto 订单参数
     * @return
     */
    ResponseVo<RealTimeOrderResult> createRealTimeOrder(RealTimeOrderDto orderDto) throws Exception;

    /**
     * 创建支付订单
     *
     * @param merchantCode 商户编号
     * @param orderRecords 订单集合
     */
    void createPayOrder(final String merchantCode, final List<CreatOrderResult> orderRecords);

    /**
     * 创建补货实时订单服务
     *
     * @param orderDto 订单参数
     * @return
     */
    ResponseVo<ReplenishRealTimeOrderResult> createReplenishRealTimeOrder(ReplenishRealTimeOrderDto orderDto);

    /**
     * 根据商品差创建实时订单服务
     *
     * @param realTimeCommodityDiffOrderDto
     * @return
     */
    ResponseVo<RealTimeOrderResult> createRealTimeOrderByCommodityDiff(RealTimeCommodityDiffOrderDto realTimeCommodityDiffOrderDto) throws Exception;

    /**
     * 根据商品差创建实时补货订单服务
     *
     * @param orderDto
     * @return
     */
    ResponseVo<ReplenishRealTimeOrderResult> createReplenishRealTimeOrderByCommodityDiff(RealTimeCommodityDiffOrderDto orderDto);

}