package com.cloud.cang.wap.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.wap.ac.vo.CouponDomain;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;

/**
 * 用户持有劵信息表(AC_COUPON_USER)
 **/
public interface CouponUserDao extends GenericDao<CouponUser, String> {


    Map<String, Object> statisticalCouponNumByMemberId(String memberId);

    /***
     * 分页查询我的卡券
     * @param map 查询参数
     */
    Page<CouponDomain> selectCouponListByPage(Map<String, Object> map);
}