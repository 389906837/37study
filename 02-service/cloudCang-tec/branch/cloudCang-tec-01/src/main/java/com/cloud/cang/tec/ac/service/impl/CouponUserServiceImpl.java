package com.cloud.cang.tec.ac.service.impl;

import java.util.List;

import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.tec.ac.vo.CouponUserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.ac.dao.CouponUserDao;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.tec.ac.service.CouponUserService;

@Service
public class CouponUserServiceImpl extends GenericServiceImpl<CouponUser, String> implements
        CouponUserService {

    @Autowired
    CouponUserDao couponUserDao;


    @Override
    public GenericDao<CouponUser, String> getDao() {
        return couponUserDao;
    }

    /**
     * 优惠券过期短信提醒
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<CouponUserVo> selectExpiredCouponWarn(String merchantId, String expireDate) {
        return couponUserDao.selectExpiredCouponWarn(expireDate, merchantId);
    }

    /**
     * 优惠券过期状态变更
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<CouponUser> selectExpiredCoupon(String merchantId) {
        return couponUserDao.selectExpiredCoupon(merchantId);

    }

}