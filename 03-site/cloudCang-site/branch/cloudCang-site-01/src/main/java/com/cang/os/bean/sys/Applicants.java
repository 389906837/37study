package com.cang.os.bean.sys;

import java.util.Date;

/**
 * Created by 37cang-1 on 2019/1/3.
 */
public class Applicants {
    /**用户的_id */
    private String _id;
    /*
  * 用户申请测试账号id
  * */
    private String code;
    /*
  * 用户公司名、个人名
  * */
    private String userName;
    /*
  * 联系人
  * */
    private String linkMan;
    /*
  * 联系人电话
  * */
    private String tel;
    /*
  * 用户需求
  * */
    private String require;
    /*
    *账号状态
    * */
    private String status;
     /*
    *申请服务类型
    * */
    private String accountType;
     /*
    *处理人
    * */
    private String operator;
     /*
    *处理时间
    * */
    private Date operationTime;
     /*
    *账号创建时间
    * */
    private Date creatTime;
               /*
    *IP地址
    * */
    private String ip;
      /*
    *账号修改时间
    * */
    private Date updateTime;
    /*
    * 备注
    * */

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String remarks;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Applicants(){};

    public Applicants(String _id, String code, String userName, String linkMan, String tel, String require, String status, String accountType, String operator, Date operationTime, Date creatTime, String ip, Date updateTime, String remarks) {
        this._id = _id;
        this.code = code;
        this.userName = userName;
        this.linkMan = linkMan;
        this.tel = tel;
        this.require = require;
        this.status = status;
        this.accountType = accountType;
        this.operator = operator;
        this.operationTime = operationTime;
        this.creatTime = creatTime;
        this.ip = ip;
        this.updateTime = updateTime;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Applicants{" +
                "_id='" + _id + '\'' +
                ", code='" + code + '\'' +
                ", userName='" + userName + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", tel='" + tel + '\'' +
                ", require='" + require + '\'' +
                ", status='" + status + '\'' +
                ", accountType='" + accountType + '\'' +
                ", operator='" + operator + '\'' +
                ", operationTime=" + operationTime +
                ", creatTime=" + creatTime +
                ", ip='" + ip + '\'' +
                ", updateTime=" + updateTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
