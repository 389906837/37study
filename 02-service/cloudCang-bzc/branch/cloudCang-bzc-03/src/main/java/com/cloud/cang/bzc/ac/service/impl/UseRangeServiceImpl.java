package com.cloud.cang.bzc.ac.service.impl;

import com.cloud.cang.bzc.ac.dao.UseRangeDao;
import com.cloud.cang.bzc.ac.service.UseRangeService;
import com.cloud.cang.bzc.om.vo.ActivityUseRangeVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.UseRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseRangeServiceImpl extends GenericServiceImpl<UseRange, String> implements
        UseRangeService {

    @Autowired
    UseRangeDao useRangeDao;


    @Override
    public GenericDao<UseRange, String> getDao() {
        return useRangeDao;
    }


    @Override
    public ActivityUseRangeVo selectRangeBySacCode(String scode) {
        return useRangeDao.selectRangeBySacCode(scode);
    }

    /***
     *根基商户Id查询启用的活动配置详情表
     *@param merchantId
     @return UseRange
     */
    @Override
    public UseRange selectBySmerchantId(String merchantId,Integer iscenesType) {
        return useRangeDao.selectBySmerchantId(merchantId,iscenesType) ;
    }
}