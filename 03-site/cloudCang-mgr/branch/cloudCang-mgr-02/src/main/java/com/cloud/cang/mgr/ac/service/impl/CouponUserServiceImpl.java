package com.cloud.cang.mgr.ac.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.CouponUserDao;
import com.cloud.cang.mgr.ac.domain.CouponUserDomain;
import com.cloud.cang.mgr.ac.service.CouponUserService;
import com.cloud.cang.mgr.ac.vo.CouponUserVo;
import com.cloud.cang.model.ac.CouponUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CouponUserServiceImpl extends GenericServiceImpl<CouponUser, String> implements
        CouponUserService {

    @Autowired
    CouponUserDao couponUserDao;


    @Override
    public GenericDao<CouponUser, String> getDao() {
        return couponUserDao;
    }


    @Override
    public Page<CouponUserDomain> queryDataCouponUser(Page<CouponUserDomain> page, CouponUserVo couponUserVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return couponUserDao.queryDataCouponUser(couponUserVo);
    }

    /**
     * 用户持有券列表页脚总统计
     *
     * @param couponUserVo
     * @return ResponseVo
     */
    @Override
    public HashMap queryTotalData(CouponUserVo couponUserVo) {
        return couponUserDao.queryTotalData(couponUserVo);
    }
}