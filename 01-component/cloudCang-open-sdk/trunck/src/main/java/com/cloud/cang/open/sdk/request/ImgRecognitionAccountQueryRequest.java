package com.cloud.cang.open.sdk.request;

import com.cloud.cang.open.sdk.mapping.BaseObject;
import com.cloud.cang.open.sdk.response.ImgRecognitionAccountQueryResponse;
import com.cloud.cang.open.sdk.util.BaseHashMap;

import java.util.Map;

/**
 * @version 1.0
 * @Description: 图片视觉识别账户查询接口请求参数类
 * @Author: zhouhong
 * @Date: 2018/9/4 15:09
 */
public class ImgRecognitionAccountQueryRequest implements BaseRequest<ImgRecognitionAccountQueryResponse> {

    private BaseHashMap udfParams;
    private String apiVersion = "1.0.0";
    private String bizContent;
    private String notifyUrl;
    private String returnUrl;
    private boolean needEncrypt = true;
    private BaseObject bizModel = null;

    @Override
    public String getApiMethodName() {
        return "cloud.api.recognition.getBalance";
    }

    @Override
    public Map<String, String> getTextParams() {
        BaseHashMap txtParams = new BaseHashMap();
        txtParams.put("bizContent", this.bizContent);
        if(this.udfParams != null) {
            txtParams.putAll(this.udfParams);
        }
        return txtParams;
    }

    public void putOtherTextParam(String key, String value) {
        if(this.udfParams == null) {
            this.udfParams = new BaseHashMap();
        }

        this.udfParams.put(key, value);
    }

    public BaseHashMap getUdfParams() {
        return udfParams;
    }

    public void setUdfParams(BaseHashMap udfParams) {
        this.udfParams = udfParams;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String getNotifyUrl() {
        return notifyUrl;
    }

    @Override
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String getReturnUrl() {
        return returnUrl;
    }

    @Override
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public boolean isNeedEncrypt() {
        return needEncrypt;
    }

    @Override
    public void setNeedEncrypt(boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
    }

    @Override
    public BaseObject getBizModel() {
        return bizModel;
    }

    @Override
    public void setBizModel(BaseObject bizModel) {
        this.bizModel = bizModel;
    }

    @Override
    public Class<ImgRecognitionAccountQueryResponse> getResponseClass() {
        return ImgRecognitionAccountQueryResponse.class;
    }
}
