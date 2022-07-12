package com.cloud.cang.mgr.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.ac.domain.ActivityConfDomain;
import com.cloud.cang.mgr.ac.domain.ActivityConfValidDomain;
import com.cloud.cang.mgr.ac.vo.ActivityConfVo;
import com.cloud.cang.model.ac.ActivityConf;
import com.github.pagehelper.Page;

/**
 * 活动配置表(AC_ACTIVITY_CONF)
 **/
public interface ActivityConfDao extends GenericDao<ActivityConf, String> {

    /**
     * @Author: zhouhong
     * @Description:分页获取活动列表数据
     * @param: activityConfVo 活动查询参数
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.ac.domain.ActivityConfDomain>
     */
    Page<ActivityConfDomain> selectPageByDomainWhere(ActivityConfVo activityConfVo);

    /**
     * @Author: zhouhong
     * @Description: 判断活动是否存在
     * @param: paramMap
     * @Date: 2018/2/10 16:27
     */
    List<ActivityConf> isActivityExistByMap(Map<String, Object> paramMap);

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: 获取商户活动信息
     * @param: actId 活动ID
     * @Date: 2018/2/10 20:15
     */
    ActivityConf selectActivityByIdAndMerchantId(Map<String, Object> paramMap);

    /**
     * 查询活动信息
     *
     * @param map 查询参数
     * @return
     */
    ActivityConf selectByMap(Map<String, Object> map);


    /**
     * 根据商户ID查询所有有效活动
     * @param merchantId
     */
    List<ActivityConfValidDomain> selectAllValidByMerchantId(String merchantId);

}