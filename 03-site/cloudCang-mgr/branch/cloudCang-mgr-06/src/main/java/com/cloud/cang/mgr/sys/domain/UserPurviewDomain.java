package com.cloud.cang.mgr.sys.domain;

import com.cloud.cang.model.sys.Operator;

/**
 * @description: 用户权限 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class UserPurviewDomain  extends Operator{

    private static final long serialVersionUID = 1L;

    private String orderStr;//排序字段
    private  String sroleName; //角色名
    private String spurName;//权限名
    private  String spurCode;//权限码

    public String getOrderStr() {
        return orderStr;
    }
    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

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
