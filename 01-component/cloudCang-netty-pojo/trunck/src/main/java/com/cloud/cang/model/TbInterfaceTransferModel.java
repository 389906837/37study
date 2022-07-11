package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;


/**
 * @version 1.0
 * @Description: 第三方接口调用记录
 * @Author: zengzexiong
 * @Date: 2018年10月10日15:48:15
 */
public class TbInterfaceTransferModel extends SuperDto {

    private Integer userType;           // 用户类型 10 普通用户 20 管理员用户
    private String thirdCode;           // 第三方编号
    private String thirdName;           // 第三方名称
    private Integer interfaceType;      // 接口类型 10 请求接口 20 返回接口
    private String interfaceAction;     // 接口动作
    private String interfaceName;       // 接口名称
    private String reqParams;           // 请求参数
    private String reqTime;             // 请求时间
    private String respParams;          // 相应参数
    private String respTime;            // 相应时间

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceAction() {
        return interfaceAction;
    }

    public void setInterfaceAction(String interfaceAction) {
        this.interfaceAction = interfaceAction;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public String getRespParams() {
        return respParams;
    }

    public void setRespParams(String respParams) {
        this.respParams = respParams;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    @Override
    public String toString() {
        return "TbInterfaceTransferModel{" +
                ", userType='" + userType + '\'' +
                ", thirdCode='" + thirdCode + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", interfaceType=" + interfaceType +
                ", interfaceAction='" + interfaceAction + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", reqParams='" + reqParams + '\'' +
                ", respParams='" + respParams + '\'' +
                ", reqTime='" + reqTime + '\'' +
                ", respTime='" + respTime + '\'' +
                '}';
    }
}
