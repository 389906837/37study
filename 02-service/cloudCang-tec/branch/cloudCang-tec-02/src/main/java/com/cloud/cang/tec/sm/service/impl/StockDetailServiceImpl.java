package com.cloud.cang.tec.sm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.tec.sm.vo.ErrorCommodityVo;
import com.cloud.cang.tec.sm.vo.StockDetailVo;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.tec.sm.dao.StockDetailDao;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.tec.sm.service.StockDetailService;

import javax.annotation.Resource;

@Service
public class StockDetailServiceImpl extends GenericServiceImpl<StockDetail, String> implements
        StockDetailService {

    @Autowired
    private StockDetailDao stockDetailDao;


    @Override
    public GenericDao<StockDetail, String> getDao() {
        return stockDetailDao;
    }

    /**
     * 商品过期预警 根据商户ID
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<StockDetailVo> selectExpired(String smerchantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("smerchantId", smerchantId);
        map.put("istatus", 10);
        String temp = BizParaUtil.get("commodity_expire_warn");
        if (StringUtil.isBlank(temp)) {
            temp = "3";
        }
        map.put("commodityExpireWarn", temp);
        return stockDetailDao.selectExpired(map);
    }

    /**
     * 今日过期商品通知 根据商户ID
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<StockDetailVo> selectComExpiredPrompt(String merchantId) {
        return stockDetailDao.selectComExpiredPrompt(merchantId);
    }

    /**
     * 异常商品预警 根据商户ID
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<ErrorCommodityVo> selectErrorCommodity(String merchantId) {
        return stockDetailDao.selectErrorCommodity(merchantId);
    }

    /**
     * 异常商品预警 根据商户ID
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<StockDetail> selectExpiredCommodity(String merchantId) {
        return stockDetailDao.selectExpiredCommodity(merchantId);
    }
}