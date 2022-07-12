package com.cloud.cang.mgr.hy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.hy.dao.WechatFreeDataDao;
import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.mgr.hy.service.WechatFreeDataService;

@Service
public class WechatFreeDataServiceImpl extends GenericServiceImpl<WechatFreeData, String> implements
        WechatFreeDataService {

    @Autowired
    WechatFreeDataDao wechatFreeDataDao;
    @Autowired
    MerchantInfoService merchantInfoService;

    @Override
    public GenericDao<WechatFreeData, String> getDao() {
        return wechatFreeDataDao;
    }


    /**
     * 查询免密签约数据
     *
     * @param contractCode  系统签约唯一标识
     * @param smerchantCode 商户编号
     * @return
     */
    @Override
    public WechatFreeData selectByContractCode(String contractCode, String smerchantCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractCode", contractCode);
        map.put("smerchantCode", smerchantCode);
        return wechatFreeDataDao.selectByContractCode(map);
    }

    /**
     * 保存免密数据
     *
     * @param freeData 免密参数
     */
    @Override
    public WechatFreeData saveOrUpdate(WechatFreeData freeData) {
        if (StringUtil.isBlank(freeData.getId())) {//保存
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(freeData.getSmerchantCode());
            freeData.setSmerchantId(merchantInfo.getId());
            freeData.setTaddTime(new Date());
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.insert(freeData);
        } else {//修改
            freeData.setTupdateTime(new Date());
            wechatFreeDataDao.updateByIdSelective(freeData);
        }
        return freeData;
    }

}