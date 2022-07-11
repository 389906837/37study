package com.cloud.cang.rec.op.vo;

import com.cloud.cang.model.op.TransferLog;

/**
 * Created by yan on 2018/12/21.
 */
public class TransferLogVo extends TransferLog {
    private String userName;
    private String appName;
    private String interfaceName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
