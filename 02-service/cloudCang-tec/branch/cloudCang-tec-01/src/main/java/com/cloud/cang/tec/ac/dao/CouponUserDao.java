package com.cloud.cang.tec.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.tec.ac.vo.CouponUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户持有劵信息表(AC_COUPON_USER)
 **/
public interface CouponUserDao extends GenericDao<CouponUser, String> {
    /**
     * 优惠券过期短信提醒
     *
     * @param merchantId 商户ID
     * @param expireDate 提前提醒时间
     */
    List<CouponUserVo> selectExpiredCouponWarn(@Param("expireDate") String expireDate, @Param("merchantId") String merchantId);

    /**
     * 优惠券过期状态变更
     *
     * @param merchantId 商户ID
     */
    List<CouponUser> selectExpiredCoupon(String merchantId);


}