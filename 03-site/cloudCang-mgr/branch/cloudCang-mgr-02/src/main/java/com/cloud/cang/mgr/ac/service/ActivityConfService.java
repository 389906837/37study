package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.ac.domain.ActivityConfDomain;
import com.cloud.cang.mgr.ac.vo.ActivityConfVo;
import com.cloud.cang.mgr.ac.vo.PromotionsVo;
import com.cloud.cang.mgr.ac.vo.ScenesVo;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ActivityConfService extends GenericService<ActivityConf, String> {

    /**
     * @Author: zhouhong
     * @Description:分页获取活动列表数据
     * @param: page 分页参数
     * @param: activityConfVo 活动查询参数
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.ac.domain.ActivityConfDomain>
     */
    Page<ActivityConfDomain> selectPageByDomainWhere(Page<ActivityConfDomain> page, ActivityConfVo activityConfVo);

    /**
     * @Author: zhouhong
     * @Description: 判断活动是否存在
     * @param: paramMap
     * @Date: 2018/2/10 16:27
     */
    List<ActivityConf> isActivityExistByMap(Map<String, Object> paramMap);

    /**
     * @Author: zhouhong
     * @Description: 保存或修改场景活动
     * @param: scenesVo
     * @Date: 2018/2/10 17:38
     * @return com.cloud.cang.model.ac.ActivityConf
     */
    ActivityConf saveOrUpdateScenes(ScenesVo scenesVo) throws Exception;

    /**
     * @Author: zhouhong
     * @Description: 删除活动
     * @param: checkboxId 活动Id集合
     * @Date: 2018/2/10 19:58
     */
    void delete(String[] checkboxId) throws ServiceException;

    /**
     * @Author: zhouhong
     * @Description: 获取商户活动信息
     * @param: actId 活动ID
     * @Date: 2018/2/10 20:15
     * @return com.cloud.cang.model.ac.ActivityConf
     */
    ActivityConf selectActivityByIdAndMerchantId(String actId);

    /**
     * 启用或关闭活动
     * @param activityConf 活动配置信息
     * @param type 操作类型 1 启用 2 禁用
     */
    void enabledOrClosed(ActivityConf activityConf,int type);

    /**
     * @Author: zhouhong
     * @Description: 保存或修改促销活动
     * @param: promotionsVo
     * @Date: 2018/2/10 17:38
     * @return com.cloud.cang.model.ac.ActivityConf
     */
    ActivityConf saveOrUpdatePromotionsVo(PromotionsVo promotionsVo) throws Exception;
    /**
     * 查询活动信息
     * @param map 查询参数
     * @return
     */
    ActivityConf selectByMap(Map<String, Object> map);
}