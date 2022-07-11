package com.cloud.cang.tec.sm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.tec.sm.vo.StandardDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.tec.sm.dao.StandardDetailDao;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.tec.sm.service.StandardDetailService;

@Service
public class StandardDetailServiceImpl extends GenericServiceImpl<StandardDetail, String> implements
        StandardDetailService {

    @Autowired
    StandardDetailDao standardDetailDao;


    @Override
    public GenericDao<StandardDetail, String> getDao() {
        return standardDetailDao;
    }


    @Override
    public List<StandardDetailVo> selectStockWarningByMerchant(String merchantId) {

        Map<String, Object> map = new HashMap<>();
        map.put("smerchantId", merchantId);
        /*map.put("stockDetailStatus", 10);*/
        map.put("standardStockStatus", 10);
        return standardDetailDao.selectStockWarningByMerchant(map);
    }

}