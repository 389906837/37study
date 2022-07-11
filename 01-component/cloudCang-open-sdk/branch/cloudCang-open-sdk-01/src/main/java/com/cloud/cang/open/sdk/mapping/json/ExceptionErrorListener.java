package com.cloud.cang.open.sdk.mapping.json;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/3 16:57
 */
public class ExceptionErrorListener extends BufferErrorListener {
    public ExceptionErrorListener() {
    }

    public void error(String type, int col) {
        super.error(type, col);
        throw new IllegalArgumentException(this.buffer.toString());
    }
}
