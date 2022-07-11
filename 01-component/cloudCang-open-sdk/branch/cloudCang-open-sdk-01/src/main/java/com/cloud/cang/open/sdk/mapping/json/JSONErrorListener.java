package com.cloud.cang.open.sdk.mapping.json;

/**
 * @version 1.0
 * @ClassName: cloudCangBranch
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/3 16:46
 */
public interface JSONErrorListener {
    void start(String var1);

    void error(String var1, int var2);

    void end();
}
