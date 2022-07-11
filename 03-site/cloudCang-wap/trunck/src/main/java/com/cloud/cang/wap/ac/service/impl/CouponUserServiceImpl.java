package com.cloud.cang.wap.ac.service.impl;

import java.util.List;
import java.util.Map;

import com.cloud.cang.wap.ac.vo.CouponDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.wap.ac.dao.CouponUserDao;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.wap.ac.service.CouponUserService;

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
    public Map<String, Object> statisticalCouponNumByMemberId(String memberId) {
        return couponUserDao.statisticalCouponNumByMemberId(memberId);
    }
    /***
     * 分页查询我的卡券
     * @param page 分页参数
     * @param map 查询参数
     */
    @Override
    public  Page<CouponDomain> selectCouponListByPage(Page<CouponDomain> page, Map<String, Object> map){
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return couponUserDao.selectCouponListByPage(map);
    }

}