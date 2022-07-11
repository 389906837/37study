package com.cloud.cang.open.api.cr.service;


import com.cloud.cang.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RecongitionService {

    /**
     * 图片识别接口 同步
     *
     * @param request
     * @param batchNo 处理业务编号
     * @param appId   商户应用appId
     * @param params  请求参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception;

    /**
     * 图片识别接口 同步
     *
     * @param request
     * @param batchNo 处理业务编号
     * @param appId   商户应用appId
     * @param params  请求参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionAsyncByImg(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception;

    /**
     * 查询视觉识别账户信息查询
     *
     * @param request 请求信息
     * @param batchNo 业务编号
     * @param appId   应用ID
     * @param params  查询参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionAccountQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception;

    /**
     * 查询视觉识别订单查询接口
     *
     * @param request 请求信息
     * @param batchNo 业务编号
     * @param appId   应用ID
     * @param params  查询参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionOrderQuery(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception;

    /***
     * http请求版云端识别接口
     * @param request 请求参数
     * @param batchNo 业务编号
     * @param appId APPID
     * @param params 请求参数
     * @return
     */
    Map<String,Object> recongitionByImgToWeb(HttpServletRequest request, String batchNo, String appId, Map<String, String> params) throws ServiceException, Exception;
}