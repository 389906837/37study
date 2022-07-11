package com.cloud.cang.open.sdk.response;

import com.cloud.cang.open.sdk.mapping.BaseField;
import com.cloud.cang.open.sdk.mapping.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 请求响应返回基类
 * @Author: zengzexiong
 * @Date: 2018年8月31日13:23:12
 */
public abstract class BaseResponse implements Serializable {

    @BaseField("code")
    private String code;           // 一般返回码
    @BaseField("msg")
    private String msg;            // 返回一般信息
    @BaseField("subCode")
    private String subCode;        // 业务返回码
    @BaseField("subMsg")
    private String subMsg;         // 返回业务信息
    private String body;                // 消息体
    private Map<String, String> params; // 扩展信息

    public BaseResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean isSuccess() {
        return StringUtils.areNotEmpty(this.subCode);
    }
    @Override
    public String toString() {
        return "CloudCangResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", subCode='" + subCode + '\'' +
                ", subMsg='" + subMsg + '\'' +
                ", body='" + body + '\'' +
                ", params=" + params +
                '}';
    }
}
