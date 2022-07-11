package com.cloud.cang.wap.hy.service;

import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface ThirdAuthoriseService extends GenericService<ThirdAuthorise, String> {

    /***
     * 获取第三方授权登录信息
     * @param openId 第三方openId
     * @param itype 第三方类型 10:微信 20:支付宝
     * @return
     */
    List<ThirdAuthorise> selectThirdAuthoriseByOpenId(String openId, Integer itype);

    /***
     * 获取第三方授权登录信息
     * @param memberId 会员ID
     * @param itype 第三方类型 10:微信 20:支付宝
     * @return
     */
    ThirdAuthorise selectThirdAuthoriseByMemberId(String memberId, Integer itype);
}