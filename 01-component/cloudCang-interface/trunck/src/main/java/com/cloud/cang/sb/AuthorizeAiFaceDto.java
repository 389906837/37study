package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * 人脸识别账号授权
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年7月28日11:32:22
 */
public class AuthorizeAiFaceDto extends SuperDto {

    private String aiId;                // 人脸识别设备ID （必填）
    private String token;               // 30分钟有效（必填）
    private Integer authorizeType;      // 授权类型 1:扫码成功，2：扫码失败，3.授权，4：拒绝（必填）



    // 授权时必填

    private String userId;              // 用户ID
//    private String authorizeAccount;    // 授权账号（支付宝，微信账号）
    private Integer payType;            // 账号类型 10：微信，20：支付宝

    public String getAiId() {
        return aiId;
    }

    public void setAiId(String aiId) {
        this.aiId = aiId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(Integer authorizeType) {
        this.authorizeType = authorizeType;
    }

//    public String getAuthorizeAccount() {
//        return authorizeAccount;
//    }

//    public void setAuthorizeAccount(String authorizeAccount) {
//        this.authorizeAccount = authorizeAccount;
//    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "AuthorizeAiFaceDto{" +
                "aiId='" + aiId + '\'' +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", authorizeType=" + authorizeType +
//                ", authorizeAccount=" + authorizeAccount +
                ", payType=" + payType +
                '}';
    }
}
