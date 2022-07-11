package com.cloud.cang.open.sdk.model.request;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 基础查询参数model
 * @Author: zhouhong
 * @Date: 2018/9/17 20:08
 */
public class QueryModel implements Serializable {

    //========二选一 必填============
    private String batchNo;//平台业务编号
    private String outBatchNo;//商户业务编号

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getOutBatchNo() {
        return outBatchNo;
    }

    public void setOutBatchNo(String outBatchNo) {
        this.outBatchNo = outBatchNo;
    }

    @Override
    public String toString() {
        return "QueryModel{" +
                "batchNo='" + batchNo + '\'' +
                ", outBatchNo='" + outBatchNo + '\'' +
                '}';
    }
}
