package com.cloud.cang.mgr.common.intercepter;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.model.sh.DomainConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 后台访问请求拦截  验证域名合法性
 *
 * @author zhouhong
 * @version 1.0
 */
public class RequestIntercepter implements HandlerInterceptor {

    @Autowired
    private ICached iCached;
    @Autowired
    private MerchantInfoService merchantInfoService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        //判断路径是否可以通过
        String currentUrl = request.getServletPath();
        if (currentUrl.indexOf("/mgr/unauthorized") != -1 || currentUrl.indexOf("/init/cached") != -1) {
            return true;
        }
        //获取域名
        DomainConf domainConf = (DomainConf) iCached.get(request.getServerName().substring(1, request.getServerName().length()));
        if (domainConf == null || domainConf.getIstatus().intValue() != BizTypeDefinitionEnum.DomainStatus.AUDIT_SUCCESS.getCode()) {
            //商户域名配置错误
            response.sendRedirect(request.getContextPath() + "/mgr/unauthorized");
            return true;
        }
        MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(domainConf.getSmerchantId());
        if (null == merchantInfo || merchantInfo.getIstatus().intValue() != BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode()) {
            //非法商户访问
            response.sendRedirect(request.getContextPath() + "/mgr/unauthorized");
            return true;
        }
        if (merchantInfo.getSparentId().equals("0") || 1 == merchantInfo.getIisParent()) {//根商户或者为父商户
            SessionUserUtils.getSession().setAttribute(SessionUserUtils.SESSION_KEY_ROOT_MERCHANT, 1);
        }
        SessionUserUtils.getSession().setAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID, domainConf.getSmerchantId());
        SessionUserUtils.getSession().setAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE, merchantInfo.getScode());
        return true;
    }

}
