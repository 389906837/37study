package com.cloud.cang.pay.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.om.vo.FreePaymentVo;
import com.cloud.cang.pay.wechat.notify.FreePayNotifyData;
import com.cloud.cang.pay.wechat.notify.PayNotifyData;

import java.util.Map;

public interface OrderRecordService extends GenericService<OrderRecord, String> {


    /***
     * 处理支付宝手动支付回调通知
     * @param conf 商户配置信息 操作商户
     * @param map 支付宝回调参数集合
     */
    boolean dealwithAlipayPaymentNotify(MerchantConf conf, Map<String, String> map);

    /**
     * 获取订单信息
     * @param orderCode 订单编号
     */
    OrderRecord selectByOrderCode(String orderCode);

    /**
     * 创建支付宝免密支付
     * @param freePaymentDto 支付参数
     */
    ResponseVo<FreePaymentResult> createAlipayFreePay(FreePaymentDto freePaymentDto);

    /**
     * 创建微信免密支付
     * @param freePaymentDto 支付参数
     */
    ResponseVo<FreePaymentResult> createWechatFreePay(FreePaymentDto freePaymentDto);

    /**
     * 新增支付宝免密支付并且签约
     * @param freePaymentDto 支付签约参数
     */
    ResponseVo<String> createAlipayFreePayAndSign(FreePaymentDto freePaymentDto);

    /**
     * 新增申请支付订单数据
     * @param orderRecord 订单数据
     * @param remark 付款备注
     * @throws Exception
     */
    PayApply insertPayApply(OrderRecord orderRecord, String remark) throws Exception;

    /**
     * 查询免密支付订单
     * @param paymentDto 查询参数
     */
    ResponseVo<QueryPaymentResult> queryAlipayFreePay(PaymentDto paymentDto) throws Exception;

    /**
     * 撤销免密支付订单
     * @param paymentDto 查询参数
     * @throws Exception
     */
    ResponseVo<String> cancelAlipayFreePay(PaymentDto paymentDto) throws Exception;

    /**
     * 关闭免密支付订单
     * @param paymentDto 查询参数
     * @throws Exception
     */
    ResponseVo<String> closeAlipayFreePay(PaymentDto paymentDto) throws Exception;

    /**
     * 处理支付宝免密支付 回调
     * @param conf 商户配置信息 操作商户
     * @param map 回调参数
     */
    boolean dealwithAlipayFreePaymentNotify(MerchantConf conf, Map<String, String> map) throws Exception;

    /**
     * 处理微信免密支付 回调
     * @param conf 商户配置信息 操作商户
     * @param freePayNotifyData 回调参数
     */
    boolean dealwithWechatFreePaymentNotify(MerchantConf conf, FreePayNotifyData freePayNotifyData) throws Exception;
    /**
     * 修改订单数据
     *
     * @param outTradePayNo
     * @param orderRecord 原始订单数据
     * @param freePaymentResult 支付返回结果
     * @return
     */
    boolean freePaySuccess(String outTradePayNo, OrderRecord orderRecord, FreePaymentResult freePaymentResult);

    /***
     * 免密订单支付失败处理
     * @param outTradePayNo
     * @param code 支付宝处理结果code
     * @param subCode 业务code
     * @param subMsg 业务处理信息
     * @param orderRecord 原始订单数据
     * @param freePaymentResult 支付返回结果   @return
     */
    FreePaymentResult freePayFail(String outTradePayNo, String code, String subCode, String subMsg, OrderRecord orderRecord, FreePaymentResult freePaymentResult);

    /***
     * 组装免密支付请求参数
     * @param orderPay
     * @param orderRecord 订单数据
     * @param freePaymentDto 请求数据
     * @param freeData 支付宝免密数据
     * @param type 类型 1支付 2 支付且签约
     */
    FreePaymentVo assemblyFreePaymentRequest(OrderPay orderPay, OrderRecord orderRecord, FreePaymentDto freePaymentDto, FreeData freeData, int type) throws Exception;

    /**
     * 处理免密支付并签约回调
     * @param conf 商户配置信息
     * @param map 支付宝返回参数
     * @return
     */
    boolean dealwithAlipayFreePaymentAndSignNotify(MerchantConf conf, Map<String, String> map) throws Exception;
    /**
     * 更新订单状态
     * @param pmap 参数集合
     * @return
     */
    Integer updateStatusByOrderId(Map<String, Object> pmap);

    /**
     * 处理微信手动支付回调通知
     * @param conf 商户配置信息 操作商户
     * @param payNotifyData 微信回调参数集合
     * @throws Exception
     */
    boolean dealwithWechatPaymentNotify(MerchantConf conf, PayNotifyData payNotifyData) throws Exception;

    /**
     * 微信免密订单支付查询
     * @param paymentDto 查询参数
     */
    ResponseVo<QueryWechatFreePayResult> queryWechatFreePay(PaymentDto paymentDto) throws Exception;
}