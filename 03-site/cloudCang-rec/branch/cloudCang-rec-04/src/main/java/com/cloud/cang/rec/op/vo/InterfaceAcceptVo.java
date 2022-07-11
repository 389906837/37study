package com.cloud.cang.rec.op.vo;

import com.cloud.cang.model.op.InterfaceAccept;

/**
 * 平台接口业务受理信息Vo
 * Created by yan on 2018/11/14.
 */
public class InterfaceAcceptVo extends InterfaceAccept {
    private String appName;//应用名
    private String userName;//用户名
    private String interfaceName;//接口名

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
