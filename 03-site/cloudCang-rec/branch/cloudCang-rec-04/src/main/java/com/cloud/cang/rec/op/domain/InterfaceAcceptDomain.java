package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.InterfaceAccept;

/**
 * AppManage 查询扩展类
 * Created by yan on 2018/9/28.
 */
public class InterfaceAcceptDomain extends InterfaceAccept {

    private String orderStr; //排序
    private String userName;//用户名
    private String appName;//应用名称
    private String interfaceName;//接口名称

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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
