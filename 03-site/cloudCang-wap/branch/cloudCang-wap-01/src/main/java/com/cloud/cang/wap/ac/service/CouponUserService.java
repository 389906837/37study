package com.cloud.cang.wap.ac.service;

import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.ac.vo.CouponDomain;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;

import java.util.Map;

public interface CouponUserService extends GenericService<CouponUser, String> {

    Map<String, Object> statisticalCouponNumByMemberId(String memberId);
    /***
     * 分页查询我的卡券
     * @param page 分页参数
     * @param map 查询参数
     */
    Page<CouponDomain> selectCouponListByPage(Page<CouponDomain> page, Map<String, Object> map);
}