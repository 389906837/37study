package com.cloud.cang.wap.hy.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.ThirdAuthorise;

import java.util.List;
import java.util.Map;

/** 第三方授权信息表(HY_THIRD_AUTHORISE) **/
public interface ThirdAuthoriseDao extends GenericDao<ThirdAuthorise, String> {

    /**
     * 获取第三方授权登录信息
     * @param map openId 第三方openId itype 第三方类型 10:微信 20:支付宝
     * @return
     */
    List<ThirdAuthorise> selectThirdAuthoriseByOpenId(Map<String, Object> map);
    /***
     * 获取第三方授权登录信息
     * @param map memberId 会员ID itype 第三方类型 10:微信 20:支付宝
     * @return
     */
    ThirdAuthorise selectThirdAuthoriseByMemberId(Map<String, Object> map);
}