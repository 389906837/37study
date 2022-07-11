package com.cloud.cang.rec.op.vo;

import com.cloud.cang.model.op.InterfaceAccount;

/**
 * Created by yan on 2018/11/6.
 */
public class InterfaceAccountVo extends InterfaceAccount {
    private String userName;//用户名
    private String interfaceName;//接口名
    private String interfaceAction;//接口动作
    private String appId;//应用Id

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getInterfaceAction() {
        return interfaceAction;
    }

    public void setInterfaceAction(String interfaceAction) {
        this.interfaceAction = interfaceAction;
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
