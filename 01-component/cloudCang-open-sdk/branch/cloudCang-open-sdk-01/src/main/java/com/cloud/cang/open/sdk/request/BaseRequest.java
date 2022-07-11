package com.cloud.cang.open.sdk.request;

import com.cloud.cang.open.sdk.mapping.BaseObject;
import com.cloud.cang.open.sdk.response.BaseResponse;

import java.util.Map;

/**
 * @version 1.0
 * @Description: 请求抽象类，所有请求必须实现该类
 * @Author: zengzexiong
 * @Date: 2018年8月31日13:23:12
 */
public interface BaseRequest<T extends BaseResponse> {

    String getApiMethodName();

    Map<String, String> getTextParams();

    String getApiVersion();

    void setApiVersion(String var1);

    String getNotifyUrl();

    void setNotifyUrl(String var1);

    String getReturnUrl();

    void setReturnUrl(String var1);

    boolean isNeedEncrypt();

    void setNeedEncrypt(boolean var1);

    BaseObject getBizModel();

    void setBizModel(BaseObject bizModel);

    Class<T> getResponseClass();

}
