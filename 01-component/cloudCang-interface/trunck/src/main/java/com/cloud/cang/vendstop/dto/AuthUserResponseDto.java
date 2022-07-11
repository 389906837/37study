package com.cloud.cang.vendstop.dto;

/**
 * @program: 37cang
 * @description: 用户授权返回
 * @author: qzg
 * @create: 2019-08-08 14:41
 **/
public class AuthUserResponseDto extends VendstopDto {
    private String isLoggedIn;
    private String userId;
    private String name;
    private String phoneNumber;

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
