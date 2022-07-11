package com.cloud.cang.act.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.act.ac.dao.DiscountRecordDao;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.act.ac.service.DiscountRecordService;

@Service
public class DiscountRecordServiceImpl extends GenericServiceImpl<DiscountRecord, String> implements
        DiscountRecordService {

    @Autowired
    DiscountRecordDao discountRecordDao;


    @Override
    public GenericDao<DiscountRecord, String> getDao() {
        return discountRecordDao;
    }


    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param memberId 会员ID
     * @param couponId 券ID
     * @return DiscountRecord
     */
    @Override
    public DiscountRecord selectByMmberIdAndCouponId(String memberId, String couponId) {
        Map<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("couponId", couponId);
        return discountRecordDao.selectByMmberIdAndCouponId(map);
    }

    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param map 查询条件
     * @return Integer
     */
    @Override
    public Integer selectCount(Map map){
        return discountRecordDao.selectCount(map);
    }


}