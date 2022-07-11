package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.request.BaseRequest;
import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.SignItem;

/**
 * @version 1.0
 * @Description: 自定义 json解析器
 * @Author: zhouhong
 * @Date: 2018/9/3 16:41
 */
public class ObjectJsonParser<T extends BaseResponse> implements BaseParser<T> {
    private Class<T> clazz;

    public ObjectJsonParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T parse(String scontent) throws CloudCangException {
        Converter converter = new JsonConverter();
        return converter.toResponse(scontent, this.clazz);
    }

    @Override
    public SignItem getSignItem(BaseRequest<?> request, String responseBody) throws CloudCangException {
        Converter converter = new JsonConverter();
        return converter.getSignItem(request, responseBody);
    }

    @Override
    public String encryptSourceData(BaseRequest<?> request, String responseBody, String format, String encryptType, String encryptKey, String charset) throws CloudCangException {
        Converter converter = new JsonConverter();
        return converter.encryptSourceData(request, responseBody, format, encryptType, encryptKey, charset);
    }
}
