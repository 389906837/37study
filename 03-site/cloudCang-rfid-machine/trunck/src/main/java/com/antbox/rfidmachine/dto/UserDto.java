package com.antbox.rfidmachine.dto;

import com.antbox.rfidmachine.enumclass.SystemType;

/**
 * Created by DK on 17/5/9.
 * 用户信息
 */
public class UserDto{

    private String username;

    private String password;

    //域名
    private String domain;

    //系统类型
    private SystemType  systemType;

    //平台商ID
    private Long mallId;

    //加盟商ID
    private Long merchantId;

    private String operatorId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
