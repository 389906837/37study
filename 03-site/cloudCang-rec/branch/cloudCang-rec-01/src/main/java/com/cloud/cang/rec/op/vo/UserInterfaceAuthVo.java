package com.cloud.cang.rec.op.vo;

import com.cloud.cang.model.op.UserInterfaceAuth;

/**
 * Created by yan on 2018/11/5.
 */
public class UserInterfaceAuthVo extends UserInterfaceAuth {
    private String appManageName;//应用名
    private String interfaceName;//接口名
    private String saction;//接口动作
    private String appManageId;
    private String interfaceId;
    private Integer isSelect;//是否选择
    private String appManageScode;
    private String userName;//用户名

    public String getSaction() {
        return saction;
    }

    public void setSaction(String saction) {
        this.saction = saction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppManageScode() {
        return appManageScode;
    }

    public void setAppManageScode(String appManageScode) {
        this.appManageScode = appManageScode;
    }

    public String getAppManageName() {
        return appManageName;
    }

    public void setAppManageName(String appManageName) {
        this.appManageName = appManageName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getAppManageId() {
        return appManageId;
    }

    public void setAppManageId(String appManageId) {
        this.appManageId = appManageId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }
}
