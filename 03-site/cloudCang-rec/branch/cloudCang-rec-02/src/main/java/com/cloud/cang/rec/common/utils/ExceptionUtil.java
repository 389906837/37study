package com.cloud.cang.rec.common.utils;

import org.springframework.ui.ModelMap;

/**
 * @version 1.0
 * @Description: 异常处理页面
 * @Author: zhouhong
 * @Date: 2018/4/21 15:50
 */
public class ExceptionUtil {

    /***
     * 500错误
     * @param errorMsg 错误信息
     * @param type 类型
     * @param modelMap 页面参数
     */
    public static String to500(String errorMsg, Integer type, ModelMap modelMap) {
        modelMap.put("errorMsg", errorMsg);
        modelMap.put("type", type);
        return "error/500";
    }

    /***
     * 500错误 统一错误页面
     */
    public static String to500() {
        return "error/common_500";
    }

    /***
     * 404错误
     * @param errorMsg 错误信息
     * @param modelMap 页面参数
     */
    public static String to404(String errorMsg, ModelMap modelMap) {
        modelMap.put("errorMsg", errorMsg);
        return "error/404";
    }

    /***
     * 404错误
     */
    public static String to404() {
        return "error/common_404";
    }

    /***
     * 业务处理错误
     * @param errorMsg 错误信息
     * @param type 类型
     * @param modelMap 页面参数
     */
    public static String toBussinessError(String errorMsg, Integer type, ModelMap modelMap) {
        modelMap.put("errorMsg", errorMsg);
        modelMap.put("type", type);
        return "error/error";
    }
}
