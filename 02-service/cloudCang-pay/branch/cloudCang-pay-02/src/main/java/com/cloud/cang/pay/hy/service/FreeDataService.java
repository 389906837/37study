package com.cloud.cang.pay.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.pay.QuerySignDto;
import com.cloud.cang.pay.SignDto;
import com.cloud.cang.pay.SignResult;
import com.cloud.cang.pay.UnsignDto;

public interface FreeDataService extends GenericService<FreeData, String> {

    /**
     * 获取支付宝免密数据
     * @param memberId 会员Id
     * @param merchantCode 商户编号
     * @return
     */
    FreeData selectByMemberId(String memberId, String merchantCode);

    /**
     * 新增或修改 支付宝免密数据
     * @param freeData 付宝免密数据
     * @return
     */
    FreeData saveOrUpdate(FreeData freeData);
    /**
     * 获取支付宝免密数据
     * @param externalAgreementNo 商户签约唯一标识
     * @param merchantCode 商户编号
     * @return
     */
    FreeData selectByExternalAgreementNo(String externalAgreementNo, String merchantCode);

    /**
     * 支付宝免密签约
     * @param signDto 签约参数
     * @return
     * @throws Exception
     */
    ResponseVo<String> alipaySign(SignDto signDto) throws Exception;

    /***
     * 支付宝免密解约
     * @param unsignDto 解约参数
     * @return
     * @throws Exception
     */
    ResponseVo<String> alipayUnsign(UnsignDto unsignDto) throws Exception;
    /***
     * 支付宝免密协议查询
     * @param querySignDto 查询参数
     * @return
     * @throws Exception
     */
    ResponseVo<SignResult> alipayQuerySign(QuerySignDto querySignDto) throws Exception;
    /**
     * 获取支付宝免密数据
     * @param externalAgreementNo 商户签约唯一标识
     * @return
     */
    FreeData selectByExternalAgreementNo(String externalAgreementNo);
    /**
     * 获取支付宝免密数据
     * @param memberId 会员Id
     * @return
     */
    FreeData selectByMemberId(String memberId);
}