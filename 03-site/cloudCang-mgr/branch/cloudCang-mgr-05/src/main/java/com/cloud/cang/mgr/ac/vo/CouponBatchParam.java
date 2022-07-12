package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Description: 优惠券批量下发Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/20 10:33
 */
public class CouponBatchParam implements Serializable {

    // 选择用户Id集合
    private String smemberId;

    // 下发用户类型
    private Integer member;

    // 券数量
    private Integer couponNum;

    // 商品券编号
    private String scommodityCode;

    //  商品券ID
    private String scommodityId;


    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    @Override
    public String toString() {
        return "CouponBatchParam{" +
                "smemberId='" + smemberId + '\'' +
                ", member=" + member +
                ", couponNum=" + couponNum +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityId='" + scommodityId + '\'' +
                '}';
    }
}
