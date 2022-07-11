package com.cloud.cang.api.om.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 订单Vo
 * @Author: zhouhong
 * @Date: 2018/4/23 18:53
 */
public class OrderDomian implements Serializable {
    //公共参数
    private String sorderCode;//订单编号
    private List<CommodityDomain> commoditys;//商品集合
    private Integer totalNum;//总的商品件数

    //订单参数
    private String id;//订单ID
    private Integer istatus;//订单状态
    private Date orderTime;//订单时间
    private BigDecimal orderAmount;//订单金额
    private BigDecimal discountAmount;//优惠金额
    private BigDecimal actualPayAmount;//实付金额
    private BigDecimal actualRefundAmount;//实际退款金额


    //退款订单参数
    private String refundId;//订单ID
    private String refundCode;//退款编号
    /*审核状态
10=待审核
20=审核通过
30=审核驳回*/
    private Integer iauditStatus;//订单审核状态
    /*退款状态
10=待退款
20=退款失败
30=退款成功*/
    private Integer irefundStatus;//订单退款状态
    private Date applyTime;//订单申请时间
    private BigDecimal refundOrderAmount;//退款订单金额



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSorderCode() {
        return sorderCode;
    }

    public void setSorderCode(String sorderCode) {
        this.sorderCode = sorderCode;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<CommodityDomain> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<CommodityDomain> commoditys) {
        this.commoditys = commoditys;
    }

    public BigDecimal getActualPayAmount() {
        return actualPayAmount;
    }

    public void setActualPayAmount(BigDecimal actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }

    public Integer getTotalNum() {
        totalNum = 0;//计算订单商品件数
        if (null != commoditys && commoditys.size() > 0) {
            for (CommodityDomain domain : commoditys) {
                totalNum += domain.getOrderNum();
            }
        }
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Integer getIauditStatus() {
        return iauditStatus;
    }

    public void setIauditStatus(Integer iauditStatus) {
        this.iauditStatus = iauditStatus;
    }

    public Integer getIrefundStatus() {
        return irefundStatus;
    }

    public void setIrefundStatus(Integer irefundStatus) {
        this.irefundStatus = irefundStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public BigDecimal getRefundOrderAmount() {
        return refundOrderAmount;
    }

    public void setRefundOrderAmount(BigDecimal refundOrderAmount) {
        this.refundOrderAmount = refundOrderAmount;
    }

    public BigDecimal getActualRefundAmount() {
        return actualRefundAmount;
    }

    public void setActualRefundAmount(BigDecimal actualRefundAmount) {
        this.actualRefundAmount = actualRefundAmount;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    @Override
    public String toString() {
        return "OrderDomian{" +
                "sorderCode='" + sorderCode + '\'' +
                ", commoditys=" + commoditys +
                ", totalNum=" + totalNum +
                ", id='" + id + '\'' +
                ", istatus=" + istatus +
                ", orderTime=" + orderTime +
                ", orderAmount=" + orderAmount +
                ", discountAmount=" + discountAmount +
                ", actualPayAmount=" + actualPayAmount +
                ", refundId='" + refundId + '\'' +
                ", refundCode='" + refundCode + '\'' +
                ", iauditStatus=" + iauditStatus +
                ", irefundStatus=" + irefundStatus +
                ", applyTime=" + applyTime +
                ", refundOrderAmount=" + refundOrderAmount +
                ", actualRefundAmount=" + actualRefundAmount +
                '}';
    }
}
