package com.cloud.cang.faceModel;

import com.cloud.cang.faceCommon.SuperDto;

/**
 * @version 1.0
 * @Description: 授权成功返回结果
 * @Author: zengzexiong
 * @Date: 2018年7月28日14:04:37
 */
public class AuthorizeSuccessModel extends SuperDto {
    private String userId;      // 用户ID
    private Integer payType;    // 授权支付方式

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "AuthorizeSuccessModel{" +
                "userId='" + userId + '\'' +
                ", payType=" + payType +
                '}';
    }
}
