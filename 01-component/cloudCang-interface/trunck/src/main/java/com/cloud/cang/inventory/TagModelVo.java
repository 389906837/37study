package com.cloud.cang.inventory;

/**
 * Created by YLF on 2019/7/3.
 */
public class TagModelVo {
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
}
