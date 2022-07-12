package com.cloud.cang.mgr.ac.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.ac.domain.ActivityConfDomain;
import com.cloud.cang.mgr.ac.service.DiscountDetailService;
import com.cloud.cang.mgr.ac.service.UseRangeService;
import com.cloud.cang.mgr.ac.vo.ActivityConfVo;
import com.cloud.cang.mgr.ac.vo.PromotionsVo;
import com.cloud.cang.mgr.ac.vo.ScenesVo;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.ac.DiscountDetail;
import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.ac.dao.ActivityConfDao;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.mgr.ac.service.ActivityConfService;

@Service
public class ActivityConfServiceImpl extends GenericServiceImpl<ActivityConf, String> implements
        ActivityConfService {

    @Autowired
    ActivityConfDao activityConfDao;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private UseRangeService useRangeService;
    @Autowired
    private DiscountDetailService discountDetailService;

    @Override
    public GenericDao<ActivityConf, String> getDao() {
        return activityConfDao;
    }

    /**
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.ac.domain.ActivityConfDomain>
     * @Author: zhouhong
     * @Description:分页获取活动列表数据
     * @param: page 分页参数
     * @param: activityConfVo 活动查询参数
     */
    @Override
    public Page<ActivityConfDomain> selectPageByDomainWhere(Page<ActivityConfDomain> page, ActivityConfVo activityConfVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return activityConfDao.selectPageByDomainWhere(activityConfVo);
    }

    /**
     * @Author: zhouhong
     * @Description: 判断活动是否存在
     * @param: paramMap
     * @Date: 2018/2/10 16:27
     */
    @Override
    public List<ActivityConf> isActivityExistByMap(Map<String, Object> paramMap) {
        return activityConfDao.isActivityExistByMap(paramMap);
    }

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: 保存或修改场景活动
     * @param: scenesVo
     * @Date: 2018/2/10 17:38
     */
    @Override
    public ActivityConf saveOrUpdateScenes(ScenesVo scenesVo) throws Exception {

        ActivityConf activityConf = new ActivityConf();
        BeanUtils.copyProperties(activityConf, scenesVo);
        int irangeType = 10;//判断应用范围
        if (scenesVo.getIrangeDevice().intValue() == 1) {
            irangeType = 20;
        }
        /*if (scenesVo.getIrangeDevice().intValue() == 1 && scenesVo.getIrangeCommodity().intValue() == 1) {
			irangeType = 40;
		} else {
			if (scenesVo.getIrangeDevice().intValue() == 1) {
				irangeType = 20;
			} else if (scenesVo.getIrangeCommodity().intValue() == 1) {
				irangeType = 30;
			}
		}*/
        activityConf.setIrangeType(irangeType);
        StringBuffer deviceIdSb = new StringBuffer();
        StringBuffer deviceCodeSb = new StringBuffer();
        StringBuffer commodityIdSb = new StringBuffer();
        StringBuffer commodityCodeSb = new StringBuffer();
        if (irangeType != 10) {
            if (irangeType == 20 || irangeType == 40) {
                //验证设备商品有效
                List<DeviceInfo> tempList = null;
                DeviceInfo temp = null;
                for (String deviceId : scenesVo.getDeviceIds()) {
                    temp = new DeviceInfo();
                    temp.setId(deviceId);
                    temp.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                    tempList = deviceInfoService.selectByEntityWhere(temp);
                    if (null != tempList && tempList.size() > 0) {
                        deviceIdSb.append(tempList.get(0).getId() + ",");
                        deviceCodeSb.append(tempList.get(0).getScode() + ",");
                    }
                }
            }
			/*if (irangeType == 30 || irangeType == 40) {
				//验证设备商品有效
				List<CommodityInfo> tempList = null;
				CommodityInfo temp = null;
				for (String commodityId : scenesVo.getCommodityIds()) {
					temp = new CommodityInfo();
					temp.setId(commodityId);
					temp.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
					tempList = commodityInfoService.selectByEntityWhere(temp);
					if (null != tempList && tempList.size() > 0) {
						commodityIdSb.append(tempList.get(0).getId()+",");
						commodityCodeSb.append(tempList.get(0).getScode()+",");
					}
				}
			}*/
        }
        // 执行新增
        if (StringUtils.isBlank(activityConf.getId())) {
            activityConf.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            activityConf.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            activityConf.setIisAvailable(0);
            activityConf.setIjoinNum(0);
            activityConf.setIusedNum(0);
            activityConf.setIdelFlag(0);
            activityConf.setSconfCode(CoreUtils.newCode("ac_activity_conf"));
            activityConf.setTaddTime(new Date());
            activityConf.setTupdateTime(new Date());
            activityConf.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            activityConf.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            this.insert(activityConf);
            //同时新增 设备应用范围数据
            UseRange useRange = new UseRange();
            useRange.setIrangeType(activityConf.getIrangeType());
            useRange.setSacCode(activityConf.getSconfCode());
            useRange.setSacId(activityConf.getId());
            useRange.setTaddTime(new Date());
            useRange.setTupdateTime(new Date());
            useRange.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            useRange.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			/*String commodityCode = commodityCodeSb.toString();
			if (StringUtil.isNotBlank(commodityCode)) {
				commodityCode = commodityCode.substring(0,commodityCode.length()-1);
				useRange.setScommodityCode(commodityCode);
			}
			String commodityId = commodityIdSb.toString();
			if (StringUtil.isNotBlank(commodityId)) {
				commodityId = commodityId.substring(0,commodityId.length()-1);
				useRange.setScommodityId(commodityId);
			}*/

            String deviceCode = deviceCodeSb.toString();
            if (StringUtil.isNotBlank(deviceCode)) {
                deviceCode = deviceCode.substring(0, deviceCode.length() - 1);
                useRange.setSdeviceCode(deviceCode);
            }
            String deviceId = deviceIdSb.toString();
            if (StringUtil.isNotBlank(deviceId)) {
                deviceId = deviceId.substring(0, deviceId.length() - 1);
                useRange.setSdeviceId(deviceId);
            }
            useRangeService.insert(useRange);
        } else {// 执行修改
            ActivityConf conf = this.selectByPrimaryKey(activityConf.getId());
            if (null == conf || !conf.getSmerchantId().equals(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId())) {
                return null;
            }
            activityConf.setSconfCode(conf.getSconfCode());
            activityConf.setTupdateTime(new Date());
            activityConf.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            this.updateBySelective(activityConf);

            UseRange useRange = useRangeService.selectByActId(activityConf.getId());
            //更新
            UseRange useRangeU = new UseRange();
            useRangeU.setId(useRange.getId());
            useRangeU.setIrangeType(activityConf.getIrangeType());
            useRangeU.setTupdateTime(new Date());
            useRangeU.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            String commodityCode = commodityCodeSb.toString();
            if (StringUtil.isNotBlank(commodityCode)) {
                commodityCode = commodityCode.substring(0, commodityCode.length() - 1);
                useRangeU.setScommodityCode(commodityCode);
            }
            String commodityId = commodityIdSb.toString();
            if (StringUtil.isNotBlank(commodityId)) {
                commodityId = commodityId.substring(0, commodityId.length() - 1);
                useRangeU.setScommodityId(commodityId);
            }

            String deviceCode = deviceCodeSb.toString();
            if (StringUtil.isNotBlank(deviceCode)) {
                deviceCode = deviceCode.substring(0, deviceCode.length() - 1);
                useRangeU.setSdeviceCode(deviceCode);
            }
            String deviceId = deviceIdSb.toString();
            if (StringUtil.isNotBlank(deviceId)) {
                deviceId = deviceId.substring(0, deviceId.length() - 1);
                useRangeU.setSdeviceId(deviceId);
            }
            useRangeService.updateBySelective(useRangeU);
        }
        return activityConf;
    }

    /**
     * @Author: zhouhong
     * @Description: 删除活动
     * @param: checkboxId 活动Id集合
     * @Date: 2018/2/10 19:58
     */
    @Override
    public void delete(String[] checkboxId) throws ServiceException {
        if (null != checkboxId && checkboxId.length > 0) {
            ActivityConf activityConf = null;
            ActivityConf updateAc = null;
            for (String actId : checkboxId) {
                activityConf = this.selectActivityByIdAndMerchantId(actId);
                if (null == activityConf) {
                    throw new ServiceException(MessageSourceUtils.getMessageByKey("con.ac.ddneosbdcbd", null));
                }
                if (null == activityConf.getIisAvailable()) {
                    throw new ServiceException(MessageSourceUtils.getMessageByKey("con.ac.sedf", null));
                }
                if (activityConf.getIisAvailable().intValue() == 1) {
                    throw new ServiceException(MessageSourceUtils.getMessageByKey("con.ac.aiipdf", null));
                }
                // 执行逻辑删除
                updateAc = new ActivityConf();
                updateAc.setIdelFlag(1);
                updateAc.setId(actId);
                this.updateBySelective(updateAc);
            }
        }
    }

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: 获取商户活动信息
     * @param: actId 活动ID
     * @Date: 2018/2/10 20:15
     */
    @Override
    public ActivityConf selectActivityByIdAndMerchantId(String actId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actId", actId);
        paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        return this.activityConfDao.selectActivityByIdAndMerchantId(paramMap);
    }

    /**
     * 启用或关闭活动
     *
     * @param activityConf 活动配置信息
     * @param type         操作类型 1 启用 2 禁用
     */
    @Override
    public void enabledOrClosed(ActivityConf activityConf, int type) {
        ActivityConf updateAc = new ActivityConf();
        if (type == 1) {
            updateAc.setIisAvailable(1);
        } else if (type == 2) {
            updateAc.setIisAvailable(-1);
        }
        updateAc.setId(activityConf.getId());
        updateAc.setSdescription(activityConf.getSdescription());
        this.updateBySelective(updateAc);
    }

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: 保存或修改促销活动
     * @param: promotionsVo
     * @Date: 2018/2/10 17:38
     */
    @Override
    public ActivityConf saveOrUpdatePromotionsVo(PromotionsVo promotionsVo) throws Exception {

        ActivityConf activityConf = new ActivityConf();
        BeanUtils.copyProperties(activityConf, promotionsVo);
        int irangeType = 10;//判断应用范围
        if (promotionsVo.getIrangeDevice().intValue() == 1 && promotionsVo.getIrangeCommodity().intValue() == 1) {
            irangeType = 40;
        } else {
            if (promotionsVo.getIrangeDevice().intValue() == 1) {
                irangeType = 20;
            } else if (promotionsVo.getIrangeCommodity().intValue() == 1) {
                irangeType = 30;
            }
        }
        activityConf.setIrangeType(irangeType);
        StringBuffer deviceIdSb = new StringBuffer();
        StringBuffer deviceCodeSb = new StringBuffer();
        StringBuffer commodityIdSb = new StringBuffer();
        StringBuffer commodityCodeSb = new StringBuffer();
        if (irangeType != 10) {
            if (irangeType == 20 || irangeType == 40) {
                //验证设备商品有效
                List<DeviceInfo> tempList = null;
                DeviceInfo temp = null;
                for (String deviceId : promotionsVo.getDeviceIds()) {
                    temp = new DeviceInfo();
                    temp.setId(deviceId);
                    temp.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                    tempList = deviceInfoService.selectByEntityWhere(temp);
                    if (null != tempList && tempList.size() > 0) {
                        deviceIdSb.append(tempList.get(0).getId() + ",");
                        deviceCodeSb.append(tempList.get(0).getScode() + ",");
                    }
                }
            }
            if (irangeType == 30 || irangeType == 40) {
                //验证设备商品有效
                List<CommodityInfo> tempList = null;
                CommodityInfo temp = null;
                for (String commodityId : promotionsVo.getCommodityIds()) {
                    temp = new CommodityInfo();
                    temp.setId(commodityId);
                    temp.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                    tempList = commodityInfoService.selectByEntityWhere(temp);
                    if (null != tempList && tempList.size() > 0) {
                        commodityIdSb.append(tempList.get(0).getId() + ",");
                        commodityCodeSb.append(tempList.get(0).getScode() + ",");
                    }
                }
            }
        }
        // 执行新增
        if (StringUtils.isBlank(activityConf.getId())) {
            activityConf.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            activityConf.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            activityConf.setIisAvailable(0);
            activityConf.setIjoinNum(0);
            activityConf.setIusedNum(0);
            activityConf.setIdelFlag(0);
            activityConf.setSconfCode(CoreUtils.newCode("ac_activity_conf"));
            activityConf.setTaddTime(new Date());
            activityConf.setTupdateTime(new Date());
            activityConf.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            activityConf.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            this.insert(activityConf);
            if (activityConf.getIdiscountWay().intValue() != 40) {
                //活动优惠信息新增
                addDiscountDetail(activityConf, promotionsVo);
            }

            //同时新增 设备应用范围数据
            UseRange useRange = new UseRange();
            useRange.setIrangeType(activityConf.getIrangeType());
            useRange.setSacCode(activityConf.getSconfCode());
            useRange.setSacId(activityConf.getId());
            useRange.setTaddTime(new Date());
            useRange.setTupdateTime(new Date());
            useRange.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            useRange.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            String commodityCode = commodityCodeSb.toString();
            if (StringUtil.isNotBlank(commodityCode)) {
                commodityCode = commodityCode.substring(0, commodityCode.length() - 1);
                useRange.setScommodityCode(commodityCode);
            }
            String commodityId = commodityIdSb.toString();
            if (StringUtil.isNotBlank(commodityId)) {
                commodityId = commodityId.substring(0, commodityId.length() - 1);
                useRange.setScommodityId(commodityId);
            }

            String deviceCode = deviceCodeSb.toString();
            if (StringUtil.isNotBlank(deviceCode)) {
                deviceCode = deviceCode.substring(0, deviceCode.length() - 1);
                useRange.setSdeviceCode(deviceCode);
            }
            String deviceId = deviceIdSb.toString();
            if (StringUtil.isNotBlank(deviceId)) {
                deviceId = deviceId.substring(0, deviceId.length() - 1);
                useRange.setSdeviceId(deviceId);
            }
            useRangeService.insert(useRange);
        } else {// 执行修改
            ActivityConf conf = this.selectByPrimaryKey(activityConf.getId());
            if (null == conf || !conf.getSmerchantId().equals(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId())) {
                return null;
            }
            activityConf.setSconfCode(conf.getSconfCode());
            activityConf.setTupdateTime(new Date());
            activityConf.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            this.updateBySelective(activityConf);
            if (activityConf.getIdiscountWay().intValue() != 40) {
                //活动优惠信息修改
                //获取之前活动优惠信息
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("sacId", activityConf.getId());
                paramMap.put("orderCondition", " isort asc");
                List<DiscountDetail> list = discountDetailService.selectByMapWhere(paramMap);
                Integer discountType = null;
                if (list.size() > 1) {
                    discountType = 50;
                } else {
                    discountType = list.get(0).getIdiscountType();
                }
                Integer targetDiscountType = null;
                if (activityConf.getIdiscountWay().intValue() == 10) {//首单
                    targetDiscountType = 10;
                } else if (activityConf.getIdiscountWay().intValue() == 20) {//折扣
                    targetDiscountType = promotionsVo.getDiscountSelect();
                } else if (activityConf.getIdiscountWay().intValue() == 30) {//满减
                    targetDiscountType = promotionsVo.getFullReductionSelect();
                } else if (activityConf.getIdiscountWay().intValue() == 50) {//返现
                    targetDiscountType = 50;
                }
                if (targetDiscountType != null && discountType != null) {
                    if (targetDiscountType != discountType) {//新增优惠信息
                        addDiscountDetail(activityConf, promotionsVo);
                        for (DiscountDetail temp : list) {//删除原来的
                            discountDetailService.deleteByPrimaryKey(temp.getId());
                        }
                    } else {//更新
                        updateDiscountDetail(targetDiscountType, activityConf, promotionsVo, list);
                    }
                }
            }
            UseRange useRange = useRangeService.selectByActId(activityConf.getId());
            //更新
            UseRange useRangeU = new UseRange();
            useRangeU.setId(useRange.getId());
            useRangeU.setIrangeType(activityConf.getIrangeType());
            useRangeU.setTupdateTime(new Date());
            useRangeU.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            String commodityCode = commodityCodeSb.toString();
            if (StringUtil.isNotBlank(commodityCode)) {
                commodityCode = commodityCode.substring(0, commodityCode.length() - 1);
                useRangeU.setScommodityCode(commodityCode);
            }
            String commodityId = commodityIdSb.toString();
            if (StringUtil.isNotBlank(commodityId)) {
                commodityId = commodityId.substring(0, commodityId.length() - 1);
                useRangeU.setScommodityId(commodityId);
            }

            String deviceCode = deviceCodeSb.toString();
            if (StringUtil.isNotBlank(deviceCode)) {
                deviceCode = deviceCode.substring(0, deviceCode.length() - 1);
                useRangeU.setSdeviceCode(deviceCode);
            }
            String deviceId = deviceIdSb.toString();
            if (StringUtil.isNotBlank(deviceId)) {
                deviceId = deviceId.substring(0, deviceId.length() - 1);
                useRangeU.setSdeviceId(deviceId);
            }
            useRangeService.updateBySelective(useRangeU);
        }
        return activityConf;
    }

    /***
     * 更新活动优惠信息
     * @param targetDiscountType 活动明细类型
     * @param activityConf 活动配置
     * @param promotionsVo
     */
    private void updateDiscountDetail(Integer targetDiscountType, ActivityConf activityConf,
                                      PromotionsVo promotionsVo, List<DiscountDetail> list) throws Exception {
        DiscountDetail discountDetail = list.get(0);
        if (targetDiscountType == 50) {
            discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney());
            discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney());
            discountDetail.setIsort(1);
            discountDetail.setTupdateTime(new Date());
            discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            discountDetailService.updateByPrimaryKey(discountDetail);
            DiscountDetail temp = null;
            if (list.size() > 1) {
                temp = list.get(1);
            }
            if (null != promotionsVo.getFullReductionTargetMoney1()
                    && null != promotionsVo.getFullReductionTargetMoney1()) {
                if (null == temp) {//新增一个
                    discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney1());
                    discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney1());
                    discountDetail.setIsort(2);
                    discountDetail.setId(null);
                    discountDetail.setTaddTime(new Date());
                    discountDetail.setTupdateTime(new Date());
                    discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetailService.insert(discountDetail);
                } else {
                    discountDetail = temp;
                    discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney1());
                    discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney1());
                    discountDetail.setIsort(2);
                    discountDetail.setTupdateTime(new Date());
                    discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetailService.updateByPrimaryKey(discountDetail);
                }
            } else {
                if (null != temp) {
                    discountDetailService.deleteByPrimaryKey(temp.getId());//删除优惠信息
                }
            }
            if (list.size() > 2) {
                temp = list.get(2);
            } else {
                temp = null;
            }
            if (null != promotionsVo.getFullReductionTargetMoney2()
                    && null != promotionsVo.getFullReductionTargetMoney2()) {
                if (null == temp) {//新增一个
                    discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney2());
                    discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney2());
                    discountDetail.setIsort(2);
                    discountDetail.setId(null);
                    discountDetail.setTaddTime(new Date());
                    discountDetail.setTupdateTime(new Date());
                    discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetailService.insert(discountDetail);
                } else {
                    discountDetail = temp;
                    discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney2());
                    discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney2());
                    discountDetail.setIsort(3);
                    discountDetail.setTupdateTime(new Date());
                    discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    discountDetailService.updateByPrimaryKey(discountDetail);
                }
            } else {
                if (null != temp) {
                    discountDetailService.deleteByPrimaryKey(temp.getId());//删除优惠信息
                }
            }
        } else {
            if (targetDiscountType == 10) {
                discountDetail.setFtargetMoney(promotionsVo.getFirstTargetMoney());
                discountDetail.setFdiscountMoney(promotionsVo.getFirstDiscountAmount());
            }
            if (targetDiscountType == 20
                    || targetDiscountType == 40) {
                discountDetail.setFtargetMoney(promotionsVo.getDiscountTargetMoney());
            }
            if (targetDiscountType == 30
                    || targetDiscountType == 40) {
                discountDetail.setFtargetMoney(promotionsVo.getDiscountTargetMoney());
            }
            if (targetDiscountType == 20
                    || targetDiscountType == 30
                    || targetDiscountType == 40) {
                discountDetail.setFdiscount(promotionsVo.getFdiscount());
            }
            if (targetDiscountType == 50
                    || targetDiscountType == 60
                    || targetDiscountType == 80) {
                discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney());
                discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney());
            }
            if (targetDiscountType == 70
                    || targetDiscountType == 80) {
                discountDetail.setFtargetNum(promotionsVo.getFullReductionTargetNum());
            }
            if (targetDiscountType == 60) {
                discountDetail.setFdiscountLimit(promotionsVo.getFdiscountLimit());
            }
            discountDetail.setTupdateTime(new Date());
            discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            discountDetailService.updateBySelective(discountDetail);
        }
    }

    /**
     * 新增活动优惠信息
     *
     * @param activityConf 活动配置
     * @param promotionsVo
     */
    private void addDiscountDetail(ActivityConf activityConf, PromotionsVo promotionsVo) throws Exception {
        DiscountDetail discountDetail = new DiscountDetail();
        discountDetail.setSacCode(activityConf.getSconfCode());
        discountDetail.setSacId(activityConf.getId());
        if (activityConf.getIdiscountWay().intValue() == 10) {//首单
            discountDetail.setIdiscountType(10);
            discountDetail.setFtargetMoney(promotionsVo.getFirstTargetMoney());
            discountDetail.setFdiscountMoney(promotionsVo.getFirstDiscountAmount());
            discountDetail.setIsort(1);
        } else if (activityConf.getIdiscountWay().intValue() == 20) {//折扣
            discountDetail.setIdiscountType(promotionsVo.getDiscountSelect());
            if (promotionsVo.getDiscountSelect().intValue() == 20
                    || promotionsVo.getDiscountSelect().intValue() == 40) {
                discountDetail.setFtargetMoney(promotionsVo.getDiscountTargetMoney());
            }
            if (promotionsVo.getDiscountSelect().intValue() == 30
                    || promotionsVo.getDiscountSelect().intValue() == 40) {
                discountDetail.setFtargetNum(promotionsVo.getDiscountTargetNum());
            }
            discountDetail.setFdiscount(promotionsVo.getFdiscount());
            discountDetail.setIsort(1);
        } else if (activityConf.getIdiscountWay().intValue() == 30) {//满减
            discountDetail.setIdiscountType(promotionsVo.getFullReductionSelect());
            if (promotionsVo.getFullReductionSelect().intValue() == 50
                    || promotionsVo.getFullReductionSelect().intValue() == 60
                    || promotionsVo.getFullReductionSelect().intValue() == 80) {
                discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney());
                discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney());
            }
            if (promotionsVo.getFullReductionSelect().intValue() == 70
                    || promotionsVo.getFullReductionSelect().intValue() == 80) {
                discountDetail.setFtargetNum(promotionsVo.getFullReductionTargetNum());
            }
            if (promotionsVo.getFullReductionSelect().intValue() == 60) {
                discountDetail.setFdiscountLimit(promotionsVo.getFdiscountLimit());
            }
            discountDetail.setIsort(1);
        } else if (activityConf.getIdiscountWay().intValue() == 50) {//返现
            discountDetail.setIdiscountType(100);
            discountDetail.setFtargetMoney(promotionsVo.getCashBackTargetMoney());
            discountDetail.setFdiscountMoney(promotionsVo.getFcashBackMoney());
            discountDetail.setIsort(1);
        }
        discountDetail.setTaddTime(new Date());
        discountDetail.setTupdateTime(new Date());
        discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        discountDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        discountDetailService.insert(discountDetail);
        if (promotionsVo.getFullReductionSelect().intValue() == 50) {
            if (null != promotionsVo.getFullReductionTargetMoney1()
                    && null != promotionsVo.getFullReductionTargetMoney1()) {
                discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney1());
                discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney1());
                discountDetail.setIsort(2);
                discountDetail.setId(null);
                discountDetail.setTaddTime(new Date());
                discountDetail.setTupdateTime(new Date());
                discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                discountDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                discountDetailService.insert(discountDetail);
            }
            if (null != promotionsVo.getFullReductionTargetMoney2()
                    && null != promotionsVo.getFullReductionTargetMoney2()) {
                discountDetail.setFtargetMoney(promotionsVo.getFullReductionTargetMoney2());
                discountDetail.setFdiscountMoney(promotionsVo.getFullReductionDiscountMoney2());
                discountDetail.setIsort(3);
                discountDetail.setId(null);
                discountDetail.setTaddTime(new Date());
                discountDetail.setTupdateTime(new Date());
                discountDetail.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                discountDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                discountDetailService.insert(discountDetail);
            }
        }
    }

    /**
     * 查询活动信息
     *
     * @param map 查询参数
     * @return
     */
    @Override
    public ActivityConf selectByMap(Map<String, Object> map) {
        return activityConfDao.selectByMap(map);
    }

}