package com.cloud.cang.rec.common.intercepter;

import com.cloud.cang.rec.common.utils.LogUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户后台访问记录拦截器
 * @author zhouhong
 * @version 1.0
 */
public class VisitLogIntercepter implements HandlerInterceptor {
    
  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
        /**
         * 在访问结束后拦截日志
         */
    }
    
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
        LogUtil.addVistLog(request);
        return true;
    }

}
