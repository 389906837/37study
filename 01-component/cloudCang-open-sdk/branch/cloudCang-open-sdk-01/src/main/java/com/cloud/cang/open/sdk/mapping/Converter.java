package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.SignItem;

/**
 * @version 1.0
 * @Description: base装换器
 * @Author: zhouhong
 * @Date: 2018/9/3 16:18
 */
public interface Converter {
    <T extends BaseResponse> T toResponse(String scontent, Class<T> tClass) throws CloudCangException;
    SignItem getSignItem(BaseRequest<?> request, String responseBody) throws CloudCangException;
    String encryptSourceData(BaseRequest<?> request, String responseBody, String format, String encryptType, String encryptKey, String charset) throws CloudCangException;
}
