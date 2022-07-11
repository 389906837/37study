package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 支付宝免密协议数据
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class WechatSignResult implements Serializable {

    private String mch_id;//协议生效时间
    private String appid;//脱敏的支付宝账
    private String contract_id;//委托代扣协议id
    private String plan_id;//模板id
    private String request_serial;//请求序列号
    private String contract_code;//签约协议号
    private String contract_display_account;//用户账户展示名称
    private String contract_state;//协议状态 0-签约中 1-解约
    private String contract_signed_time;//协议签署时间
    private String contract_expired_time;//协议到期时间
    private String contract_terminated_time;//协议解约时间
    private String contract_termination_mode;//协议解约方式
    private String contract_termination_remark;//解约备注
    private String openid;//用户标识


    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getRequest_serial() {
        return request_serial;
    }

    public void setRequest_serial(String request_serial) {
        this.request_serial = request_serial;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getContract_display_account() {
        return contract_display_account;
    }

    public void setContract_display_account(String contract_display_account) {
        this.contract_display_account = contract_display_account;
    }

    public String getContract_state() {
        return contract_state;
    }

    public void setContract_state(String contract_state) {
        this.contract_state = contract_state;
    }

    public String getContract_signed_time() {
        return contract_signed_time;
    }

    public void setContract_signed_time(String contract_signed_time) {
        this.contract_signed_time = contract_signed_time;
    }

    public String getContract_expired_time() {
        return contract_expired_time;
    }

    public void setContract_expired_time(String contract_expired_time) {
        this.contract_expired_time = contract_expired_time;
    }

    public String getContract_terminated_time() {
        return contract_terminated_time;
    }

    public void setContract_terminated_time(String contract_terminated_time) {
        this.contract_terminated_time = contract_terminated_time;
    }

    public String getContract_termination_mode() {
        return contract_termination_mode;
    }

    public void setContract_termination_mode(String contract_termination_mode) {
        this.contract_termination_mode = contract_termination_mode;
    }

    public String getContract_termination_remark() {
        return contract_termination_remark;
    }

    public void setContract_termination_remark(String contract_termination_remark) {
        this.contract_termination_remark = contract_termination_remark;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "WechatSignResult{" +
                "mch_id='" + mch_id + '\'' +
                ", appid='" + appid + '\'' +
                ", contract_id='" + contract_id + '\'' +
                ", plan_id='" + plan_id + '\'' +
                ", request_serial='" + request_serial + '\'' +
                ", contract_code='" + contract_code + '\'' +
                ", contract_display_account='" + contract_display_account + '\'' +
                ", contract_state='" + contract_state + '\'' +
                ", contract_signed_time='" + contract_signed_time + '\'' +
                ", contract_expired_time='" + contract_expired_time + '\'' +
                ", contract_terminated_time='" + contract_terminated_time + '\'' +
                ", contract_termination_mode='" + contract_termination_mode + '\'' +
                ", contract_termination_remark='" + contract_termination_remark + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
