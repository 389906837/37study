package com.cloud.cang.act.ac.dao;

import com.cloud.cang.act.CouponQueryDto;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponUser;

import java.util.List;
import java.util.Map;


/** 用户持有劵信息表(AC_COUPON_USER) **/
public interface CouponUserDao extends GenericDao<CouponUser, String> {

    /**
     * 根据用户查询用户持有未使用的券
     * @param
     * @return
     */
    List<CouponUser> selectCouponUserByCouponQueryDto(CouponQueryDto couponQueryDto);
    /**
     * 修改用券信息
     * @param map
     * @return
     */
    int updateCouponUserByMap(Map map);
    /**
     * 统计券码每月参与一次
     * @param couponCode
     * @return
     */
    Integer countCouponUserMouthJoinByCouponCode(String couponCode);

    /**
     * 统计券码只能兑换一次
     * @param couponCode
     * @return
     */
    Integer countCouponUserOnceJoinByCouponCode(String couponCode);
    /**
     * 统计用户兑换券码每月只能兑换一次
     * @param
     * @return
     */
    Integer countCouponUserMouthJoinByMemberId(Map<String,String> map);

    /**
     * 统计券码只能兑换一次
     * @param
     * @return
     */
    Integer countCouponUserOnceJoinByMmeberId(Map<String,String> map);
}