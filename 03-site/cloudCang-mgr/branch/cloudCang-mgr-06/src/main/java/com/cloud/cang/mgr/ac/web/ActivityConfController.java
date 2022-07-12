package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.ac.domain.ActivityConfDomain;
import com.cloud.cang.mgr.ac.domain.UseRangeDomain;
import com.cloud.cang.mgr.ac.service.*;
import com.cloud.cang.mgr.ac.vo.ActivityConfVo;
import com.cloud.cang.mgr.ac.vo.PromotionsVo;
import com.cloud.cang.mgr.ac.vo.ScenesVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.ac.*;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.text.MessageFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName: ActivityConfController
 * @Description: 活动管理
 * @Author: zhouhong
 * @Date: 2018/2/7 19:33
 */
@Controller
@RequestMapping("/activityConf")
public class ActivityConfController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityConfController.class);

    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private UseRangeService useRangeService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private CouponRuleService couponRuleService;
    @Autowired
    private IntegralRuleService integralRuleService;
    @Autowired
    private DiscountDetailService discountDetailService;
    @Autowired
    private CommodityInfoService commodityInfoService;

    /**
     * @Author: zhouhong
     * @Description: 场景活动列表
     * @Date: 2018/2/7 19:47
     */
    @RequestMapping("/scenesList")
    public String scenesList(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "ac/activityConf/scenes-list";
    }

    /**
     * @Author: zhouhong
     * @Description: 促销活动列表
     */
    @RequestMapping("/promotionsList")
    public String promotionsList(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "ac/activityConf/promotions-list";
    }

    /**
     * @Author: zhouhong
     * @Description: 查看活动设备
     * @Date: 2018/2/7 19:47
     */
    @RequestMapping("/viewDevice")
    public String viewDevice(ModelMap map, String sid) {
        ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
        List<DeviceInfo> deviceInfos = null;
        if (conf.getIrangeType().intValue() == BizTypeDefinitionEnum.RangeType.ALL.getCode() ||
                conf.getIrangeType().intValue() == BizTypeDefinitionEnum.RangeType.SECTION_COMMODITY.getCode()) {
            //全部设备
            Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
            String queryCondition = operatorService.generatePurviewSql(operator, 200);
            deviceInfos = deviceInfoService.selectAvailable(conf.getSmerchantId(),queryCondition);
        } else {
            //获取设备的应用范围
            UseRange useRange = useRangeService.selectByActId(conf.getId());
            if (null != useRange && StringUtil.isNotBlank(useRange.getSdeviceId())) {
                deviceInfos = new ArrayList<DeviceInfo>();
                String[] arr = useRange.getSdeviceId().split(",");
                DeviceInfo temp = null;
                for (String deviceId : arr) {
                    temp = deviceInfoService.selectByPrimaryKey(deviceId);
                    deviceInfos.add(temp);
                }
            }
        }
        map.put("deviceInfos", deviceInfos);//设备
        return "ac/activityConf/viewDevice-list";
    }

    /**
     * @Author: ChangTanchang
     * @Description: 查看活动商品
     * @Date: 2018/5/12 16:51
     */
    @RequestMapping("/viewCommodity")
    public String viewCommodity(ModelMap map, String sid) {
        ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
        List<CommodityInfo> commodityInfos = null;
        if (conf.getIrangeType().intValue() == BizTypeDefinitionEnum.RangeType.ALL.getCode() ||
                conf.getIrangeType().intValue() == BizTypeDefinitionEnum.RangeType.SECTION_DEVICE.getCode()) {
            //全部商品
            commodityInfos = commodityInfoService.selectAllCommodityByMerchantId(conf.getSmerchantId());
        } else {
            //获取商品的应用范围
            UseRange useRange = useRangeService.selectByActId(conf.getId());
            if (null != useRange && StringUtil.isNotBlank(useRange.getScommodityId())) {
                commodityInfos = new ArrayList<CommodityInfo>();
                String[] arr = useRange.getScommodityId().split(",");
                CommodityInfo temp = null;
                for (String commodityId : arr) {
                    temp = commodityInfoService.selectByPrimaryKey(commodityId);
                    commodityInfos.add(temp);
                }
            }
        }
        map.put("commodityInfos", commodityInfos);//商品
        return "ac/activityConf/viewCommodity-list";
    }

    /**
     * @Author: zhouhong
     * @Description: 查询场景活动列表数据
     * @param: activityConfVo 查询参数
     * @param: paramPage 分页参数
     */
    @RequestMapping("/queryScenesData")
    @ResponseBody
    public PageListVo<List<ActivityConfDomain>> queryScenesData(ActivityConfVo activityConfVo, ParamPage paramPage) {
        logger.debug("获取场景活动分页数据：{}", paramPage);
        logger.debug("获取场景活动查询数据：{}", activityConfVo);
        activityConfVo.setItype(10);
        return getActivityData(activityConfVo, paramPage);
    }

    /**
     * @Author: zhouhong
     * @Description: 查询促销活动列表数据
     * @param: activityConfVo 查询参数
     * @param: paramPage 分页参数
     */
    @RequestMapping("/queryPromotionsData")
    @ResponseBody
    public PageListVo<List<ActivityConfDomain>> queryPromotionsData(ActivityConfVo activityConfVo, ParamPage paramPage) {
        logger.debug("获取促销活动分页数据：{}", paramPage);
        logger.debug("获取促销活动查询数据：{}", activityConfVo);
        activityConfVo.setItype(20);
        return getActivityData(activityConfVo, paramPage);
    }

    /**
     * @return com.cloud.cang.common.PageListVo<java.util.List<com.cloud.cang.mgr.ac.domain.ActivityConfDomain>>
     * @Author: zhouhong
     * @Description: 查询数据
     * @param: activityConfVo 查询参数
     * @param: paramPage 分页参数
     */
    private PageListVo<List<ActivityConfDomain>> getActivityData(ActivityConfVo activityConfVo, ParamPage paramPage) {
        PageListVo<List<ActivityConfDomain>> pageListVo = new PageListVo<List<ActivityConfDomain>>();
        Page<ActivityConfDomain> page = new Page<ActivityConfDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        //设置默认参数
        activityConfVo.setIdelFlag(0);
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 20);
        if (StringUtil.isNotBlank(queryCondition)) {
            activityConfVo.setQueryCondition(queryCondition);
        }
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            activityConfVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = activityConfService.selectPageByDomainWhere(page, activityConfVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * @Author: zhouhong
     * @Description:场景活动编辑
     * @param: sid 活动ID
     * @Date: 2018/2/8 20:14
     */
    @RequestMapping("/editScenes")
    public String editScenes(ModelMap map, String sid) {
        try {
            ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
            if (null == conf) {//新增
                conf = new ActivityConf();
                conf.setIrangeType(10);
            } else {
                //编辑获取设备信息
                if (conf.getIrangeType().intValue() != BizTypeDefinitionEnum.RangeType.ALL.getCode()) {
                    //获取设备的应用范围
                    UseRange useRange = useRangeService.selectByActId(conf.getId());
                    map.put("useRange", useRange);
                }
            }
            map.put("conf", conf);
            return "ac/activityConf/scenes-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * @Author: zhouhong
     * @Description:促销活动编辑
     * @param: sid 活动ID
     * @Date: 2018/2/8 20:14
     */
    @RequestMapping("/editPromotions")
    public String editPromotions(ModelMap map, String sid) {
        try {
            ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
            if (null == conf) {//新增
                conf = new ActivityConf();
                conf.setIrangeType(10);
            } else {
                //编辑获取设备信息
                if (conf.getIrangeType().intValue() != BizTypeDefinitionEnum.RangeType.ALL.getCode()) {
                    //获取设备的应用范围
                    UseRange useRange = useRangeService.selectByActId(conf.getId());
                    map.put("useRange", useRange);
                }
                //活动优惠信息
                if (conf.getIdiscountWay().intValue() != 40) {
                    //获取之前活动优惠信息
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("sacId", conf.getId());
                    paramMap.put("orderCondition", " isort asc");
                    List<DiscountDetail> list = discountDetailService.selectByMapWhere(paramMap);
                    map.put("confDetail", list.get(0));
                    if (list.size() > 1) {
                        map.put("confDetail1", list.get(1));
                    }
                    if (list.size() > 2) {
                        map.put("confDetail2", list.get(2));
                    }
                }
            }
            map.put("conf", conf);
            return "ac/activityConf/promotions-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * @Author: zhouhong
     * @Description: 新增和修改 场景活动
     * @param: scenesVo
     * @Date: 2018/2/10 15:21
     */
    @RequestMapping("/saveScenes")
    public @ResponseBody
    ResponseVo<ActivityConf> saveScenes(ScenesVo scenesVo) {
        try {


            if (StringUtils.isBlank(scenesVo.getSconfName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动名称不能为空！");
            }
            if (null == scenesVo.getIscenesType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择活动场景类型！");
            }

            if (scenesVo.getIscenesType().intValue() == 20 || scenesVo.getIscenesType().intValue() == 50 ||
                    scenesVo.getIscenesType().intValue() == 30 || scenesVo.getIscenesType().intValue() == 40) {
                scenesVo.setIcountType(BizTypeDefinitionEnum.ActivityCountType.DURING_ACTIVITY.getCode());
                scenesVo.setIuserAllCount(1);
                scenesVo.setIuserDayCount(1);
            }
            if (null == scenesVo.getIallCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动期间总参与次数限制不能为空！");
            }
            if (null == scenesVo.getIcountType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型不能为空！");
            }
            if (null == scenesVo.getIuserAllCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型总参与次数不能为空！");
            }
            String limitStr = "用户";
            if (scenesVo.getIuserAllCount().intValue() == BizTypeDefinitionEnum.ActivityCountType.DURING_ACTIVITY_EQUIPMENT.getCode()) {
                limitStr = "设备";
            }
            if (scenesVo.getIallCount().intValue() > 0 && scenesVo.getIuserAllCount().intValue() > scenesVo.getIallCount().intValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动期间总参数次数必须大于" + limitStr + "总参与次数！");
            }
            if (null == scenesVo.getIuserDayCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型单日参与次数不能为空！");
            }
            if (scenesVo.getIuserAllCount().intValue() > 0 && scenesVo.getIuserDayCount().intValue() > scenesVo.getIuserAllCount().intValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(limitStr + "总参数次数必须大于" + limitStr + "单日参与次数！");
            }
            if (null == scenesVo.getTactiveStartTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动开始时间不能为空！");
            }
            if (null == scenesVo.getTactiveEndTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动结束时间不能为空！");
            }
            if (scenesVo.getTactiveEndTime().before(scenesVo.getTactiveStartTime())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动结束时间必须在活动开始时间之后！");
            }
            /*if(null == scenesVo.getIrangeDevice() || null == scenesVo.getIrangeCommodity() ||
                    (scenesVo.getIrangeDevice().intValue() == 1 && (null == scenesVo.getDeviceIds()|| scenesVo.getDeviceIds().length <= 0)) ||
                    (scenesVo.getIrangeCommodity().intValue() == 1 && (null == scenesVo.getCommodityIds()|| scenesVo.getCommodityIds().length <= 0))) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动参与设备或商品异常！");
            }*/
            if (scenesVo.getIscenesType().intValue() != 30 && scenesVo.getIscenesType().intValue() != 40) {
                if (null == scenesVo.getIrangeDevice() ||
                        (scenesVo.getIrangeDevice().intValue() == 1 && (null == scenesVo.getDeviceIds() || scenesVo.getDeviceIds().length <= 0))) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动参与设备异常！");
                }
            } else {
                scenesVo.setIrangeDevice(10);
            }
            scenesVo.setItype(10);
            //判断活动同类型活动是否存在 时间段
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(scenesVo.getId())) {//编辑排除自己
                ActivityConf temp = activityConfService.selectActivityByIdAndMerchantId(scenesVo.getId());
                if (null == temp) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常，保存失败！");
                }
                paramMap.put("id", scenesVo.getId());
            }
            paramMap.put("type", scenesVo.getItype());
            paramMap.put("iisAvailable", -1);
            paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            paramMap.put("scenesType", scenesVo.getIscenesType());
            paramMap.put("startTime", scenesVo.getTactiveStartTime());
            paramMap.put("endTime", scenesVo.getTactiveEndTime());
            List<ActivityConf> tempAc = activityConfService.isActivityExistByMap(paramMap);
            if (null != tempAc && tempAc.size() > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("同时间段已存在此场景活动！");
            }
            ActivityConf activityConf = activityConfService.saveOrUpdateScenes(scenesVo);
            if (null == activityConf) {
                return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("数据异常，操作失败");
            }
            // 执行新增
            if (StringUtils.isBlank(scenesVo.getId())) {
                //操作日志
                String logText = MessageFormat.format("新增场景活动，编号{0}", activityConf.getSconfCode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行修改
                //操作日志
                String logText = MessageFormat.format("编辑场景活动，编号{0}", activityConf.getSconfCode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(activityConf);
        } catch (Exception ex) {
            logger.error("新增和修改 场景活动异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存活动失败");
        }
    }


    /**
     * @Author: zhouhong
     * @Description: 新增和修改 促销活动
     * @param: promotionsVo
     * @Date: 2018/2/10 15:21
     */
    @RequestMapping("/savePromotions")
    public @ResponseBody
    ResponseVo<ActivityConf> savePromotions(PromotionsVo promotionsVo) {
        try {
            if (StringUtils.isBlank(promotionsVo.getSconfName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动名称不能为空！");
            }
            if (null == promotionsVo.getIdiscountWay()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择活动优惠方式！");
            }
            if (promotionsVo.getIdiscountWay() % 10 != 0 || promotionsVo.getIdiscountWay() < 10 || promotionsVo.getIdiscountWay() > 50) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动优惠方式异常！");
            }
            if (promotionsVo.getIdiscountWay().intValue() == 10) {//首单
                if (null == promotionsVo.getFirstTargetMoney()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("首单满足条件金额不能为空！");
                }
                if (null == promotionsVo.getFirstDiscountAmount()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("首单优惠金额不能为空！");
                }
            } else if (promotionsVo.getIdiscountWay().intValue() == 20) {//折扣
                if (null == promotionsVo.getDiscountSelect()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择活动折扣方式！");
                }
                if (promotionsVo.getDiscountSelect().intValue() == 30 || promotionsVo.getDiscountSelect().intValue() == 40) {
                    if (null == promotionsVo.getDiscountTargetNum() || promotionsVo.getDiscountTargetNum() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("折扣满足条件件数不能为空！");
                    }
                }
                if (promotionsVo.getDiscountSelect().intValue() == 20 || promotionsVo.getDiscountSelect().intValue() == 40) {
                    if (null == promotionsVo.getDiscountTargetMoney()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("折扣满足条件金额不能为空！");
                    }
                }
                if (null == promotionsVo.getFdiscount() || promotionsVo.getFdiscount().doubleValue() <= 0) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠折扣不能为空！");
                }
            } else if (promotionsVo.getIdiscountWay().intValue() == 30) {//满减
                if (null == promotionsVo.getFullReductionSelect()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择活动满减方式！");
                }
                if (promotionsVo.getFullReductionSelect().intValue() == 70
                        || promotionsVo.getFullReductionSelect().intValue() == 80) {
                    if (null == promotionsVo.getFullReductionTargetNum() || promotionsVo.getFullReductionTargetNum() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("满减条件件数不能为空！");
                    }
                }
                if (promotionsVo.getFullReductionSelect().intValue() == 50
                        || promotionsVo.getFullReductionSelect().intValue() == 60
                        || promotionsVo.getFullReductionSelect().intValue() == 80) {
                    if (null == promotionsVo.getFullReductionTargetMoney()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("满减条件金额不能为空！");
                    }
                    if (null == promotionsVo.getFullReductionDiscountMoney() || promotionsVo.getFullReductionDiscountMoney().doubleValue() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("满减优惠金额不能为空！");
                    }
                }
                if (promotionsVo.getFullReductionSelect().intValue() == 60) {
                    if (null == promotionsVo.getFdiscountLimit()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("满减优惠上限不能为空！");
                    }
                }
            } else if (promotionsVo.getIdiscountWay().intValue() == 30) {//返现
                if (null == promotionsVo.getCashBackTargetMoney()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("返现满足条件金额不能为空！");
                }
                if (null == promotionsVo.getFcashBackMoney() || promotionsVo.getFcashBackMoney().doubleValue() <= 0) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("返现金额不能为空！");
                }
            }
            if (null == promotionsVo.getIallCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动期间总参与次数限制不能为空！");
            }
            if (null == promotionsVo.getIcountType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型不能为空！");
            }
            if (null == promotionsVo.getIuserAllCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型总参与次数不能为空！");
            }
            String limitStr = "用户";
            if (promotionsVo.getIuserAllCount().intValue() == BizTypeDefinitionEnum.ActivityCountType.DURING_ACTIVITY_EQUIPMENT.getCode()) {
                limitStr = "设备";
            }
            if (promotionsVo.getIallCount().intValue() > 0 && promotionsVo.getIuserAllCount().intValue() > promotionsVo.getIallCount().intValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动期间总参数次数必须大于" + limitStr + "总参与次数！");
            }
            if (null == promotionsVo.getIuserDayCount()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("次数限制类型单日参与次数不能为空！");
            }
            if (promotionsVo.getIuserAllCount().intValue() > 0 && promotionsVo.getIuserDayCount().intValue() > promotionsVo.getIuserAllCount().intValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(limitStr + "总参数次数必须大于" + limitStr + "单日参与次数！");
            }
            if (null == promotionsVo.getTactiveStartTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动开始时间不能为空！");
            }
            if (null == promotionsVo.getTactiveEndTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动结束时间不能为空！");
            }
            if (promotionsVo.getTactiveEndTime().before(promotionsVo.getTactiveStartTime())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动结束时间必须在活动开始时间之后！");
            }
            if (null == promotionsVo.getIrangeDevice() || null == promotionsVo.getIrangeCommodity() ||
                    (promotionsVo.getIrangeDevice().intValue() == 1 && (null == promotionsVo.getDeviceIds() || promotionsVo.getDeviceIds().length <= 0)) ||
                    (promotionsVo.getIrangeCommodity().intValue() == 1 && (null == promotionsVo.getCommodityIds() || promotionsVo.getCommodityIds().length <= 0))) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动参与设备或商品异常！");
            }
            promotionsVo.setItype(20);
            //判断活动同类型活动是否存在 时间段
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(promotionsVo.getId())) {//编辑排除自己
                ActivityConf temp = activityConfService.selectActivityByIdAndMerchantId(promotionsVo.getId());
                if (null == temp) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常，保存失败！");
                }
                paramMap.put("id", promotionsVo.getId());
            }
            paramMap.put("type", promotionsVo.getItype());
            paramMap.put("iisAvailable", -1);
            paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            paramMap.put("discountWay", promotionsVo.getIdiscountWay());//优惠方式
            paramMap.put("startTime", promotionsVo.getTactiveStartTime());
            paramMap.put("endTime", promotionsVo.getTactiveEndTime());
            List<ActivityConf> tempAc = activityConfService.isActivityExistByMap(paramMap);
            if (null != tempAc && tempAc.size() > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("同时间段已存在此优惠活动！");
            }
            ActivityConf activityConf = activityConfService.saveOrUpdatePromotionsVo(promotionsVo);
            if (null == activityConf) {
                return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("数据异常，操作失败");
            }
            // 执行新增
            if (StringUtils.isBlank(promotionsVo.getId())) {
                //操作日志
                String logText = MessageFormat.format("新增促销活动，编号{0}", activityConf.getSconfCode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行修改
                //操作日志
                String logText = MessageFormat.format("编辑促销活动，编号{0}", activityConf.getSconfCode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(activityConf);
        } catch (Exception ex) {
            logger.error("编辑促销活动异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存活动失败");
        }
    }

    /**
     * 删除活动
     *
     * @param checkboxId 活动ID集合
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            activityConfService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除商户，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            logger.error("删除活动异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e1) {
            logger.error("删除活动异常：{}", e1);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，删除失败！");
        }
    }


    /**
     * @return com.cloud.cang.common.ResponseVo<java.lang.String>
     * @Author: zhouhong
     * @Description: 启用活动
     * @param: sid 活动ID
     * @Date: 2018/2/10 20:13
     */
    @RequestMapping("/enabled")
    public @ResponseBody
    ResponseVo<String> enabled(String sid) {
        try {
            ActivityConf activityConf = activityConfService.selectActivityByIdAndMerchantId(sid);
            //验证数据有效性
            ResponseVo<String> responseVo = verifyActivityData(activityConf, 1);
            if (!responseVo.isSuccess()) {
                return responseVo;
            }
            activityConfService.enabledOrClosed(activityConf, 1);

            //判断是否是促销活动
            if (activityConf.getItype().intValue() == 20) {//促销活动
                //更新商品 参与活动次数
                commodityInfoService.updateCommodityJoinNum(activityConf);
            }
            //操作日志
            String logText = MessageFormat.format("启用活动，编号{0}", activityConf.getSconfCode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("启用活动异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，开启失败");
        }
    }


    /**
     * @return com.cloud.cang.common.ResponseVo<java.lang.String>
     * @Author: zhouhong
     * @Description: 关闭活动
     * @param: sid 活动ID
     * @Date: 2018/2/10 20:13
     */
    @RequestMapping("/closed")
    public @ResponseBody
    ResponseVo<String> closed(String sid) {
        try {
            ActivityConf activityConf = activityConfService.selectActivityByIdAndMerchantId(sid);
            //验证数据有效性
            ResponseVo<String> responseVo = verifyActivityData(activityConf, 2);
            if (!responseVo.isSuccess()) {
                return responseVo;
            }
            activityConfService.enabledOrClosed(activityConf, 2);
            //操作日志
            String logText = MessageFormat.format("关闭活动，编号{0}", activityConf.getSconfCode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("关闭活动异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，开启失败");
        }
    }

    /**
     * @param activityConf 活动数据
     * @param type         验证类型 1 启用 2 禁用
     * @Author: zhouhong
     * @Description: 验证活动数据有效性
     * @Date: 2018/2/10 20:51
     */
    private ResponseVo<String> verifyActivityData(ActivityConf activityConf, int type) {
        if (null == activityConf) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("活动不存在！");
        }
        if (type == 2) {
            if (activityConf.getIisAvailable().intValue() != 1) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("活动不是开启状态！");
            }
        }
        if (type == 1) {
            if (activityConf.getTactiveEndTime().before(new Date())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("活动结束时间必须在当前时间之后！");
            }
            UseRange useRange = new UseRange();
            useRange.setSacId(activityConf.getId());
            useRange.setSacCode(activityConf.getSconfCode());
            List<UseRange> useRanges = useRangeService.selectByEntityWhere(useRange);
            if (null == useRanges || useRanges.size() <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动配置错误，请确认活动应用范围配置存在并有效！");
            }
            //判断活动同类型活动是否存在 时间段
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(activityConf.getId())) {//操作排除自己
                paramMap.put("id", activityConf.getId());
            }
            paramMap.put("type", activityConf.getItype());
            paramMap.put("iisAvailable", -1);
            paramMap.put("merchantId", activityConf.getSmerchantId());
            if (activityConf.getItype().intValue() == BizTypeDefinitionEnum.ActivityType.SCENES_ACTIVITY.getCode()) {
                paramMap.put("scenesType", activityConf.getIscenesType());
            } else if (activityConf.getItype().intValue() == BizTypeDefinitionEnum.ActivityType.PROMOTIONS_ACTIVITY.getCode()) {
                paramMap.put("discountWay", activityConf.getIdiscountWay());
            }
            paramMap.put("startTime", activityConf.getTactiveStartTime());
            paramMap.put("endTime", activityConf.getTactiveEndTime());
            List<ActivityConf> tempAc = activityConfService.isActivityExistByMap(paramMap);
            if (null != tempAc && tempAc.size() > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("同时间段已存在此活动！");
            }
            if (activityConf.getItype().intValue() == BizTypeDefinitionEnum.ActivityType.SCENES_ACTIVITY.getCode()) {
                //场景活动
                //查询券规则
                CouponRule couponRule = new CouponRule();
                couponRule.setSacId(activityConf.getId());
                couponRule.setSacCode(activityConf.getSconfCode());
                couponRule.setIdelFlag(0);
                couponRule.setIisValid(1);
                List<CouponRule> couponRules = couponRuleService.selectByEntityWhere(couponRule);
                if (null == couponRules || couponRules.size() <= 0) {
                    //判断积分规则是否可用
                    IntegralRule integralRule = new IntegralRule();
                    integralRule.setSacId(activityConf.getId());
                    integralRule.setSacCode(activityConf.getSconfCode());
                    integralRule.setIdelFlag(0);
                    integralRule.setIisValid(1);
                    List<IntegralRule> integralRules = integralRuleService.selectByEntityWhere(integralRule);
                    if (null == integralRules || integralRules.size() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动配置错误，请确认券规则或积分规则存在并有效！");
                    }
                }
            } else {//促销活动
                //获取活动优惠信息
                if (activityConf.getIdiscountWay().intValue() != 40) {
                    paramMap = new HashMap<String, Object>();
                    paramMap.put("sacId", activityConf.getId());
                    paramMap.put("orderCondition", " isort asc");
                    List<DiscountDetail> list = discountDetailService.selectByMapWhere(paramMap);
                    if (null == list || list.size() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动配置错误，数据异常");
                    }
                } else {
                    //查询券规则
                    CouponRule couponRule = new CouponRule();
                    couponRule.setSacId(activityConf.getId());
                    couponRule.setSacCode(activityConf.getSconfCode());
                    couponRule.setIdelFlag(0);
                    couponRule.setIisValid(1);
                    List<CouponRule> couponRules = couponRuleService.selectByEntityWhere(couponRule);
                    if (null == couponRules || couponRules.size() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动配置错误，请确认券规则存在并有效！");
                    }
                }
            }
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 促销活动详情
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/promotionsView")
    public String promotionsView(ModelMap modelMap, String sid) {
        try {
            if (StringUtils.isNotBlank(sid) && sid != "") {
                ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
                //活动优惠信息
                if (conf.getIdiscountWay().intValue() != 40) {
                    //获取之前活动优惠信息
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("sacId", conf.getId());
                    paramMap.put("orderCondition", " isort asc");
                    List<DiscountDetail> list = discountDetailService.selectByMapWhere(paramMap);
                    modelMap.put("confDetail", list.get(0));
                    if (list.size() > 1) {
                        modelMap.put("confDetail1", list.get(1));
                    }
                    if (list.size() > 2) {
                        modelMap.put("confDetail2", list.get(2));
                    }
                }
                modelMap.put("conf", conf);
                return "ac/activityConf/promotions-view";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 场景活动详情
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/scenesView")
    public String scenesView(ModelMap modelMap, String sid) {
        try {
            if (StringUtils.isNotBlank(sid) && sid != "") {
                ActivityConf conf = activityConfService.selectByPrimaryKey(sid);
                modelMap.put("conf", conf);
                return "ac/activityConf/scenes-view";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

}
