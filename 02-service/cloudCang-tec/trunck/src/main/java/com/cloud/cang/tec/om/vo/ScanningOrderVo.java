package com.cloud.cang.tec.om.vo;

/**
 * 扫描订单 Vo(付款很失败、待付款、付款处理中)
 * Created by yan on 2018/5/14.
 */
public class ScanningOrderVo {
    private Integer failureNum;//付款失败数
    private Integer waitNum;//待付款数
    private Integer processingNum;//付款处理中数

    public Integer getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(Integer failureNum) {
        this.failureNum = failureNum;
    }

    public Integer getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(Integer waitNum) {
        this.waitNum = waitNum;
    }

    public Integer getProcessingNum() {
        return processingNum;
    }

    public void setProcessingNum(Integer processingNum) {
        this.processingNum = processingNum;
    }
}
