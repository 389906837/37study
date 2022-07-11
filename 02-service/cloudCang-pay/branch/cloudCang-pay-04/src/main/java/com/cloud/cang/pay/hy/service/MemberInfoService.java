package com.cloud.cang.pay.hy.service;

import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.notify.SignNotifyData;
import com.cloud.cang.pay.wechat.notify.UnsignNotifyData;
import com.cloud.cang.pay.wechat.notify.WechatPointsNotifyData;

import java.util.Map;

public interface MemberInfoService extends GenericService<MemberInfo, String> {

    /**
     * 处理支付宝签约处理
     *
     * @param merchantCode 商户编号
     * @param map          返回参数 详见文档
     * @param conf         商户配置信息
     * @throws Exception
     */
    boolean dealwithAlipaySign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception;

    /**
     * 处理微信签约处理
     *
     * @param signNotifyData 签约参数
     * @param conf           商户配置数据
     * @param sip            操作IP
     * @throws Exception
     */
    boolean dealwithWechatSign(SignNotifyData signNotifyData, MerchantConf conf, String sip) throws Exception;

    /***
     * 支付宝免密解约处理
     * @param merchantCode 商户编号
     * @param map 返回参数 详见文档
     * @param conf 商户配置信息
     * @throws Exception
     */
    boolean dealwithAlipayUnsign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception;

    /**
     * 支付宝交易订单关闭处理
     *
     * @param merchantCode 商户编号
     * @param map          返回参数 详见文档
     * @param conf         商户配置信息
     * @throws Exception
     */
    boolean dealwithAlipayClosePayment(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception;

    /***
     * 微信免密协议解约处理
     * @param sip IP
     * @param resMap 返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    boolean dealwithWechatUnsign(String sip, Map<String, Object> resMap, MerchantConf conf) throws Exception;

    /***
     * 微信免密协议解约处理
     * @param sip IP
     * @param unsignNotifyData 返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    boolean dealwithWechatUnsign(String sip, UnsignNotifyData unsignNotifyData, MerchantConf conf) throws Exception;

    /***
     * 微信支付分开启服务处理
     * @param sip IP
     * @param openNotifyData  返回参数
     * @param conf 商户配置信息
     * @throws Exception
     */
    boolean dealwithWechatOpenService(String sip, WechatPointsNotifyData openNotifyData, MerchantConf conf) throws Exception;

}