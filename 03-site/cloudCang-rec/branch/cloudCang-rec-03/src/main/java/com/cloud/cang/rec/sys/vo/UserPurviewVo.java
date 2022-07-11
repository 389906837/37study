package com.cloud.cang.rec.sys.vo;

import com.cloud.cang.model.sys.Operator;

/**
 * @description: 用户权限 Vo
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class UserPurviewVo  extends Operator{
    private  String sroleName; //角色名
    private String spurName;//权限名
    private  String spurCode;//权限码

    public String getSroleName() {
        return sroleName;
    }

    public void setSroleName(String sroleName) {
        this.sroleName = sroleName;
    }

    public String getSpurName() {
        return spurName;
    }

    public void setSpurName(String spurName) {
        this.spurName = spurName;
    }

    public String getSpurCode() {
        return spurCode;
    }

    public void setSpurCode(String spurCode) {
        this.spurCode = spurCode;
    }
}
