package com.cloud.cang.wap.hy.web;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.TrashAuthorise;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.hy.service.TrashAuthoriseService;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sys.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 我的
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
    @Autowired
    private TrashAuthoriseService trashAuthoriseService;
    @Autowired
    DeviceInfoService deviceInfoService;
    private static Logger logger = LoggerFactory.getLogger(PersonalController.class);

    /**
     * 我的
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String getMineInfo(HttpServletRequest request, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
        modelMap.put("user", memberInfo);
       /* if (authVo.isReplenishment()) {
            modelMap.put("isReplenishment", 1);
        }*/
        DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
        if (authVo.isReplenishment()) {
            Operator operator = operatorService.selectByMemberName(authVo.getUserName(), authVo.getSmerchantCode());
            //DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
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
                    if ((merchantConf.getIwechatWithholdType() == null || merchantConf.getIwechatWithholdType() == 10) && (null == memberInfo.getIwechatOpen() || memberInfo.getIwechatOpen().intValue() == 0)) {
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
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
            memberId = (String) iCached.get("is_remind_auth_trash_data_" + authVo.getId());
            if (null != deviceInfo.getIisBindTrash() && deviceInfo.getIisBindTrash() == 1) {
                Map<String, Object> map1 = new HashMap();
                map1.put("smemberId", authVo.getId());
                map1.put("strashCode", deviceInfo.getStrashCode());
                map1.put("strashBrand", deviceInfo.getStrashBrand());
                map1.put("istatus", 10);
                TrashAuthorise authoriseTrash = trashAuthoriseService.selectByUserIdAndTrash(map1);
                if (null != authoriseTrash && authoriseTrash.getIstatus() == 10) {
                    modelMap.put("authTrash", 1);
                    modelMap.put("authoriseTrash", authoriseTrash);
                } else if ((null == authoriseTrash || authoriseTrash.getIstatus() == 20) /*&& StringUtil.isNotBlank(memberId)*/) {
                    modelMap.put("authTrash", 0);
                    modelMap.put("authoriseTrashPage", "/personal/authTrash.html");
                }
            }
        } catch (Exception e) {
            logger.error("我的页面访问异常：{}", e);
        }
        return "mine/mine_new";
    }

}
