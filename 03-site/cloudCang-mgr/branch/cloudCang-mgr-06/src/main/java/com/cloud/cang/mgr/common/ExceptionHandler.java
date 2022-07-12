package com.cloud.cang.mgr.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


public class ExceptionHandler implements HandlerExceptionResolver {
    
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        log.error("系统异常",ex);
        if ((request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest"))
                || (request.getContentType() != null && request.getContentType().toLowerCase()
                        .indexOf("multipart/form-data".toLowerCase()) > -1)) {
            String error = "系统异常!请稍后重试";
            if (ex instanceof ServiceException) {
                error = ex.getMessage();
            }
            ResponseVo resVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(error);
            com.cloud.cang.mgr.common.StreamRenderUtil.render(resVo, response, null);
         
            return null;
        } else {
            return new ModelAndView("redirect:/mgr/login");
        }

    }

}
