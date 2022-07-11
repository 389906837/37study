package com.cloud.cang.open.sdk.util;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/3 17:04
 */
public class RequestParametersHolder {
    private BaseHashMap protocalMustParams;
    private BaseHashMap protocalOptParams;
    private BaseHashMap applicationParams;

    public RequestParametersHolder() {
    }

    public BaseHashMap getProtocalMustParams() {
        return this.protocalMustParams;
    }

    public void setProtocalMustParams(BaseHashMap protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public BaseHashMap getProtocalOptParams() {
        return this.protocalOptParams;
    }

    public void setProtocalOptParams(BaseHashMap protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    public BaseHashMap getApplicationParams() {
        return this.applicationParams;
    }

    public void setApplicationParams(BaseHashMap applicationParams) {
        this.applicationParams = applicationParams;
    }
}
