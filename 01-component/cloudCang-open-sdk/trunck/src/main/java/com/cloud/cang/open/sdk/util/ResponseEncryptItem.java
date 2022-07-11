package com.cloud.cang.open.sdk.util;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/4 11:21
 */
public class ResponseEncryptItem implements Serializable {

    private String respContent;
    private String realContent;

    public ResponseEncryptItem(String respContent, String realContent) {
        this.respContent = respContent;
        this.realContent = realContent;
    }

    public String getRespContent() {
        return this.respContent;
    }

    public String getRealContent() {
        return this.realContent;
    }
}
