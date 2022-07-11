package com.cloud.cang.wap.hy.service;

import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.generic.GenericService;

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
     * 获取支付宝免密数据
     * @param externalAgreementNo 商户签约唯一标识
     * @return
     */
    FreeData selectByExternalAgreementNo(String externalAgreementNo);
}