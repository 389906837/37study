package com.cloud.cang.wap.hy.web;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.ac.service.DiscountRecordService;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.ParamPage;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.index.service.impl.IndexServiceImpl;
import com.cloud.cang.wap.index.web.IndexController;
import com.cloud.cang.wap.om.service.OrderCommodityService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.om.service.RefundAuditService;
import com.cloud.cang.wap.om.service.RefundCommodityService;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sys.service.OperatorService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: ??????
 * @Author: zhouhong
 * @Date: 2018/4/17 16:28
 */
@Controller
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private ICached iCached;
    @Autowired
    private OperatorService operatorService;
    private static Logger logger = LoggerFactory.getLogger(PersonalController.class);

    /**
     * ??????
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String getMineInfo(HttpServletRequest request, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
        modelMap.put("user", memberInfo);
       /* if (authVo.isReplenishment()) {
            modelMap.put("isReplenishment", 1);
        }*/
        if (authVo.isReplenishment()) {
            Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
            DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
            if (null != operator.getIdevType() && 0 == operator.getIdevType()) {
                modelMap.put("isReplenishment", 1);
            } else if (null != operator.getIdevType() && 2 == operator.getIdevType() && operator.getSgroupDecList().contains(deviceVo.getDeviceId())) {
                modelMap.put("isReplenishment", 1);
            } else {
                modelMap.put("isReplenishment", 0);
            }
        } else {
            modelMap.put("isReplenishment", 0);
        }


        try {
            modelMap = indexService.getPayConfig(modelMap, request);
            String memberId = (String) iCached.get("is_remind_open_free_data_" + authVo.getId());
            if (StringUtil.isNotBlank(memberId)) {

                if (WapUtils.isWXRequest(request) /*&& (null == memberInfo.getIwechatOpen() || memberInfo.getIwechatOpen().intValue() == 0)*/) {
                    MerchantConf merchantConf = indexService.getWechatMerchantConf(memberInfo.getSmerchantCode(), 2);
                    if((merchantConf.getIwechatWithholdType() == null || merchantConf.getIwechatWithholdType() == 10) && (null == memberInfo.getIwechatOpen() || memberInfo.getIwechatOpen().intValue() == 0)){
                        modelMap.put("openFree", 1);
                        modelMap.put("freeOpenPath", "/personal/wechatSign.html");
                    } else if ((merchantConf.getIwechatWithholdType() != null && merchantConf.getIwechatWithholdType() == 20) && (null == memberInfo.getIwechatPointOpen() || memberInfo.getIwechatPointOpen().intValue() == 0)) {
                        modelMap.put("openFree", 2);
                        modelMap.put("freeOpenPath", "/personal/wechatOpen.html");
                    }
                } else if (WapUtils.isAlipayRequest(request) && (null == memberInfo.getIaipayOpen() || memberInfo.getIaipayOpen().intValue() == 0)) {
                    modelMap.put("openFree", 1);
                    modelMap.put("freeOpenPath", "/personal/alipaySign.html");
                }
            }
        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
        }
        return "mine/mine_new";
    }

}
