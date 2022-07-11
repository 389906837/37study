package com.cloud.cang.open.sdk.mapping.json;

/**
 * @version 1.0
 * @ClassName: cloudCangBranch
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/3 16:47
 */
public class StdoutStreamErrorListener extends BufferErrorListener {
    public StdoutStreamErrorListener() {
    }

    public void end() {
        System.out.print(this.buffer.toString());
    }
}
