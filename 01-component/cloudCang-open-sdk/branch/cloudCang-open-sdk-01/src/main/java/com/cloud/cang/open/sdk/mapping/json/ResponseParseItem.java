package com.cloud.cang.open.sdk.mapping.json;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/4 11:42
 */
public class ResponseParseItem implements Serializable {
    private int startIndex = -1;
    private int endIndex = -1;
    private String encryptContent = null;

    public ResponseParseItem(int startIndex, int endIndex, String encryptContent) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.encryptContent = encryptContent;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public String getEncryptContent() {
        return this.encryptContent;
    }
}
