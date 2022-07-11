package com.cloud.cang.pay.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.pay.*;

public interface WechatFreeDataService extends GenericService<WechatFreeData, String> {

    /**
     * 支付宝免密签约
     *
     * @param signDto 签约参数
     * @return
     * @throws Exception
     */
    ResponseVo<String> wechatSign(SignDto signDto) throws Exception;

    /**
     * 微信支付分开启服务
     *
     * @param signDto 签约参数
     * @return
     * @throws Exception
     */
    ResponseVo<String> wechatOpenService(SignDto signDto) throws Exception;

    /**
     * 获取微信免密数据
     *
     * @param smemberId     会员信息
     * @param smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByMemberId(String smemberId, String smerchantCode);

    /**
     * 保存免密数据
     *
     * @param freeData 免密参数
     */
    WechatFreeData saveOrUpdate(WechatFreeData freeData);


    /**
     * 查询免密签约数据
     *
     * @param contractCode  系统签约唯一标识
     * @param smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByContractCode(String contractCode, String smerchantCode);

    /**
     * 查询微信免密支付
     *
     * @param querySignDto 查询参数
     * @return
     */
    ResponseVo<WechatSignResult> wechatQuerySign(QuerySignDto querySignDto) throws Exception;

    /**
     * 微信免密解决
     *
     * @param unsignDto 解约参数
     * @throws Exception
     */
    ResponseVo<String> wechatUnsign(UnsignDto unsignDto) throws Exception;

    /**
     * 查询免密签约数据
     *
     * @param contractCode 系统签约唯一标识
     * @return
     */
    WechatFreeData selectByContractCode(String contractCode);

    /**
     * 查询免密签约数据
     *
     * @param outReuestNo 调用开启服务接口提交的商户请求唯一标识
     * @return
     */
    WechatFreeData selectByOutReuestNo(String outReuestNo);

    /**
     * 获取微信免密数据
     *
     * @param smemberId 会员信息
     * @return
     */
    WechatFreeData selectByMemberId(String smemberId);

    /**
     * 获取微信免密数据
     *
     * @param openId 微信用户在商户对应appid下的唯一标识。
     * @param mchId  调用开启服务接口提交的商户号
     * @return
     */
    WechatFreeData selectByOpenIdAndMchId(String openId, String mchId);

    /**
     * 获取微信免密数据
     *
     * @param smemberId 会员信息
     * @return
     */
    WechatFreeData selectByMemberIdAndWithholdType(String smemberId, String smerchantCode, Integer withholdType);

    /**
     * 微信支付分查询用户是否可使用
     *
     * @param queryUserAvaiDto 签约参数
     * @return
     */
    ResponseVo<QueryUserAvaiResult> queryUserAvailability(QueryUserAvaiDto queryUserAvaiDto) throws Exception;
}