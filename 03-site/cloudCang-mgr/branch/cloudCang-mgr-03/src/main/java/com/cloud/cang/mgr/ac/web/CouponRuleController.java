package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ac.service.ActivityConfService;
import com.cloud.cang.mgr.ac.service.CouponRuleService;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
 * @Description: ιεΈθ§ε
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
     * @Description:εΊζ―ζ΄»ε¨ηΌθΎ ιεΈθ§ε
     * @param: sid ζ΄»ε¨ID
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
            if (null != couponRules && couponRules.size() > 0) {//ηΌθΎ
                couponRule = couponRules.get(0);
                //θ·ειεΆζ°ζ?
                if ((null != activityConf.getIscenesType() && (activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_ORDER.getCode() ||
                        activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode() ||
                        activityConf.getIscenesType().intValue() == BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode()))
                        || activityConf.getItype().intValue() == 20) {//ε€ζ­ζ―δΈζ―δΈεη­εΊζ―
                    /*if (StringUtil.isNotBlank(couponRule.getSgiveLimitDevice())) {//θ?Ύε€δΏ‘ζ―
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
                    if (StringUtil.isNotBlank(couponRule.getSgiveLimitBrand())) {//εεεη
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
                    if (StringUtil.isNotBlank(couponRule.getSgiveLimitCommodity())) {//εεδΏ‘ζ―
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
                //θ·εδ½Ώη¨ζ°ζ?
                if (StringUtil.isNotBlank(couponRule.getSuseLimitBrand())) {//εεεη
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
                if (StringUtil.isNotBlank(couponRule.getSuseLimitCategory())) {//εεεη±»
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
                if (StringUtil.isNotBlank(couponRule.getSuseLimitCommodity())) {//εεδΏ‘ζ―
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
                if (StringUtil.isNotBlank(couponRule.getSuseLimitDevice())) {//θ?Ύε€δΏ‘ζ―
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
            } else {//ζ°ε’
                couponRule = new CouponRule();
                couponRule.setSacCode(activityConf.getSconfCode());
                couponRule.setSacId(activityConf.getId());
            }
            map.put("couponRule", couponRule);
            map.put("iscenesType", activityConf.getIscenesType());//εΊζ―η±»ε
            map.put("itype", activityConf.getItype());//ζ΄»ε¨εη±»
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            if (activityConf.getIisAvailable().intValue() == 0 && activityConf.getSmerchantId().equals(merchantId)) {
                return "ac/couponRule/couponRule-edit";
            }
            return "ac/couponRule/couponRule-readonly";
        } catch (Exception e) {
            logger.error("θ·³θ½¬ι‘΅ι’εΌεΈΈοΌ{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * @Author: zhouhong
     * @Description:δΏε­εΈθ§ε
     * @param: scenesVo
     * @Date: 2018/2/10 15:21
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<CouponRule> save(CouponRule couponRule) {
        try {
            if (StringUtil.isBlank(couponRule.getSacId()) || StringUtil.isBlank(couponRule.getSacCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tdiapro", null));
            }
            ActivityConf activityConf = activityConfService.selectByPrimaryKey(couponRule.getSacId());
            if (activityConf == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.aad",null));
            }
            if (null == couponRule.getIcouponType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.pstct",null));
            }
            if (couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.MONEY_COUPON.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.REBATE_COUPON.getCode() &&
                    couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.FULL_CUT_COUPON.getCode()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.pstcct",null));
            }
            if (couponRule.getIcouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                if (StringUtil.isBlank(couponRule.getScommodityId()) || StringUtil.isBlank(couponRule.getScommodityCode())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.cvmsapi",null));
                }
            } else {
                if (couponRule.getIcouponType().intValue() != BizTypeDefinitionEnum.CouponType.FULL_CUT_COUPON.getCode()) {
                    if (null == couponRule.getFuseLimitAmount() || couponRule.getFuseLimitAmount().doubleValue() <= 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.curopambgt",null));
                    }
                }
                if (null == couponRule.getFcouponValue()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.vvcbe",null));
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
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.pctcm",null));
                }
                if (couponRule.getIgiveMethod() == 10) {
                    if (null == couponRule.getFminValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.mcvcbe",null));
                    }
                    if (couponRule.getFminValue().doubleValue() < 0) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tmcvmbapi",null));
                    }
                    if (null == couponRule.getFmaxValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.maxcvcbe",null));
                    }
                    if (couponRule.getFminValue().doubleValue() > couponRule.getFmaxValue().doubleValue()) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tmcvmbgttmcv",null));
                    }
                }
            }
            if (null == couponRule.getIcouponValidityValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tvpotccbe",null));
            }
            if (couponRule.getIcouponValidityValue() < 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.vpmbaigt",null));
            }
            if (null == couponRule.getDcouponEffectiveDate()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.vedcbe",null));
            }
            //θ?‘η?εΈηε€±ζζ₯ζ
            couponRule.setDcouponExpiryDate(DateUtils.addDays(couponRule.getDcouponEffectiveDate(), couponRule.getIcouponValidityValue()));
            if (couponRule.getIisValid().intValue() == 1) {
                if (couponRule.getDcouponExpiryDate().before(DateUtils.getCurrentDateTime())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tcdigttvedtrcbe",null));
                }
            }
            if (couponRule.getDcouponExpiryDate().before(activityConf.getTactiveEndTime())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.teedigttvedtrcbe",null));
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
            if (flag) {// ζ§θ‘ζ°ε’
                //ζδ½ζ₯εΏ
                String logText = MessageFormat.format( MessageSourceUtils.getMessageByKey("con.ac.nsacr", null), couponRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// ζ§θ‘δΏ?ζΉ
                //ζδ½ζ₯εΏ
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.ac.esacr", null), couponRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(couponRule);
        } catch (Exception ex) {
            logger.error("δΏε­εΈθ§εεΌεΈΈοΌ{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.scrf", null));
        }
    }


}
