package com.cloud.cang.vendstop.dto;

import java.io.Serializable;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-08 14:38
 **/
public class VendstopDto implements Serializable {
    //用户token
    private String userAuthToken;

    public String getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    @Override
    public String toString() {
        return "VendstopDto{" +
                "userAuthToken='" + userAuthToken + '\'' +
                '}';
    }
}
