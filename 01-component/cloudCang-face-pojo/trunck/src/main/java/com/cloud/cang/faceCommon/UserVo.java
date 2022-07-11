package com.cloud.cang.faceCommon;

/**
 * @version 1.0
 * @Description: 用户信息
 * @Author: zengzexiong
 * @Date: 2018年7月25日10:16:01
 */
public class UserVo extends SuperDto {
    private String userId;      // 操作用户ID
    private String userCode;    // 用户编号
    private String userName;    // 用户名

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                ", userId='" + userId + '\'' +
                ", userCode='" + userCode + '\'' +
                "userName='" + userName + '\'' +
                '}';
    }
}
