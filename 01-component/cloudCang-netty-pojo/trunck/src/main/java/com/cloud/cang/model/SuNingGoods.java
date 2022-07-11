package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 苏宁商品集合
 * @Author: zengzexiong
 * @Date: 2018年9月4日14:28:46
 */
public class SuNingGoods extends SuperDto {
    private List<SuNingGoodChangeModel> suNingGoodChangeModelList;
    private Integer userType;       // 用户类型 10 普通用户，20 管理员
    private String ip;              // 苏宁的IP
    private String userId;          // 苏宁的用户ID

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<SuNingGoodChangeModel> getSuNingGoodChangeModelList() {
        return suNingGoodChangeModelList;
    }

    public void setSuNingGoodChangeModelList(List<SuNingGoodChangeModel> suNingGoodChangeModelList) {
        this.suNingGoodChangeModelList = suNingGoodChangeModelList;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "SuNingGoods{" +
                "suNingGoodChangeModelList=" + suNingGoodChangeModelList +
                ", userType=" + userType +
                ", ip='" + ip + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
