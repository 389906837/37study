package com.cloud.cang.bzc.om.vo;

import java.math.BigDecimal;

/**
 * Created by Alex on 2018/1/9.
 */
public class ActivityDiscountDetailVo {

    private String sacId;	/* 活动ID */

    private String sacCode;	/* 活动编号 */

    private Integer idiscountType;/* 优惠类型10:首单 20:打折满X元 30:打折满X件 40:打折满X元且满X件 50:满减满X元 60:满减每满X元 70:满减满X件 80:满减满X元且满Y件 90:返券 100:返现 */

    private BigDecimal ftargetMoney; /* 目标金额（0不限制） */

    private Integer ftargetNum; /* 目标件数 */

    private BigDecimal fdiscount; /* 优惠折扣 */

    private BigDecimal fdiscountMoney; /* 优惠金额 */

    private BigDecimal fdiscountLimit; /* 优惠上限（满减每满可用） */

    public String getSacId() {
        return sacId;
    }

    public void setSacId(String sacId) {
        this.sacId = sacId;
    }

    public String getSacCode() {
        return sacCode;
    }

    public void setSacCode(String sacCode) {
        this.sacCode = sacCode;
    }

    public Integer getIdiscountType() {
        return idiscountType;
    }

    public void setIdiscountType(Integer idiscountType) {
        this.idiscountType = idiscountType;
    }

    public BigDecimal getFtargetMoney() {
        return ftargetMoney;
    }

    public void setFtargetMoney(BigDecimal ftargetMoney) {
        this.ftargetMoney = ftargetMoney;
    }

    public Integer getFtargetNum() {
        return ftargetNum;
    }

    public void setFtargetNum(Integer ftargetNum) {
        this.ftargetNum = ftargetNum;
    }

    public BigDecimal getFdiscount() {
        return fdiscount;
    }

    public void setFdiscount(BigDecimal fdiscount) {
        this.fdiscount = fdiscount;
    }

    public BigDecimal getFdiscountMoney() {
        return fdiscountMoney;
    }

    public void setFdiscountMoney(BigDecimal fdiscountMoney) {
        this.fdiscountMoney = fdiscountMoney;
    }

    public BigDecimal getFdiscountLimit() {
        return fdiscountLimit;
    }

    public void setFdiscountLimit(BigDecimal fdiscountLimit) {
        this.fdiscountLimit = fdiscountLimit;
    }
}
