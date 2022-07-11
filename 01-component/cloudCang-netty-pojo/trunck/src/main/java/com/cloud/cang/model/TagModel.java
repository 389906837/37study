package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 商品Bean
 * @Author: zengzexiong
 * @Date: 2018年4月10日20:03:57
 */
public class TagModel extends SuperDto {
    private String name;        //商品名称
    private String svrCode;     //商品视觉识别编号
    private Integer number;     //商品数量

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "name='" + name + '\'' +
                ", svrCode='" + svrCode + '\'' +
                ", number=" + number +
                '}';
    }
}
