package com.cloud.cang.open.api.cr.service;


import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.open.sdk.model.request.BalanceModel;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.request.ImgRecognitionDto;
import com.cloud.cang.open.sdk.model.request.QueryModel;
import com.cloud.cang.openapi.RecongitionVo;

import java.util.Map;

public interface RecongitionService {

    /**
     * 图片识别接口
     *
     * @param recongitionVo  请求参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionByImg(RecongitionVo<ImgRecognitionDto> recongitionVo) throws ServiceException, Exception;

    /**
     * 图片识别接口
     *
     * @param recongitionVo  请求参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionByImg_callBack(RecongitionVo<ImgRecognition> recongitionVo) throws ServiceException, Exception;

    /**
     * 查询视觉识别账户信息查询
     *
     * @param recongitionVo  查询参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionAccountQuery(RecongitionVo<BalanceModel> recongitionVo) throws ServiceException, Exception;

    /**
     * 查询视觉识别订单查询接口
     *
     * @param recongitionVo  查询参数
     * @throws ServiceException
     */
    Map<String, Object> recongitionOrderQuery(RecongitionVo<QueryModel> recongitionVo) throws ServiceException, Exception;
    /**
     * 图片识别接口
     *
     * @param recongitionVo  请求参数
     * @throws ServiceException
     */
    boolean recongitionSynchronizeByImg(RecongitionVo recongitionVo) throws ServiceException, Exception;
}