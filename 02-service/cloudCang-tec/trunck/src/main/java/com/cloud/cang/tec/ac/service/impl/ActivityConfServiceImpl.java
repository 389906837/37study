package com.cloud.cang.tec.ac.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.tec.ac.vo.ActivityConfVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.tec.ac.dao.ActivityConfDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.tec.ac.service.ActivityConfService;

@Service
public class ActivityConfServiceImpl extends GenericServiceImpl<ActivityConf, String> implements
        ActivityConfService {

    @Autowired
    ActivityConfDao activityConfDao;


    @Override
    public GenericDao<ActivityConf, String> getDao() {
        return activityConfDao;
    }

    /**
     * 关闭商户过期且未关闭的活动
     *
     * @param merchantId 商户ID
     */
    @Override
    public int upActivityStatus(String merchantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("smerchantId", merchantId);
        map.put("newIsAvailable", -1);
        map.put("oldIsAvailable", 1);
        return activityConfDao.upActivityStatus(map);
    }

    /**
     * 查询商户即将过期(数据字典配置时间)的场景活动
     *
     * @param merchantId 商户ID
     */
    @Override
    public ActivityConfVo selectExpireSceneActivity(String merchantId) {
        Map<String, String> map = new HashMap<>();
        map.put("smerchantId", merchantId);
        String day = BizParaUtil.get("scene_activity_warn");
        if (StringUtils.isBlank(day)) {
            day = "3";
        }
        map.put("sceneActivityWarn", day);
        return activityConfDao.selectExpireSceneActivity(map);
    }

    /**
     * 查询商户过期且未关闭的活动
     *
     * @param merchantId 商户ID
     */
    @Override
    public List<ActivityConf> selectExpireActivity(String merchantId) {
        return activityConfDao.selectExpireActivity(merchantId);
    }
}