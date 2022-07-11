package com.cloud.cang.open.sdk.client;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;

import java.util.Map;

/**
 * @version 1.0
 * @Description: 请求客户端
 * @Author: zhouhong
 * @Date: 2018/9/3 15:15
 */
public interface BaseClient {

    <T extends BaseResponse> T execute(BaseRequest<T> request) throws CloudCangException;

    Map<String, Object> executeByMap(BaseRequest request) throws CloudCangException;
}
