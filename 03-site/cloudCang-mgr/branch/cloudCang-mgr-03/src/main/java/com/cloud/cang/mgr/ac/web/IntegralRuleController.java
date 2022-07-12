package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ac.service.ActivityConfService;
import com.cloud.cang.mgr.ac.service.IntegralRuleService;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
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
import java.util.List;

/**
 * @version 1.0
 * @ClassName: IntegralRuleController
 * @Description: 积分规则
 * @Author: zhouhong
 * @Date: 2018/2/7 19:33
 */
@Controller
@RequestMapping("/integralRule")
public class IntegralRuleController {

    private static final Logger logger = LoggerFactory.getLogger(IntegralRuleController.class);

    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private IntegralRuleService integralRuleService;


    /**
     * @Author: zhouhong
     * @Description:场景活动编辑 积分规则
     * @param: sid 活动ID
     * @Date: 2018/2/8 20:14
     */
    @RequestMapping("/edit")
    public String edit(ModelMap map, String sid) {
        try {
            ActivityConf activityConf = activityConfService.selectByPrimaryKey(sid);
            IntegralRule param = new IntegralRule();
            param.setSacId(activityConf.getId());
            param.setSacCode(activityConf.getSconfCode());
            param.setIdelFlag(0);
            List<IntegralRule> integralRules = integralRuleService.selectByEntityWhere(param);
            IntegralRule integralRule = null;
            if (null != integralRules && integralRules.size() > 0) {//编辑
                integralRule = integralRules.get(0);
            } else {//新增
                integralRule = new IntegralRule();
                integralRule.setSacCode(activityConf.getSconfCode());
                integralRule.setSacId(activityConf.getId());
            }
            map.put("integralRule", integralRule);
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            if (activityConf.getIisAvailable().intValue() == 0 && activityConf.getSmerchantId().equals(merchantId)) {
                return "ac/integralRule/integralRule-edit";
            }
            return "ac/integralRule/integralRule-readonly";
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
    ResponseVo<IntegralRule> save(IntegralRule integralRule) {
        try {
            if (StringUtil.isBlank(integralRule.getSacId()) || StringUtil.isBlank(integralRule.getSacCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tdiapro", null));
            }
            if (null == integralRule.getIgiveMethod()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.pcagm", null));
            }
            if (integralRule.getIgiveMethod() == 10) {
                if (null == integralRule.getFminValue()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tmivcbe", null));
                }
                if (null == integralRule.getFmaxValue()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.mivcbe", null));
                }
            }
            boolean flag = true;
            if (StringUtil.isNotBlank(integralRule.getId())) {
                flag = false;
            }
            integralRule = integralRuleService.saveOrUpdate(integralRule);
            if (flag) {// 执行新增
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.ac.asatspr", null), integralRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行修改
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.ac.esatspr", null), integralRule.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(integralRule);
        } catch (Exception ex) {
            logger.error("保存券规则异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.stbrf", null));
        }
    }


}
