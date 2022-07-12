package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ac.service.ActivityConfService;
import com.cloud.cang.mgr.ac.service.CouponRuleService;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.CouponRule;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName: CouponRuleController
 * @Description: 送券规则
 * @Author: zhouhong
 * @Date: 2018/2/7 19:33
 */
@Controller
@RequestMapping("/couponRule")
public class CouponRuleController {

    private static final Logger logger = LoggerFactory.getLogger(CouponRuleController.class);

    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private CouponRuleService couponRuleService;
    @Autowired
    private BrandInfoService brandInfoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private DeviceInfoService deviceInfoService;


    /**
     * @Author: zhouhong
     * @Description:场景活动编辑 送券规则
     * @param: sid 活动ID
     * @Date: 2018/2/8 20:14
     */
    @RequestMapping("/edit")
    public String edit(ModelMap map, String sid) {
        try {
            ActivityConf activityConf = activityConfService.selectByPrimaryKey(sid);
            CouponRule param = new CouponRule();
            param.setSacId(activityConf.getId());
            param.setSacCode(activityConf.getSconfCode());
            param.setIdelFlag(0);
            List<CouponRule> couponRules = couponRuleService.selectByEntityWhere(param);
            CouponRule couponRule = null;
            if (null != couponRules && couponRules.size() > 0) {//编辑
                couponRule = couponRules.get(0);
                //获取限制数据
                if ((null != activityConf.getIscenesType() && (activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_ORDER.getCode() ||
                        activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode() ||
                        activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode()))
                        || activityConf.getItype().intValue() == 20) {//判断是不是下单等场景
                    /*if (StringUtil.isNotBlank(couponRule.getSgiveLimitDevice())) {//设备信息
                                    String tempArr[] = couponRule.getSgiveLimitDevice().split(",");
                                    DeviceInfo deviceInfo = null;
                                    StringBuffer sb = new StringBuffer();
                                    for(String scode : tempArr) {
                                        deviceInfo = deviceInfoService.selectByCode(scode);
                                        sb.append(deviceInfo.getId()+",");
                                    }
                                    map.put("giveDeviceIds",sb.toString().length()>0 ? sb.toString().substring(0,sb.toString().length()-1) : "");
                    }*/
                    if (StringUtil.isNotBlank(couponRule.getSgiveLimitCategory())) {
                        String tempArr[] = couponRule.getSgiveLimitCategory().split(",");
                        Category category = null;
                        StringBuffer sb = new StringBuffer();
                        for (String categoryId : tempArr) {
                            category = categoryService.selectByPrimaryKey(categoryId);
                            if (null != category) {
                                sb.append(category.getSname() + ",");
                            }
                        }
                        map.put("giveLimitCategory", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                    }
                    if (StringUtil.isNotBlank(couponRule.getSgiveLimitBrand())) {//商品品牌
                        String tempArr[] = couponRule.getSgiveLimitBrand().split(",");
                        BrandInfo brandInfo = null;
                        StringBuffer sb = new StringBuffer();
                        for (String brandId : tempArr) {
                            brandInfo = brandInfoService.selectByPrimaryKey(brandId);
                            if (null != brandInfo) {
                                sb.append(brandInfo.getSname() + ",");
                            }
                        }
                        map.put("giveLimitBrand", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                    }
                    if (StringUtil.isNotBlank(couponRule.getSgiveLimitCommodity())) {//商品信息
                        String tempArr[] = couponRule.getSgiveLimitCommodity().split(",");
                        CommodityInfo commodityInfo = null;
                        StringBuffer sb = new StringBuffer();
                        for (String commodityCode : tempArr) {
                            commodityInfo = commodityInfoService.selectByCode(commodityCode);
                            if (null != commodityInfo) {
                                sb.append(commodityInfo.getId() + ",");
                            }
                        }
                        map.put("giveCommodityIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                    }
                }
                //获取使用数据
                if (StringUtil.isNotBlank(couponRule.getSuseLimitBrand())) {//商品品牌
                    String tempArr[] = couponRule.getSuseLimitBrand().split(",");
                    BrandInfo brandInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String brandId : tempArr) {
                        brandInfo = brandInfoService.selectByPrimaryKey(brandId);
                        if (null != brandInfo) {
                            sb.append(brandInfo.getSname() + ",");
                        }
                    }
                    map.put("useLimitBrand", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponRule.getSuseLimitCategory())) {//商品分类
                    String tempArr[] = couponRule.getSuseLimitCategory().split(",");
                    Category category = null;
                    StringBuffer sb = new StringBuffer();
                    for (String categoryId : tempArr) {
                        category = categoryService.selectByPrimaryKey(categoryId);
                        if (null != category) {
                            sb.append(category.getSname() + ",");
                        }
                    }
                    map.put("useLimitCategory", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponRule.getSuseLimitCommodity())) {//商品信息
                    String tempArr[] = couponRule.getSuseLimitCommodity().split(",");
                    CommodityInfo commodityInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String commodityCode : tempArr) {
                        commodityInfo = commodityInfoService.selectByCode(commodityCode);
                        if (null != commodityInfo) {
                            sb.append(commodityInfo.getId() + ",");
                        }
                    }
                    map.put("useCommodityIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponRule.getSuseLimitDevice())) {//设备信息
                    String tempArr[] = couponRule.getSuseLimitDevice().split(",");
                    DeviceInfo deviceInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String scode : tempArr) {
                        deviceInfo = deviceInfoService.selectByCode(scode);
                        if (null != deviceInfo) {
                            sb.append(deviceInfo.getId() + ",");
                        }
                    }
                    map.put("useDeviceIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
            } else {//新增
                couponRule = new CouponRule();
                couponRule.setSacCode(activityConf.getSconfCode());
                couponRule.setSacId(activityConf.getId());
            }
            map.put("couponRule", couponRule);
            map.put("iscenesType", activityConf.getIscenesType());//场景类型
            map.put("itype", activityConf.getItype());//活动分类
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            if (activityConf.getIisAvailable().intValue() == 0 && activityConf.getSmerchantId().equals(merchantId)) {
                return "ac/couponRule/couponRule-edit";
            }
            return "ac/couponRule/couponRule-readonly";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * @Author: zhouhong
     * @Description:保存券规则
     * @param: scenesVo
     * @Date: 2018/2/10 15:21
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<CouponRule> save(CouponRule couponRule) {
        try {
            if (StringUtil.isBlank(couponRule.getSacId()) || StringUtil.isBlank(couponRule.getSacCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常，请重新操作！");
            }
            ActivityConf activityConf = activityConfService.selectByPrimaryKey(couponRule.getSacId());
            if (activityConf == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动数据异常！");
            }
            if (null == couponRule.getIcouponType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择券类型！");
            }
            if (couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.MONEY_COUPON.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.REBATE_COUPON.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.FULL_CUT_COUPON.getCode()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择正确的券类型！");
            }
            if (couponRule.getIcouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                if (StringUtil.isBlank(couponRule.getScommodityId()) || StringUtil.isBlank(couponRule.getScommodityCode())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品券必须选择适用商品信息！");
                }
            } else {
                if (couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.FULL_CUT_COUPON.getCode()) {
                    if (null == couponRule.getFuseLimitAmount() || couponRule.getFuseLimitAmount().doubleValue() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("劵使用规则订单实付金额必须大于0！");
                    }
                }
                if (null == couponRule.getFcouponValue()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券面值不能为空！");
                }
            }
            if ((null != activityConf.getIscenesType() && (activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_ORDER.getCode() ||
                    activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode() ||
                    activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode()))
                    || activityConf.getItype() == BizTypeDefinitionEnum.ActivityType.PROMOTIONS_ACTIVITY.getCode()) {
            } else {
                couponRule.setIgiveMethod(20);
            }
            if (couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                if (null == couponRule.getIgiveMethod()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择送券方式！");
                }
                if (couponRule.getIgiveMethod() == 10) {
                    if (null == couponRule.getFminValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("最小券值不能为空！");
                    }
                    if (couponRule.getFminValue().doubleValue() < 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("最小券值必须是正整数！");
                    }
                    if (null == couponRule.getFmaxValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("最大券值不能为空！");
                    }
                    if (couponRule.getFminValue().doubleValue() > couponRule.getFmaxValue().doubleValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("最大券值必须大于最小券值！");
                    }
                }
            }
            if (null == couponRule.getIcouponValidityValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券有效期天数不能为空！");
            }
            if (couponRule.getIcouponValidityValue() < 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("有效期必须是大于0的整数！");
            }
            if (null == couponRule.getDcouponEffectiveDate()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券生效日期不能为空！");
            }
            //计算券的失效日期
            couponRule.setDcouponExpiryDate(DateUtils.addDays(couponRule.getDcouponEffectiveDate(), couponRule.getIcouponValidityValue()));
            if (couponRule.getIisValid().intValue() == 1) {
                if (couponRule.getDcouponExpiryDate().before(DateUtils.getCurrentDateTime())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("当前日期大于券失效日期，规则不能启用！");
                }
            }
            if (couponRule.getDcouponExpiryDate().before(activityConf.getTactiveEndTime())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("活动结束日期大于券失效日期，规则不能启用！");
            }
            if (null == couponRule.getFgiveLimitAmount()) {
                couponRule.setFgiveLimitAmount(BigDecimal.ZERO);
            }
            if (null == couponRule.getFuseLimitAmount()) {
                couponRule.setFuseLimitAmount(BigDecimal.ZERO);
            }
            boolean flag = true;
            if (StringUtil.isNotBlank(couponRule.getId())) {
                flag = false;
            }

            couponRule = couponRuleService.saveOrUpdate(couponRule, activityConf.getIscenesType(), activityConf.getItype());
            if (flag) {// 执行新增
                //操作日志
                String logText = MessageFormat.format("新增场景活动送券规则，编号{0}", couponRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行修改
                //操作日志
                String logText = MessageFormat.format("编辑场景活动送券规则，编号{0}", couponRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(couponRule);
        } catch (Exception ex) {
            logger.error("保存券规则异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存送券规则失败");
        }
    }


}
